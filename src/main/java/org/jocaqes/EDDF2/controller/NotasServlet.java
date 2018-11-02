package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jocaqes.Misc.Archivo;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Tutor;

/**
 * Servlet implementation class NotasServlet
 */
@WebServlet("/Tutor/notas")
public class NotasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotasServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("notas.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
		if(tipo.equals("add"))
			agregarNota(request,response);
		else if(tipo.equals("modify"))
			moficiarNota(request,response);
		else if(tipo.equals("load"))
			cargarNotas(request,response);
		else
			response.sendRedirect("notas.jsp");
	}

	private void cargarNotas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne_tutor=toInt(request.getParameter("tutor"));
		Tutor tutor=DataBase.getTutores().buscar(carne_tutor);
		if(tutor==null)
		{
			request.setAttribute("load", "Se ha acabado el tiempo de esta sesion");
			request.getRequestDispatcher("notas.jsp").forward(request, response);
		}
		else
		{
			String ruta=request.getParameter("ruta");
			String actividad=request.getParameter("actividad");
			String notas=Archivo.leer(ruta);
			String alumnos[]=notas.split("\n");
			String valores[];
			int carne=0;
			int nota=0;
			for(String alumno:alumnos)
			{
				valores=alumno.split(",");
				if(valores.length>1)
				{
					carne=toInt(valores[0]);
					nota=toInt(valores[1]);
					tutor.agregarNota(actividad, carne, nota);
				}
			}
			graficar(carne_tutor);
			request.setAttribute("load", "Carga de notas exitosa");
			request.getRequestDispatcher("notas.jsp").forward(request, response);
		}
	}

	private void moficiarNota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne_tutor=toInt(request.getParameter("tutor"));
		int carne_alumno=toInt(request.getParameter("alumno"));
		String actividad=request.getParameter("actividad");
		int ponderacion=toInt(request.getParameter("calificacion"));
		if(carne_alumno<0||carne_tutor<0||ponderacion<0)
		{
			request.setAttribute("modify", "Este campo solo acepta formato numerico");
			request.getRequestDispatcher("notas.jsp").forward(request, response);
		}
		else
		{
			Tutor tutor=DataBase.getTutores().buscar(carne_tutor);
			if(tutor==null)
			{
				request.setAttribute("modify", "Se ha acabado el tiempo de esta sesion");
				request.getRequestDispatcher("notas.jsp").forward(request, response);
			}
			else
			{
				if(tutor.modificarNota(actividad, carne_alumno, ponderacion))
				{
					graficar(carne_tutor);
					request.setAttribute("modify", "Nota modificada con exito");
					request.getRequestDispatcher("notas.jsp").forward(request, response);
				}
				else
				{
					request.setAttribute("modify", "Ya existe una nota para ese alumno en esa actividad");
					request.getRequestDispatcher("notas.jsp").forward(request, response);
				}
			}
		}
	}

	private void agregarNota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carne_tutor=toInt(request.getParameter("tutor"));
		int carne_alumno=toInt(request.getParameter("alumno"));
		String actividad=request.getParameter("actividad");
		int ponderacion=toInt(request.getParameter("calificacion"));
		if(carne_alumno<0||carne_tutor<0||ponderacion<0)
		{
			request.setAttribute("add", "Este campo solo acepta formato numerico");
			request.getRequestDispatcher("notas.jsp").forward(request, response);
		}
		else
		{
			Tutor tutor=DataBase.getTutores().buscar(carne_tutor);
			if(tutor==null)
			{
				request.setAttribute("add", "Se ha acabado el tiempo de esta sesion");
				request.getRequestDispatcher("notas.jsp").forward(request, response);
			}
			else
			{
				if(!tutor.agregarNota(actividad, carne_alumno, ponderacion))
				{
					request.setAttribute("add", "Ya existe una nota para ese alumno en esa actividad");
					request.getRequestDispatcher("notas.jsp").forward(request, response);
				}
				else
				{
					graficar(carne_tutor);
					request.setAttribute("add", "Nota agregada con exito");
					request.getRequestDispatcher("notas.jsp").forward(request, response);
				}
			}
		}
		
	}
	
	
	private void graficar(int carne)
	{
		String nombre="matriz_"+carne;//nombre archivo
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
			return;
		String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
		if(DataBase.getPensum().getCurso(tutor.getCurso())==null)
			return;
		String codigo=DataBase.getTutores().buscar(carne).graficaControlNotas();
		Archivo.generarGrafica(path, nombre, codigo);//de paso actualizo la matriz de notas
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
			System.out.println("En NotasServlet->toInt->buscarEstudiante:"+e.getMessage());
			return out;
		}
	}

}
