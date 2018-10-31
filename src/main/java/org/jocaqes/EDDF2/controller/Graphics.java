package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.restclient.RestClient;
import org.jocaqes.Misc.Archivo;

/**
 * Servlet implementation class GraphManagement
 */
@WebServlet("/Admin/graph")
public class Graphics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.sendRedirect("estudiantes.jsp");
		WebTarget graph=RestClient.getMisc_target()
				.path("/btree");
		Response respuesta = graph
				.request(MediaType.TEXT_PLAIN)
				.get();
		int code=respuesta.getStatus();
		if(code==Status.OK.getStatusCode())
		{
			String nombre="arbolB";//nombre del archivo
			String codigo=respuesta.readEntity(String.class);//recuperamos codigo graphviz
			String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
			Archivo.generarGrafica(path, nombre, codigo);//generamos la grafica
			String base64 = Archivo.graficaBase64(path, nombre);//recuperamos la grafica en base64
			if(base64!=null)//si todo salio bien
			{
				String imagen="<img src=\"data:image/png;base64, ";
				imagen+=base64+"\" alt=\"Imagen no disponible\"/>";
				request.setAttribute("imagen", imagen);//generamos un string para imagen html y lo agregamos como atributo
			}
		}
		request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
