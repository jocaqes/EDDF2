package org.jocaqes.EDDF2.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jocaqes.Misc.Curso;
import org.jocaqes.Misc.Estudiante;

@Path("/Admin")
public class AdminResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Estudiante> addStudent(List<Estudiante> estudiantes)
	{
		return estudiantes;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Estudiante getStudent()
	{
		Estudiante prueba=new Estudiante(201212921, "pancho", "", "Jose Carlos", "Quiñonez Espinoza", 140, "pancho@gmail.com");
		return prueba; 
	}
	
	
}
