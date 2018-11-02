package org.jocaqes.EDDF2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jocaqes.Misc.Pregunta;
import org.jocaqes.Misc.Pregunta.TipoPregunta;


/**
 * Servlet implementation class EdicionCortoServlet
 */
@WebServlet("/Tutor/edicioncorto")
public class EdicionCortoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo=request.getParameter("tipo");
		if(tipo==null)
			startForm(request,response);
		else
			response.sendRedirect("edicioncorto.jsp");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo=request.getParameter("tipo");
		if(tipo==null)
			startForm(request,response);
		else if(tipo.equals("Agregar"))
			agregarOpcion(request,response);
		else if(tipo.equals("Actualizar"))
			actualizarFormulario(request,response);
		else if(tipo.equals("Cancelar"))
			cancelarTodo(request,response);
		else if(tipo.equals("Aceptar"))
			regresarPregunta(request,response);
		else
		{
			response.sendRedirect("edicioncorto.jsp");
		}
	}
	
	

	private void regresarPregunta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pregunta pregunta=(Pregunta)request.getSession().getAttribute("pregunta");
		if(pregunta!=null)
		{
			pregunta.setPregunta(request.getParameter("question"));
			pregunta.setRespuesta(request.getParameter("respuesta"));
			request.getSession().setAttribute("pregunta", pregunta);
		}
		String formulario="";
		formulario+="<input type=\"hidden\" name=\"tipo\" value=\"three\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Agregar Pregunta\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Guardar\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Cancelar\">";
		request.getSession().setAttribute("formulario", formulario);
		response.sendRedirect("cortos");
	}

	private void cancelarTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("pregunta");
		String formulario="";
		formulario+="<input type=\"hidden\" name=\"tipo\" value=\"three\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Agregar Pregunta\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Guardar\">";
		formulario+="<input type=\"submit\" name=\"option\" value=\"Cancelar\">";
		request.getSession().setAttribute("formulario", formulario);
		response.sendRedirect("cortos");
		//request.getRequestDispatcher("cortos.jsp").forward(request, response);
		//response.sendRedirect("cortos.jsp");
	}

	private void actualizarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pregunta pregunta=(Pregunta)request.getSession().getAttribute("pregunta");
		if(pregunta==null)
		{
			System.out.println("la pregunta es null");
			request.getRequestDispatcher("edicioncorto.jsp").forward(request, response);
		}
		else
		{
			pregunta.setTipo(TipoPregunta.valueOf(request.getParameter("tipo_pregunta")));
			pregunta.setPregunta(request.getParameter("question"));
			pregunta.setRespuesta(request.getParameter("respuesta"));
			request.getSession().setAttribute("pregunta", pregunta);
			request.setAttribute("formulario", formulario(pregunta));
			request.getRequestDispatcher("edicioncorto.jsp").forward(request, response);
		}
	}

	private void agregarOpcion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pregunta pregunta=(Pregunta)request.getSession().getAttribute("pregunta");
		if(pregunta!=null)
		{
			pregunta.addOpcion(request.getParameter("opcion"));
			pregunta.setPregunta(request.getParameter("question"));
			request.setAttribute("formulario", formulario(pregunta));
			request.getSession().setAttribute("pregunta", pregunta);
		}
		request.getRequestDispatcher("edicioncorto.jsp").forward(request, response);
		
		
	}

	private void startForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pregunta pregunta=(Pregunta)request.getSession().getAttribute("pregunta");
		if(pregunta!=null)
			request.setAttribute("formulario", formulario(pregunta));
		request.getRequestDispatcher("edicioncorto.jsp").forward(request, response);
	}
	



	
	
	
	private String formulario(Pregunta pregunta)
	{
		String formulario="";
		formulario+="<table>";
		formulario+="<tr>";
		formulario+="<td>Tipo</td>";
		formulario+="<td>";
		formulario+="<select name=\"tipo_pregunta\">";
		formulario+=pregunta.getSelectTipo();
		formulario+="</select>";
		formulario+="</td>";
		formulario+="</tr>";
		formulario+="<tr>";
		formulario+="<td>Pregunta:</td>";
		formulario+="<td>";
		formulario+="<input type=\"text\" name=\"question\" size=\"70\" value=\""+pregunta.getPregunta()+"\">";
		formulario+="</td>";
		formulario+="</tr>";
		if(pregunta.getTipo()==TipoPregunta.VERDADERO_FALSO)
		{
			formulario+="<tr>";
			formulario+="<td>";
			formulario+="Respuesta:";
			formulario+="</td>";
			formulario+="<td>";
			formulario+="<input type=\"radio\" name=\"respuesta\" value=\"true\">Verdadero ";
			formulario+="<input type=\"radio\" name=\"respuesta\" value=\"false\">Falso ";
			formulario+="</td>";
			formulario+="</tr>";
		}else
		{
			formulario+=pregunta.getTextOpcines();
			formulario+="<tr>";
			formulario+="<td>";
			formulario+="<input type=\"submit\" name=\"tipo\" value=\"Agregar\">";
			formulario+="</td>";
			formulario+="<td>";
			formulario+="<input type=\"text\" name=\"opcion\">";
			formulario+="</td>";
			formulario+="</tr>";
		}
		formulario+="<tr>";
		formulario+="<td></td>";
		formulario+="<td>";
		formulario+="<input type=\"submit\" name=\"tipo\" value=\"Actualizar\">";
		formulario+="<input type=\"submit\" name=\"tipo\" value=\"Aceptar\">";
		formulario+="<input type=\"submit\" name=\"tipo\" value=\"Cancelar\">";
		formulario+="</td>";
		formulario+="</tr>";
		formulario+="</table>";
		return formulario;
	}
}
