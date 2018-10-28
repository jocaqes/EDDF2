package org.jocaqes.EDDF2.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.services.AdminService;
import org.jocaqes.Misc.Estudiante;
import org.jocaqes.Misc.Estudiantes;
/**
 * RestAPI para con funciones exclusivas de administrador
 * @author jocaqes
 *
 */
@Path("/Admin")
public class AdminResource {
	AdminService service = new AdminService(); 
	
	@POST
	@Path("/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String agregarEstudiante(Estudiante estudiante)
	{
		if(service.agregar(estudiante, estudiante.getCarnet()))
			return "Estudiante agregado con exito!!";
		return "El carne del estudiante esta repetido, por favor cambie ese valor";
	}	
	
	@GET
	@Path("/buscar/{carne}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEstudiante(@PathParam("carne") int carne)
	{
		Estudiante encontrado=service.buscar(carne);
		if(encontrado!=null)
			return Response.status(Status.FOUND).entity(encontrado).build();
		else
			return Response.status(Status.NOT_FOUND).entity("{\"error\":\"el estudiante no existe\"}").build();
	}
	
	/*
	@GET
	@Path("/login/{carne}/{password}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginEstudiante(@PathParam("carne") int carne, @PathParam("password") String password)
	{
		if(service.login(carne, password))
			return Response.status(Status.ACCEPTED).entity(true).build();
		return Response.status(Status.UNAUTHORIZED).entity(false).build();
	}*/

	
	@POST
	@Path("/cargar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response cargarEstudiantes(Estudiantes estudiantes_)//el json de carga masiva automaticamente es una lista de estudiantes	
	{
		List<Estudiante> estudiantes = estudiantes_.getEstudiantes();
		if(estudiantes==null||estudiantes.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("No hay datos para realizar la carga de estudiantes").build();
		service.cargar(estudiantes);
		return Response.status(Status.CREATED).entity("Carga de estudiantes realizada con exito!!").build();
	}
				
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public Response modificarEstudiante(Estudiante estudiante)
	{
		return Response.status(Status.NOT_IMPLEMENTED).entity("Esta funcion no esta implementada aun").build();
	}
		
}
