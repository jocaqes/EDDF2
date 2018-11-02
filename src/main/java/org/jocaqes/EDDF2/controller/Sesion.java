package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.restclient.RestClient;
import org.jocaqes.Misc.DataBase;

/**
 * Servlet implementation class SessionServlet; para el manejo de login
 */
//@WebServlet("/SessionLogin")
public class Sesion extends HttpServlet {
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
			HttpSession session = request.getSession(false);
			session.setAttribute("user", usuario);
			//debug
			/*String path=getServletContext().getRealPath("/Imagenes");
			System.out.println(path);
			request.setAttribute("path", path);
			request.getRequestDispatcher("Admin/HomeAdmin.jsp").forward(request, response);*/
			response.sendRedirect("Admin/HomeAdmin.jsp");
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
				WebTarget search=RestClient.getMisc_target()
						.path("login/{carne}/{password}");//path para llamar rest buscar alumno en arbol B  
				Response respuesta=search
						.resolveTemplate("carne", usuario)
						.resolveTemplate("password", password)
						.request(MediaType.TEXT_PLAIN)
						.get();//la verdad solo me interesa el status code, el contenido es un booleano pero con el codigo ya se si funciona o no
				int status = respuesta.getStatus();
				if(status==Status.ACCEPTED.getStatusCode())//match entre usuario y password
				{
					int carne=Integer.parseInt(usuario);
					HttpSession session = request.getSession(false);
					session.setAttribute("user", usuario);
					if(DataBase.getTutores().existe(carne))
						response.sendRedirect("Tutor/homeTutor.jsp");
					else
						response.sendRedirect("Estudiante/homeEstudiante.jsp");
				}
				else//aqui es un Status.UNAUTHORIZED pero la verdad es irrelevante porque no puedo dejar que la pagina se caiga
				{
					request.setAttribute("mensaje", "La contraseña o el usuario no es valido");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		}
	}


}
