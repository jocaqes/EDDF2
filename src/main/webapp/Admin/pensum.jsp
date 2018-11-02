<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>Administracion</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if(session.getAttribute("user")==null)
		response.sendRedirect("../index.jsp");
%>
<a href="../logout.jsp">Logout</a>
	<h1>Bienvenido a la pagina de administracion</h1>
	<br>
	<jsp:include page="Sidebar.jsp"/>
	<br>
	<fieldset>
		<legend>Carga Pensum</legend>
		<form method="post" action="pensum">
			Ruta del archivo:<br>
			<input type="text" name="ruta" size="35">
			<input type="hidden" name="tipo" value="load">
			<input type="submit" value="OK"> 
		</form>
		${mensaje }
	</fieldset>
	<fieldset>
	<legend>Grafica</legend>
	<form method="post" action="pensum">
		<input type="hidden" name="tipo" value="graph"> 
		<input type="submit" value="Graficar">
	</form>
	</fieldset>
	<br>
	${imagen }

</body>
</html>