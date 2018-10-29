package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.restclient.RestClient;
import org.jocaqes.Misc.Archivo;
import org.jocaqes.Misc.Estudiante;

/**
 * Servlet implementation class ArbolBservlet
 */
@WebServlet("/Admin/estudiantes")
public class ArbolBservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArbolBservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo_request=request.getParameter("tipo");
		if(tipo_request.equals("load"))
		{
			String ruta_archivo=request.getParameter("ruta");
			cargaMasiva(ruta_archivo,request,response);
			/*if(respuesta==null)
				response.sendRedirect("estudiantes.jsp");
			else
			{
				request.setAttribute("mensaje", respuesta);
				request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
			}*/
		}else if(tipo_request.equals("add"))
		{
			agregar(request,response);
		}
		else
		{
			response.sendRedirect("estudiantes.jsp");
		}
	}
	
	private void agregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nombre=request.getParameter("nombre");
		String apellidos=request.getParameter("apellidos");
		int carnet=Integer.parseInt(request.getParameter("carnet"));
		double dpi=Double.parseDouble(request.getParameter("dpi"));
		String correo=request.getParameter("correo");
		String password=request.getParameter("password");
		Estudiante estudiante=new Estudiante(carnet, dpi, password, "", nombre, apellidos, 0, correo);
		WebTarget agregar=RestClient.getAdmin_target()
				.path("/agregar");
		Response respuesta = agregar
				.request(MediaType.TEXT_PLAIN)
				.post(Entity.json(estudiante));
		int status=respuesta.getStatus();
		if(status==Status.CREATED.getStatusCode())
		{
			request.setAttribute("agregado", respuesta.readEntity(String.class));
			request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		}
		else//400 bad request
		{
			request.setAttribute("agregado", "El estudiante no pudo ser agregado, lo sentimos");
			request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		}
		
	}

	
	private void cargaMasiva(String ruta, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String output="";
		WebTarget cargar=RestClient.getAdmin_target()
				.path("/cargar");
		String json = Archivo.leer(ruta);
		if(json==null)
			output=null;
		else
		{
			Response respuesta = cargar
					.request(MediaType.TEXT_PLAIN)
					.post(Entity.json(json));
			int status = respuesta.getStatus();
			if(status==Status.CREATED.getStatusCode())
			{
				output=respuesta.readEntity(String.class);
			}
			else
			{
				output="Lo sentimos no se pudo realizar la carga masiva, revise que el formato de su archivo sea el correcto";
			}
		}
		if(output==null)
			response.sendRedirect("estudiantes.jsp");
		else
		{
			request.setAttribute("mensaje", output);
			request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		}
		//return output;
	}

}
