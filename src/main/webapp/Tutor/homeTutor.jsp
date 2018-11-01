<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tutor</title>
</head>
<body>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if(session.getAttribute("user")==null)
		response.sendRedirect("../index.jsp");
%>
	<a href="../logout.jsp">Logout</a>
	<h1>Bienvenido a la pagina de Tutores</h1>
	<br>
	<jsp:include page="sidebar.html"/>
	<br>
</body>
</html>