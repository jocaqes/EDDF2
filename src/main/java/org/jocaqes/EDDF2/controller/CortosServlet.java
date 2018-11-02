package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jocaqes.Misc.Corto;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Pregunta;

/**
 * Servlet implementation class CortosServlet
 */
@WebServlet("/Tutor/cortos")
public class CortosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CortosServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("cortos.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo=request.getParameter("tipo");
		if(tipo.equals("nuevo"))
			formNuevoCorto(request,response);
		else if(tipo.equals("generate"))
			formCortoGenerado(request,response);
		else if(tipo.equals("three"))
			threeOption(request,response);
		else
			response.sendRedirect("cortos.jsp");
	}

	private void threeOption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option=request.getParameter("option");
		if(option.equals("Agregar Pregunta"))
		{
			request.getSession().setAttribute("pregunta", new Pregunta());
			response.sendRedirect("edicioncorto");
		}
		else if(option.equals("Guardar"))
		{
			Corto corto=(Corto)request.getSession().getAttribute("corto");
			DataBase.getCortos().agregarCorto(corto);
			request.getSession().removeAttribute("corto");
			request.setAttribute("guardar", "Corto guardado!");
			request.getRequestDispatcher("cortos.jsp").forward(request, response);
		}
		else//Cancelar
		{
			request.removeAttribute("corto");
			response.sendRedirect("homeTutor.jsp");
		}
	}

	private void formCortoGenerado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tutor=toInt(request.getSession().getAttribute("user").toString());
		String formulario="";
		formulario+="Corto creado!!<br>";
		formulario+="<input type=\"hidden\" name=\"tipo\" value=\"three\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Agregar Pregunta\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Guardar\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Cancelar\">";
		Corto corto=(Corto)request.getSession().getAttribute("corto");
		if(corto!=null)
		{
			corto.setNombre(request.getParameter("nombre"));
			corto.setRoom(request.getParameter("room"));
			corto.setTutor(tutor);
			request.getSession().setAttribute("corto", corto);
		}
		request.setAttribute("generate", formulario);
		request.getRequestDispatcher("cortos.jsp").forward(request, response);
	}

	private void formNuevoCorto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formulario="";
		formulario+="<table>";
		formulario+="<tr>";
		formulario+="<td>";
		formulario+="Nombre del Corto";
		formulario+="</td>";
		formulario+="<td>";
		formulario+="<input type=\"text\" name=\"nombre\">";
		formulario+="</td>";
		formulario+="</tr>";
		formulario+="<tr>";
		formulario+="<td>";
		formulario+="Nombre del Room";
		formulario+="</td>";
		formulario+="<td>";
		formulario+="<input type=\"text\" name=\"room\">";
		formulario+="</td>";
		formulario+="</tr>";
		formulario+="</table>";
		formulario+="<input type=\"hidden\" name=\"tipo\" value=\"generate\">";
		formulario+="<input type=\"submit\" value=\"Generar\">";
		Corto nuevo = new Corto();
		request.getSession().setAttribute("corto", nuevo);
		request.setAttribute("nuevo", formulario);
		request.getRequestDispatcher("cortos.jsp").forward(request, response);
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
