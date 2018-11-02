<%@page import="org.jocaqes.Misc.Tutor"%>
<%@page import="org.jocaqes.Misc.Pregunta"%>
<%@page import="org.jocaqes.Misc.Corto"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>Tutor</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	Tutor yo=null;
	if(session.getAttribute("user")==null)
		response.sendRedirect("../index.jsp");
	else
		yo = DataBase.getTutores().buscar(Integer.parseInt(session.getAttribute("user").toString()));
%>
<a href="../logout.jsp">Logout</a>
<h1>Creacion de Cortos</h1>
	<fieldset>
		<legend>Nuevo Corto</legend>
		<form  method="post" action="cortos">
			<%
				if(session.getAttribute("corto")==null)
				{
					out.print("<input type=\"submit\" value=\"Nuevo\">");
					out.print("<input type=\"hidden\" name=\"tipo\" value=\"nuevo\">");
				}
			%>
			${nuevo }
			${generate }
			${guardar }
			<%
				if(session.getAttribute("formulario")!=null)
				{
					out.println(session.getAttribute("formulario").toString());
					session.removeAttribute("formulario");
				}
			%>
			<%
				if(session.getAttribute("pregunta")!=null&&session.getAttribute("corto")!=null)
				{
					Corto corto=(Corto)session.getAttribute("corto");
					corto.addPregunta((Pregunta)session.getAttribute("pregunta"));
					out.print("<br>");
					out.println(((Pregunta)session.getAttribute("pregunta")).getPregunta());
					session.removeAttribute("pregunta");
					session.setAttribute("corto", corto);
					out.print("<br>");
					out.println("Pregunta agregada con Exito!!");					
				}
			%>
		</form>
	</fieldset>
	<br>
	<fieldset>
		<legend>Habilitar o Desabilitar cortos</legend>
		<form method="post" action="cortos">
			<select name="nombre_corto">
				<%=DataBase.getCortos().selectCortosTutor(Integer.parseInt(session.getAttribute("user").toString())) %>
			</select>
			<input type="radio" name="habilitar" value="1">Habilitar<br>
			<input type="radio" name="habilitar" value="0">Deshabilitar<br>
			<input type="hidden" name="tipo" value="enable">
			<input type="submit" value="OK">
		</form>
	</fieldset>	
	<br>
	<fieldset>
		<legend>Grafica</legend>
		<form method="post" action="cortos">
			<input type="hidden" name="tipo" value="graph">
			<input type="button" value="Graficar">
		</form>
		${graph }
		<br>
	</fieldset>
</body>
</html>