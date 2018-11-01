<%@page import="org.jocaqes.Estructura.NodoS"%>
<%@page import="org.jocaqes.Misc.Estudiante"%>
<%@page import="org.jocaqes.Estructura.Lista"%>
<%@page import="org.jocaqes.Misc.DataBase"%>
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
	<h1>Tutores</h1>
	<br>
	<jsp:include page="Sidebar.jsp"/>
	<br>
	<fieldset>
		<legend>Agregar Tutor</legend>
		<form action="tutores" method="post">
			Carnet del Tutor:
			<select name="tutor">
				<%
					Lista<Estudiante> aux_lista=DataBase.getPosiblesTutores();
					NodoS<Estudiante> aux = aux_lista.raiz;
					while(aux!=null)
					{
						out.print("<option>");
						out.print(aux.item.getCarnet());
						out.println("</option>");
						aux=aux.siguiente;
					}
				%>
			</select><br>
			Curso:
			<select name="curso">
				<%
					out.println(DataBase.getPensum().getCursos());
				%>
			</select><br>
			Periodo:
			<select name="periodo">
				<%
					for(int i=2000;i<2019;i++)
					{
						out.print("<option>");
						out.print(i);
						out.println("</option>");
					}
				%>
			</select><br>
			<input type="hidden" name="tipo" value="add">
			<input type="submit" value="Agregar">
			${add }
		</form>
	</fieldset>
	<br>
	<fieldset>
		<legend>Buscar Tutor</legend>
		<form action="tutores" method="post">
			Carnet del tutor:<br>
			<input type="text" name="carne"><br>
			<input type="hidden" name="tipo" value="search">
			<input type="submit" value="Buscar"><br>
		</form>
		${search }
	</fieldset>
	<br>
		<fieldset>
		<legend>Remover Tutor</legend>
		<form action="tutores" method="post">
			Carnet del tutor:<br>
			<input type="text" name="carne"><br>
			<input type="hidden" name="tipo" value="delete">
			<input type="submit" value="Remover"><br>
		</form>
		${delete }
	</fieldset>
	<br>
	<fieldset>
	<legend>Grafica AVL</legend>
		<form action="tutores" method="post">
			<input type="hidden" name="tipo" value="graph">
			<input type="submit" value="Graficar"> 
		</form>
	</fieldset>
	${graph }
</body>
</html>