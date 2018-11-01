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
import org.jocaqes.Misc.Alumno;
import org.jocaqes.Misc.Alumnos;
import org.jocaqes.Misc.Archivo;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Estudiante;
import org.jocaqes.Misc.Tutor;

/**
 * Servlet implementation class TutorAlumnos
 */
@WebServlet("/Tutor/alumnos")
public class TutorAlumnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TutorAlumnos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("alumnos.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
		if(tipo.equals("add"))
			agregarAlumno(request,response);
		else if(tipo.equals("load"))
			cargarAlumnos(request,response);
		else if(tipo.equals("graph"))
			graficaAlumnos(request,response);
		else
			response.sendRedirect("alumnos.jsp");
	}

	private void cargarAlumnos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne_tutor=Integer.parseInt(request.getParameter("tutor"));
		Tutor tutor=DataBase.getTutores().buscar(carne_tutor);
		if(tutor==null)
		{
			request.setAttribute("load", "Se ha acabado el tiempo de esta sesion");
			request.getRequestDispatcher("alumnos.jsp").forward(request, response);
		}
		else
		{
			String ruta=request.getParameter("ruta");
			String json = Archivo.leer(ruta);
			WebTarget cargar=RestClient.getTutor_target()
					.path("cargar/alumnos");
			Response respuesta = cargar
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.json(json));
			int status=respuesta.getStatus();
			if(status==Status.OK.getStatusCode())
			{
				Alumnos alumnos=respuesta.readEntity(Alumnos.class);
				int carne_alumno=0;
				boolean todo_bien=true;
				while(!alumnos.getEstudiantes().isEmpty()&&todo_bien)
				{
					carne_alumno=alumnos.getEstudiantes().remove(0);
					Estudiante alumno = DataBase.getBtreeEstudiantes().buscar(carne_alumno);
					if(alumno!=null)
					{
						if(DataBase.getPensum().getCurso(tutor.getCurso())==null)
						{
							todo_bien=false;
						}
						else
						{
							Alumno nuevo=new Alumno(alumno.getCarnet(), alumno.getNombre(), alumno.getApellidos(), alumno.getCorreo());//nuevo
							DataBase.getPensum().getCurso(tutor.getCurso()).getAlumnos().insertar(nuevo, alumno.getNombre());//insertar a tabla hash
							tutor.getControlNotas().addRow(String.valueOf(alumno.getCarnet()), alumno.getCarnet());//agregar estudiante a la matriz de adyacencia
						}
					}
				}
				if(todo_bien)
				{
					graficar(tutor.getCarnet());//nuevo
					request.setAttribute("load", "Alumnos agregados con exito!!");
					request.getRequestDispatcher("alumnos.jsp").forward(request, response);
				}
				else
				{
					request.setAttribute("load", "Como tutor no tiene asignado ese curso");
					request.getRequestDispatcher("alumnos.jsp").forward(request, response);
				}
			}
			else
			{
				request.setAttribute("load", "No se pudieron cargar los alumnos, revise que el formato de su archivo sea el correcto");
				request.getRequestDispatcher("alumnos.jsp").forward(request, response);
			}
		}
				
	}

	private void graficaAlumnos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String graph=buscarGrafica();
		request.setAttribute("graph", graph);
		request.getRequestDispatcher("alumnos.jsp").forward(request, response);
	}

	private void agregarAlumno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne_tutor=Integer.parseInt(request.getParameter("tutor"));
		int carne_alumno=toInt(request.getParameter("id"));
		if(carne_alumno<0)
		{
			request.setAttribute("add", "Este campo solo acepta formato numerico");
			request.getRequestDispatcher("alumnos.jsp").forward(request, response);
		}
		else
		{
			Tutor tutor=DataBase.getTutores().buscar(carne_tutor);
			if(tutor==null)
			{
				request.setAttribute("add", "Se ha acabado el tiempo de esta sesion");
				request.getRequestDispatcher("alumnos.jsp").forward(request, response);
			}
			else
			{
				Estudiante alumno = DataBase.getBtreeEstudiantes().buscar(carne_alumno);
				if(alumno==null)
				{
					request.setAttribute("add", "Ese estudiante no se encuentra en la base de datos");
					request.getRequestDispatcher("alumnos.jsp").forward(request, response);
				}
				else
				{
					if(DataBase.getPensum().getCurso(tutor.getCurso())==null)
					{
						request.setAttribute("add", "Como tutor no tienes asignado este curso");
						request.getRequestDispatcher("alumnos.jsp").forward(request, response);
					}
					else
					{
						Alumno nuevo=new Alumno(alumno.getCarnet(), alumno.getNombre(), alumno.getApellidos(), alumno.getCorreo());//nuevo
						DataBase.getPensum().getCurso(tutor.getCurso()).getAlumnos().insertar(nuevo, alumno.getNombre());//insertar a tabla hash
						tutor.getControlNotas().addRow(String.valueOf(alumno.getCarnet()), alumno.getCarnet());//agregar estudiante a la matriz de adyacencia
						graficar(tutor.getCarnet());//nuevo
						request.setAttribute("add", "Alumno agregado con exito");
						request.getRequestDispatcher("alumnos.jsp").forward(request, response);
					}
				}
			}
		}
	}
	
	private void graficar(int carne)
	{
		String nombre="hash";//nombre archivo
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
			return;
		String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
		if(DataBase.getPensum().getCurso(tutor.getCurso())==null)
			return;
		String codigo=DataBase.getPensum().getCurso(tutor.getCurso()).getAlumnos().codigoGrafica();
		Archivo.generarGrafica(path, nombre, codigo);
		nombre="matriz_"+carne;
		codigo=DataBase.getTutores().buscar(carne).graficaControlNotas();
		Archivo.generarGrafica(path, nombre, codigo);//de paso actualizo la matriz de notas
	}
	private String buscarGrafica()
	{
		String nombre="hash";
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
