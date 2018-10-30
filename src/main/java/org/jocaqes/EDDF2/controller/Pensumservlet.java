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

/**
 * Servlet implementation class Pensumservlet
 */
@WebServlet("/Admin/pensum")
public class Pensumservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pensumservlet() {
        super();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo_request=request.getParameter("tipo");
		if(tipo_request.equals("load"))
		{
			cargaMasiva(request, response);
		}
		else if(tipo_request.equals("graph"))
		{
			graficaMatriz(request, response);
		}
		else
			response.sendRedirect("pensum.jsp");
	}
	
	private void cargaMasiva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String ruta=request.getParameter("ruta");
		String output="";
		WebTarget cargar=RestClient.getAdmin_target()
				.path("/pensum/cargar");
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
			response.sendRedirect("pensum.jsp");
		else
		{
			request.setAttribute("mensaje", output);
			request.getRequestDispatcher("pensum.jsp").forward(request, response);
		}
	}
	
	private void graficaMatriz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		WebTarget graph=RestClient.getMisc_target()
				.path("/matrizady");
		Response respuesta = graph
				.request(MediaType.TEXT_PLAIN)
				.get();
		int code=respuesta.getStatus();
		if(code==Status.OK.getStatusCode())
		{
			String nombre="matriz-adyacencia";//nombre del archivo
			String codigo=respuesta.readEntity(String.class);//recuperamos codigo graphviz
			String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
			Archivo.generarGrafica(path, nombre, codigo);//generamos la grafica
			String base64 = Archivo.graficaBase64(path, nombre);//recuperamos la grafica en base64
			if(base64!=null)//si todo salio bien
			{
				String imagen="<img src=\"data:image/png;base64, ";
				imagen+=base64+"\" alt=\"Imagen no disponible, intente volver a graficar\" width=\"100%\"/>";
				request.setAttribute("imagen", imagen);//generamos un string para imagen html y lo agregamos como atributo
				request.getRequestDispatcher("pensum.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("imagen", "No se pudo generar la grafica");
				request.getRequestDispatcher("pensum.jsp").forward(request, response);
			}
		}else
		{
			request.setAttribute("imagen", "Error con el servicio rest");
			request.getRequestDispatcher("pensum.jsp").forward(request, response);
		}
	}

}
