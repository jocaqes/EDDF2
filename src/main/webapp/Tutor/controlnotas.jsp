<%@page import="org.jocaqes.Misc.Tutor"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
<%@page import="org.jocaqes.Misc.Actividad"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="tutorstyle.css">
<meta charset="UTF-8">
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
	<h1>Administracion de Actividades</h1>
	<br>
	<jsp:include page="sidebar.html"/>
	<br>
	<fieldset>
	 	<legend>Ponderacion Actual</legend>
	 	<%
	 		int ponderacion=yo.getPonderacionTotal();
	 		if(ponderacion<100)
	 		{
	 			out.print("<h3> Tus actividades suman menos de 100 puntos</h3>");
	 			out.print("<h2 class=\"below\"");
	 		}
	 		else if(ponderacion==100)
	 			out.print("<h2 class=\"ok\"");
	 		else
	 		{
	 			out.print("<h3> Tus actividades suman mas de 100 puntos</h3>");
	 			out.print("<h2 class=\"above\"");
	 		}
	 		out.print(">");
	 		out.print(ponderacion);
	 		out.print("</h2>");
	 	%>
	</fieldset>
	<fieldset>
		<legend>Cargar Actividades</legend>
		<form method="post" action="controlnotas">
			Ruta del archivo:<br>
			<input type="text" name="ruta" size="35"><br>
			<input type="hidden" name="tipo" value="load">
			<input type="hidden" name="carne" value=<%=session.getAttribute("user")%>>
			<input type="submit" value="Cargar"> 
		</form>
		<br>
		${load }
	</fieldset>
	<br>
	<fieldset>
		<legend>Agregar Actividad</legend>
		<form method="post" action="controlnotas">
			Nombre de la actividad:<br>
			<input type="text" name="nombre"><br>
			Descripcion de la actividad:<br>
			<textarea rows="10" cols="45" name="descripcion">Agrege una descripcion de la activiadd...</textarea><br>
			Ponderacion:<br>
			<input type="number" name="ponderacion"><br>
			Fecha de entrega:<br>
			<input type="date" name="fecha_entrega"><br>
			Tipo de actividad:
			<select name="tipo_actividad">
			<%
				for(Actividad.Tipo tipo:Actividad.Tipo.values())
				{
					out.print("<option>");
					out.print(tipo.toString());
					out.println("</option>");
				}
			%>			
			</select><br>
			<input type="hidden" name="tipo" value="add">
			<input type="hidden" name="carne" value=<%=session.getAttribute("user")%>>
			<input type="submit" value="Agregar">
		</form>
		<br>
		${add }
	</fieldset>
	<br>
	<fieldset>
		<legend>Modificar Actividad</legend>
		<form method="post" action="controlnotas">
			Seleccione la actividad que desea modificar:<br>
			<select name="nombre">
				<%
					out.println(yo.actividades());
				%>
			</select><br>
			<input type="hidden" name="tipo" value="form">
			<input type="hidden" name="carne" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Buscar">
		</form>
		${form }
		${modify }
		<br>
	</fieldset>
	<br>
	<fieldset>
		<legend>Eliminar Actividad</legend>
		<form method="post" action="controlnotas">
			Seleccione la actividad que desea eliminar:<br>
			<select name="nombre">
			<%
				out.println(yo.actividades());
			%>
			</select>
			<input type="hidden" name="tipo" value="delete">
			<input type="hidden" name="carne" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Eliminar">
		</form>
		${delete }
	</fieldset>
	<br>
	<fieldset>
		<legend>Grafica de Actividades</legend>
		<form method="post" action="controlnotas">
			<input type="hidden" name="tipo" value="graph">
			<input type="hidden" name="carne" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Graficar">
		</form>
		${graph }
	</fieldset>
</body>
</html>