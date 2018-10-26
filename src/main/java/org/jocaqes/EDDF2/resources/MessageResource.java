package org.jocaqes.EDDF2.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jocaqes.EDDF2.services.MessageService;

@Path("/messages")
public class MessageResource {
	
	MessageService messageservice = new MessageService();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessages()
	{
		return "hola jose";
	}
	
}