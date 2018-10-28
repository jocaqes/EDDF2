package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.restclient.RestClient;

/**
 * Servlet implementation class SessionServlet para el manejo de login
 */
@WebServlet("/SessionLogin")
public class SessionLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recuperamos los parametros
		String usuario=request.getParameter("usuario");
		String password=request.getParameter("password");
		//Revisamos por null
		if(usuario==null||usuario.isEmpty()||password==null||password.isEmpty())
		{
			request.setAttribute("mensaje", "La contraseña o el usuario no es valido");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		//Revisamos por admin
		else if(usuario.equals("ADMIN")&&password.equals("140918"))//contraseña para ADMIN 140918
		{
			HttpSession session = request.getSession();
			session.setAttribute("user", usuario);
			response.sendRedirect("test.jsp");
		}
		//Revisamos por alumno
		else {
			if(usuario.equals("ADMIN"))//admin es el unico que pone texto como usuario, pero la llamada al api y busqueda en arbol b no aceptan string
			{
				//asi que lo descarto
				request.setAttribute("mensaje", "La contraseña o el usuario no es valido");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else
			{
				WebTarget search=RestClient.getAdmin_target().path("login/{carne}/{password}");//path para llamar rest buscar alumno en arbol B  
				Response respuesta=search
						.resolveTemplate("carne", usuario)
						.resolveTemplate("password", password)
						.request(MediaType.TEXT_PLAIN)
						.get();//la verdad solo me interesa el status code, el contenido es un booleano pero con el codigo ya se si funciona o no
				int status = respuesta.getStatus();
				if(status==Status.ACCEPTED.getStatusCode())//match entre usuario y password
				{
					HttpSession session = request.getSession();
					session.setAttribute("user", usuario);
					response.sendRedirect("test.jsp");
				}
				else//aqui es un Status.UNAUTHORIZED pero la verdad es irrelevante
				{
					request.setAttribute("mensaje", "La contraseña o el usuario no es valido");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		}
	}
	
	/*
	private static String buscarEstudiante(String carnet)
	{
		WebTarget search=admin_target.path("buscar/{carne}");
		Response response = search
				.resolveTemplate("carne", carnet)
				.request(MediaType.APPLICATION_JSON)
				.get();
		if(response.getStatus()==Status.FOUND.getStatusCode())
		{
			return response.readEntity(String.class);
		}else//404
		{
			return response.readEntity(String.class);
		}
	}
*/

}
