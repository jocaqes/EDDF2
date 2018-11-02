<!--<%@page import="javax.ws.rs.core.Response"%>-->
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>Test title</title>
</head>
<body>
<a href="SessionLogin">Logout</a>
	<h1>Hola te saludamos de la pagina test</h1>
	<br>
	<h3>Bienvenido: ${sessionScope.user} </h3>
</body>
</html>