<%@page import="org.jocaqes.Misc.Pregunta.TipoPregunta"%>
<%@page import="org.jocaqes.Misc.Pregunta"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>Tutor</title>
</head>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if(session.getAttribute("user")==null)
		response.sendRedirect("../index.jsp");
%>
<body>
<a href="../logout.jsp">Logout</a>
	<h1>Nueva Pregunta</h1>
	<fieldset>
		<legend>Pregunta</legend>
		<form method="post" action="edicioncorto">
			${formulario }
		</form>
	</fieldset>

</body>
</html>