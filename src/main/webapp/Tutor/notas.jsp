<%@page import="org.jocaqes.Misc.Tutor"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
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
	<h1>Control de Notas</h1>
	<br>
	<jsp:include page="sidebar.html"/>
	<br>
	<fieldset>
		<legend>Cargar Notas</legend>
		No implementado
	</fieldset>
	<br>
	<fieldset>
		<legend>Agregar Nota</legend>
		No implementado
	</fieldset>
	<br>
	<fieldset>
		<legend>Modificar Nota</legend>
		No implementado
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