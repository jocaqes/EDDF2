<%@page import="org.jocaqes.Misc.Archivo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<title>Home</title>
<body>
	<!-- Aqui iria un encabezado -->
	<!-- algunos dibujos -->
	<img alt="Imagen no disponible" src="Imagenes/izumi.jpeg">
	<form method="post" action="sesion">
		${mensaje }
		<fieldset>
			<legend>Login</legend>
			Nombre de Usuario:<br>
			<input type="text" name="usuario"><br>
			Contraseña(140918):<br>
			<input type="password" name="password"><br><br>
			<input type="submit" value="OK">
		</fieldset>	
	</form>
	<!-- Aqui iria un pie de pagina -->
</body>
</html>
