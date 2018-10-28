package org.jocaqes.EDDF2.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * AdminRestCall es una clase que mantiene un cliente estatico del paquete client de javax
 * para su uso por los servlets 
 * @author jocaqes
 *
 */
public class RestClient {
	private static Client cliente = ClientBuilder.newClient();
	/**
	 * El path inicial para peticiones del administrador
	 */
	private static WebTarget admin_target = cliente.target("http://localhost:8080/EDDF2/webapi/Admin/");
	/**
	 * El path inicial para peticiones miscelaneas
	 */
	private static WebTarget misc_target = cliente.target("http://localhost:8080/EDDF2/webapi/misc/");
	
	
	public static WebTarget getAdmin_target() {
		return admin_target;
	}


	public static WebTarget getMisc_target() {
		return misc_target;
	}
	
	
}
