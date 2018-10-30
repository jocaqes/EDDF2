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
		}else if(tipo_request.equals("add"))
		{
			agregar(request,response);
		}
		else if(tipo_request.equals("search"))
		{
			buscar(request,response);
		}
		else if(tipo_request.equals("modify"))
		{
			modificar(request,response);
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
	
	private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String carnet_=request.getParameter("carne");
		int carnet=toInt(carnet_);
		if(carnet<0)
		{
			request.setAttribute("modificar", "Este campo solo acepta formato numerico");
			request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		}
		else
		{
			WebTarget buscar=RestClient.getAdmin_target()
					.path("buscar/{carne}");
			Response respuesta = buscar
					.resolveTemplate("carne", carnet)
					.request(MediaType.APPLICATION_JSON)
					.get();
			int status=respuesta.getStatus();
			if(status==Status.FOUND.getStatusCode())
			{
				Estudiante encontrado = respuesta.readEntity(Estudiante.class);
				request.setAttribute("modificar", formModificar(encontrado));
				request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
			}
			else//Status.NOT_FOUND
			{
				request.setAttribute("modificar", "No se encontro al estudiante");
				request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
			}
		}
	}
	
	private void modificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String carnet_=request.getParameter("carne");
		int carnet=Integer.parseInt(carnet_);
		String nombre=request.getParameter("nombre");
		String apellidos=request.getParameter("apellidos");
		double dpi=Double.parseDouble(request.getParameter("dpi"));
		String correo=request.getParameter("password");
		String password=request.getParameter("password");
		int creditos=Integer.parseInt(request.getParameter("creditos"));
		String token=request.getParameter("token");
		Estudiante modificado=new Estudiante(carnet, dpi, password, token, nombre, apellidos, creditos, correo);
		WebTarget modifi=RestClient.getAdmin_target()
				.path("modificar");
		Response respuesta = modifi
				.request(MediaType.TEXT_PLAIN)
				.put(Entity.json(modificado));
		int status=respuesta.getStatus();
		if(status==Status.ACCEPTED.getStatusCode())
		{
			request.setAttribute("resultado", respuesta.readEntity(String.class));
			request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		}
		else//Status.BAD_REQUEST
		{
			request.setAttribute("resultado", "No se pudo modificar el estudiante");
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
	
	
	
	
	
	
	
	private String formModificar(Estudiante a_modificar)
	{

		String dpi = new java.math.BigDecimal(a_modificar.getDpi()).toPlainString();
		String out="Carnet:"+a_modificar.getCarnet(); 
		out+="<form method=\"post\" action=\"estudiantes\">" + 
				"Nombre:<br>" + 
				"<input type=\"text\" name=\"nombre\" value=\""+a_modificar.getNombre()+"\"><br>" + 
				"Apellidos:<br>" + 
				"<input type=\"text\" name=\"apellidos\" value=\""+a_modificar.getApellidos()+"\"><br>" + 
				"DPI:<br>" + 
				"<input type=\"text\" name=\"dpi\" value=\""+dpi+"\"><br>" + 
				"Correo:<br>" + 
				"<input type=\"email\" name=\"correo\" value=\""+a_modificar.getCorreo()+"\"><br>" + 
				"Contraseña:<br>" + 
				"<input type=\"text\" name=\"password\" value=\""+a_modificar.getPassword()+"\"><br>" +
				"<input type=\"hidden\" name=\"tipo\" value=\"modify\">"+
				"<input type=\"hidden\" name=\"carne\" value=\""+a_modificar.getCarnet()+"\">"+
				"<input type=\"hidden\" name=\"creditos\" value=\""+a_modificar.getCreditos()+"\">"+
				"<input type=\"hidden\" name=\"token\" value=\""+a_modificar.getToken()+"\">"+
				"<input type=\"submit\" name=\"modificar\" value=\"Modificar\">"+
				"</form>";
		return out;
	}
	
	private int toInt(String numero)
	{
		int out=-1;
		if(numero==null||numero.isEmpty())
			return out;
		try
		{
			out=Integer.parseInt(numero);
			return out;
		}catch(NumberFormatException e)
		{
			System.out.println("En ControlAdmin->buscarEstudiante:"+e.getMessage());
			return out;
		}
	}

}
