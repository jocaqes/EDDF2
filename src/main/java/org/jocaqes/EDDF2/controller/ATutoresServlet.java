package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jocaqes.Misc.Archivo;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Estudiante;
import org.jocaqes.Misc.Tutor;

/**
 * Servlet implementation class ATutoresServlet
 */
@WebServlet("/Admin/tutores")
public class ATutoresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("tutores.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo=request.getParameter("tipo");
		if(tipo.equals("add"))
			agregarTutor(request,response);
		else if(tipo.equals("graph"))
			graficaAVL(request,response);
		else if(tipo.equals("search"))
			buscarTutor(request,response);
		else if(tipo.equals("delete"))
			eliminarTutor(request,response);
		else
			response.sendRedirect("tutores.jsp");
	}

	private void eliminarTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carnet=toInt(request.getParameter("carne"));
		if(carnet<0)
		{
			request.setAttribute("delete", "Este campo solo acepta valores numericos");
			request.getRequestDispatcher("tutores.jsp").forward(request, response);
		}
		else
		{
			if(DataBase.getTutores().eliminar(carnet))
			{
				request.setAttribute("delete", "Tutor removido con exito");
				request.getRequestDispatcher("tutores.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("delete", "Lo sentimos, no se encontro al tutor");
				request.getRequestDispatcher("tutores.jsp").forward(request, response);
			}
		}
	}

	private void buscarTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int carnet=toInt(request.getParameter("carne"));
		if(carnet<0)
		{
			request.setAttribute("search", "Este campo solo acepta valores numericos");
			request.getRequestDispatcher("tutores.jsp").forward(request, response);
		}
		else
		{
			Tutor tutor=DataBase.getTutores().buscar(carnet);
			if(tutor!=null)
			{
				Estudiante extra_info=DataBase.getBtreeEstudiantes().buscar(carnet);
				String search="";
				search+="<h4>Nombre:</h4>"+extra_info.getNombre()+" "+extra_info.getApellidos()+"<br>";
				search+="<h4>Carnet:</h4>"+carnet+"<br>";
				search+="<h4>Creditos:</h4>"+extra_info.getCreditos()+"<br>";
				search+="<h4>Curso que imparte:</h4>"+tutor.getCurso()+"<br>";
				search+="<h4>Periodo:</h4>"+tutor.getPeriodo()+"<br>";
				request.setAttribute("search", search);
				request.getRequestDispatcher("tutores.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("search", "Lo sentimos, no se encontro al tutor");
				request.getRequestDispatcher("tutores.jsp").forward(request, response);
			}
		}
	}

	private void graficaAVL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nombre="arbolavl";//nombre del archivo
		String codigo=DataBase.getTutores().graficar();//recuperamos codigo graphviz
		String path=getServletContext().getRealPath("/Imagenes");//conseguimos path para guardar el archivo
		Archivo.generarGrafica(path, nombre, codigo);//generamos la grafica
		String base64 = Archivo.graficaBase64(path, nombre);//recuperamos la grafica en base64
		if(base64!=null)//si todo salio bien
		{
			String imagen="<img class=\"zoom\" src=\"data:image/png;base64, ";
			imagen+=base64+"\" alt=\"Imagen no disponible\"/>";
			request.setAttribute("graph", imagen);//generamos un string para imagen html y lo agregamos como atributo
		}
		request.getRequestDispatcher("tutores.jsp").forward(request, response);
	}

	private void agregarTutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int carnet = Integer.parseInt(request.getParameter("tutor"));
		String curso=request.getParameter("curso");
		System.out.println(curso);
		int periodo=Integer.parseInt(request.getParameter("periodo"));
		Tutor nuevo = new Tutor(carnet, curso, periodo);
		if(DataBase.getTutores().insertar(nuevo, carnet))
		{
			request.setAttribute("add", "El tutor fue asignado con exito");
			request.getRequestDispatcher("tutores.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("add", "El tutor ya tiene asignado un curso");
			request.getRequestDispatcher("tutores.jsp").forward(request, response);
		}
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
