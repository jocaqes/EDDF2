<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="iso-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="iso-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
session.removeAttribute("user");
session.removeAttribute("corto");
session.removeAttribute("pregunta");
session.invalidate();
response.sendRedirect("index.jsp");
%>
</body>
</html>