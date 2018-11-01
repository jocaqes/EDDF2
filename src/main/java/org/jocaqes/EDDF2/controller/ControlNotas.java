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
import org.jocaqes.Misc.Actividad;
import org.jocaqes.Misc.Archivo;
import org.jocaqes.Misc.Actividad.Tipo;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Tutor;

/**
 * Servlet implementation class ControlNotas
 */
@WebServlet("/Tutor/controlnotas")
public class ControlNotas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlNotas() {
        super();
    }
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
		if(tipo.equals("add"))
			agregarActividad(request,response);
		else if(tipo.equals("load"))
			cargarActividades(request,response);
		else if(tipo.equals("graph"))
			graficaActividades(request,response);
		else if(tipo.equals("delete"))
			eliminarActividad(request,response);
		else
			response.sendRedirect("controlnotas.jsp");
	}
    
    
	private void eliminarActividad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne=Integer.parseInt(request.getParameter("carne"));
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
		{
			request.setAttribute("delete", "Error, se acabo el tiempo de su sesion");
			request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
		}
		else
		{
			String actividad=request.getParameter("nombre");
			if(tutor.eliminarActividad(actividad))
			{
				graficar(carne);
				request.setAttribute("delete", "Actividad eliminada con Exito!!");
				request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("delete", "Error, no se pudo eliminar la actividad");
				request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
			}
		}
	}


	private void graficaActividades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne=Integer.parseInt(request.getParameter("carne"));
		String graph=buscarGrafica(carne);
		request.setAttribute("graph", graph);
		request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
	}

	
	private void graficar(int carne)
	{
		String nombre="matriz_"+carne;//nombre del archivo
		String codigo=DataBase.getTutores().buscar(carne).graficaControlNotas();//recuperamos codigo graphviz
		String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
		Archivo.generarGrafica(path, nombre, codigo);//generamos la grafica
	}
	private String buscarGrafica(int carne)
	{
		String nombre="matriz_"+carne;
		String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
		String base64 = Archivo.graficaBase64(path, nombre);//recuperamos la grafica en base64
		if(base64!=null)//si todo salio bien
		{
			String imagen="<img class=\"zoom\" src=\"data:image/png;base64, ";
			imagen+=base64+"\" alt=\"Imagen no disponible\"/>";
			return imagen;//generamos un string para imagen html y lo agregamos como atributo
		}
		else 
			return "No se pudo generar la Grafica"; 
	}

	private void cargarActividades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne=Integer.parseInt(request.getParameter("carne"));
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
		{
			request.setAttribute("load", "Error, se acabo el tiempo de su sesion");
			request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
		}
		else
		{
			String ruta=request.getParameter("ruta");
			WebTarget cargar=RestClient.getTutor_target()
					.path("cargar/{carne}");
			String json = Archivo.leer(ruta);
			if(json==null||json.isEmpty())
			{
				request.setAttribute("load", "El archivo que proporciono no existe o esta vacio");
				request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
			}
			else
			{
				Response respuesta = cargar
						.resolveTemplate("carne", carne)
						.request(MediaType.TEXT_PLAIN)
						.post(Entity.json(json));
				int status = respuesta.getStatus();
				if(status==Status.CREATED.getStatusCode())
				{
					graficar(carne);//nuevo
					request.setAttribute("load",respuesta.readEntity(String.class));
					request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
				}
				else//Forbidden||bad_request
				{
					request.setAttribute("load","Formato del archivo incorrecto");
					request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
				}
			}
		}
	}


	private void agregarActividad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne = Integer.parseInt(request.getParameter("carne"));
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		int ponderacion = toInt(request.getParameter("ponderacion"));
		String fecha_entrega = request.getParameter("fecha_entrega");
		String tipo = request.getParameter("tipo_actividad");
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
		{
			request.setAttribute("add", "Error no se pudo entontrar tu hoja de control de notas");
			request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
		}
		else
		{
			Actividad nueva = new Actividad(nombre, descripcion, ponderacion, fecha_entrega, Tipo.valueOf(tipo));
			if(tutor.agregarActividad(nueva))
			{
				graficar(carne);//nuevo
				request.setAttribute("add", "Actividad agregada con Exito!!");
				request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("add", "Error, ya existe una actividad con ese nombre");
				request.getRequestDispatcher("controlnotas.jsp").forward(request, response);
			}
		}
		
		/**
		 * 	private String nombre;
	private String descripcion;
	private int ponderacion;
	private String fecha_entrega;
	private Tipo tipo;
		 */
		
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("controlnotas.jsp");
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
