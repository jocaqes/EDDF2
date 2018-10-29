<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
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
	<h1>Bienvenido a la pagina de administracion</h1>
	<br>
	<jsp:include page="Sidebar.jsp"/>
	<br>

</body>
</html>