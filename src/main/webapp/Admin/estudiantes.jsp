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
	<form method="post">
	</form>
	
	
	<form method="get" action="graph">
		<input type="submit" value="Graficar">
	</form>
	<br>
	${imagen }


</body>
</html>