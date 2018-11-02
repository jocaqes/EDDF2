<%@page import="org.jocaqes.Misc.Tutor"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
<%@page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="tutorstyle.css">
<meta charset="iso-8859-1">
<title>Insert title here</title>
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
	<h1>Mis Alumnos</h1>
	<br>
	<jsp:include page="sidebar.html"/>
	<br>
	<h2>Curso:<%=yo.getCurso() %></h2>
	<fieldset>
		<legend>Cargar Alumnos</legend>
		<form method="post" action="alumnos">
			Ruta del archivo:<br>
			<input type="text" name="ruta" size="35"><br>
			<input type="hidden" name="tipo" value="load">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user")%>>
			<input type="submit" value="Cargar"> 
		</form>
		<br>
		${load }
	</fieldset>
	<br>
	<fieldset>
		<legend>Asignar Alumno</legend>
		<form method="post" action="alumnos">
			Carnet del Alumno:<br>
			<input type="number" name="id"><br>
			<input type="hidden" name="tipo" value="add">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user")%>>
			<input type="submit" value="Agregar">
		</form>
		${add }
	</fieldset>
	<br>
		<fieldset>
		<legend>Modificar Alumno</legend>
		<form method="post" action="alumnos">
			Carnet del Alumno:<br>
			<input type="number" name="id"><br>
			<input type="hidden" name="tipo" value="form">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Buscar">
		</form>
		${form }
		${modify }
		<br>
	</fieldset>
	<br>
		<fieldset>
		<legend>Eliminar Alumno</legend>
		<form method="post" action="alumnos">
			Seleccione el que desea eliminar:<br>
			<select name="id">
			<%
				//out.println(yo.actividades());
			%>
			</select>
			<input type="hidden" name="tipo" value="delete">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Eliminar">
		</form>
		${delete }
	</fieldset>
	<br>
	<fieldset>
		<legend>Grafica de Alumnos</legend>
		<form method="post" action="alumnos">
			<input type="hidden" name="tipo" value="graph">
			<input type="hidden" name="tutor" value=<%=session.getAttribute("user") %>>
			<input type="submit" value="Graficar">
		</form>
		${graph }
	</fieldset>
</body>
</html>