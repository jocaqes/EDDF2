<%@page import="javax.ws.rs.core.Response"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.jocaqes.EDDF2.restcalls.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test title</title>
</head>
<body>
	<h1>Hola te saludamos de la pagina test</h1>
	<br>
	<h2>Este es el resultado de la busqueda</h2>
	<br>
	<%
		String carnet = request.getParameter("carnet");
		out.println(AdminRestCall.buscarEstudiante(carnet));
		//out.println(val);
	%>
</body>
</html>