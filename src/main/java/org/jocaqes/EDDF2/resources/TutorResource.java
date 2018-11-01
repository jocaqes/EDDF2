package org.jocaqes.EDDF2.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.services.TutorService;
import org.jocaqes.Misc.Actividad;
import org.jocaqes.Misc.Actividades;

@Path("/Tutor")
public class TutorResource {
	TutorService service = new TutorService();
	
	@POST
	@Path("/cargar/{carne}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response cargarActividades(Actividades actividades_, @PathParam("carne") int carne)//el json de carga masiva automaticamente es una lista de estudiantes	
	{
		List<Actividad> actividades = actividades_.getActividades();
		if(actividades==null||actividades.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("No hay datos para realizar la carga de actividades").build();
		if(service.cargar(actividades,carne))
			return Response.status(Status.CREATED).entity("Carga de actividades exitosa!!").build();
		else
			return Response.status(Status.FORBIDDEN).entity("No tiene autorizacion para llevar a cabo esa peticion").build();
	}
	
	@POST
	@Path("/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarActividad(Actividad actividad)
	{
		return Response.status(Status.OK).entity(actividad).build();
	}

}
