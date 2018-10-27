package org.jocaqes.EDDF2.restcalls;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AdminRestCall {
	private static Client cliente = ClientBuilder.newClient();
	private static WebTarget admin_target = cliente.target("http://localhost:8080/EDDF2/webapi/Admin/");
	
	
	public static String buscarEstudiante(String carnet)
	{
		WebTarget search=admin_target.path("buscar/{carne}");
		Response response = search
				.resolveTemplate("carne", carnet)
				.request(MediaType.APPLICATION_JSON)
				.get();
		if(response.getStatus()==Status.FOUND.getStatusCode())
		{
			return response.readEntity(String.class);
		}else//404
		{
			return response.readEntity(String.class);
		}
	}
	
}
