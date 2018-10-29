<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administracion</title>
</head>
<body>

<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if(session.getAttribute("user")==null)
		response.sendRedirect("../index.jsp");
%>

<a href="../logout.jsp">Logout</a>
	<h1>Estudiantes</h1>
	<br>
	<jsp:include page="Sidebar.jsp"/>
	<br>
	<fieldset>
		<legend>Agregar Estudiante</legend>
		<form method="post" action="estudiantes">
			Nombre:<br>
			<input type="text" name="nombre"><br>
			Apellidos:<br>
			<input type="text" name="apellidos"><br>
			Carnet:<br>
			<input type="text" name="carnet"><br>
			DPI:<br>
			<input type="text" name="dpi"><br>
			Correo:<br>
			<input type="email" name="correo"><br>
			Contraseña:<br>
			<input type="text" name="password"><br>
			<input type="hidden" name="tipo" value="add">
			<input type="submit" value="Agregar">
			${agregado }
		</form>
			
	</fieldset>
	<br>
	<fieldset>
	<legend>Cargar Estudiantes</legend>
		<form method="post" action="estudiantes">
			Ruta del archivo:<br>
			<input type="text" name="ruta"><br>
			<input type="hidden" name="tipo" value="load"> 
			<input type="submit" value="OK">
		</form>
		${mensaje }
	</fieldset>
	
	
	<form method="get" action="graph">
		Nombre para la Grafica:<br>
		<input type="text" name="nombre_archivo"><br>
		<input type="submit" value="Graficar">
	</form>
	<br>
	${imagen }


</body>
</html>