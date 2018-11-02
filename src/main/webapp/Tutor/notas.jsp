<%@page import="org.jocaqes.Misc.Tutor"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="tutorstyle.css">
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
	<h1>Control de Notas</h1>
	<br>
	<jsp:include page="sidebar.html"/>
	<br>
	<fieldset>
		<legend>Cargar Notas</legend>
		<form method="post" action="notas">
			Actividad:<br>
			<select name="actividad">
				<%=yo.getActividades() %>
			</select><br>
			Ruta del archivo:<br>
			<input type="text" name="ruta" size="35"><br>
			<input type="hidden" name="tipo" value="load">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user")%>>
			<input type="submit" value="Cargar"> 
		</form>
		${load }
	</fieldset>
	<br>
	<fieldset>
		<legend>Agregar Nota</legend>
		<form method="post" action="notas">
		<table>
		<tr>
			<td>Actividad</td>
			<td>
			<select name="actividad">
				<%
					out.println(yo.getActividades());
				%>
			</select></td>
			</tr>
			<tr>
			<td>Alumno</td>
			<td>
			<select name="alumno">
				<%out.println(yo.getAlumnos()); %>
			</select></td>
			</tr>
			<tr>
			<td>Nota</td>
				<td><input type="number" name="calificacion"></td>
			</tr>
			</table>
			<input type="hidden" name="tipo" value="add">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user") %>>
			
			<input type="submit" value="Agregar">
		</form>
		${add }
	</fieldset>
	<br>
	<fieldset>
		<legend>Modificar Nota</legend>
		<form method="post" action="notas">
			<table>
				<tr>
					<td>Actividad</td>
					<td>
					<select name="actividad"><%out.println(yo.getActividades()); %></select>
					</td>
				</tr>
				<tr>
					<td>Alumno</td>
					<td>
					<select name="alumno"><%out.println(yo.getAlumnos()); %></select>
					</td>
				</tr>
				<tr>
					<td>Nota</td>
					<td><input type="number" name="calificacion"></td>
				</tr>
			</table>
			<input type="hidden" name="tipo" value="modify">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Modificar">
		</form>
		${modify }
	</fieldset>
	<br>
	<fieldset>
		<legend>Eliminar Nota</legend>
		No implementado
	</fieldset>
	<br>
	<fieldset>
		<legend>Estadisticos</legend>
		No implementado
	</fieldset>
	<br>
	<fieldset>
		<legend>Aprobar Notas</legend>
		No implementado
	</fieldset>
</body>
</html>