package org.jocaqes.EDDF2.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jocaqes.EDDF2.services.MiscService;

@Path("/misc")
public class MiscResource {
	MiscService service = new MiscService();
	
	
	@GET
	@Path("/login/{carne}/{password}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginEstudiante(@PathParam("carne") String carne, @PathParam("password") String password)
	{
		if(service.isNumber(carne))
		{
			int carne_=Integer.parseInt(carne);
			if(service.login(carne_, password))
				return Response.status(Status.ACCEPTED).entity(true).build();
			return Response.status(Status.UNAUTHORIZED).entity(false).build();
		}
		return Response.status(Status.UNAUTHORIZED).entity(false).build();
	}
	
	@GET
	@Path("/btree")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response codigoBtree()
	{
		return Response.status(Status.OK).entity(service.graficaArbolB()).build();
	}

}
