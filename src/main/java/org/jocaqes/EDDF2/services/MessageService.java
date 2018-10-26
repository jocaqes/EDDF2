package org.jocaqes.EDDF2.services;

import org.jocaqes.Estructura.ListaD;
import org.jocaqes.Misc.DataBase;

public class MessageService {
	private ListaD<String> mensajes = DataBase.getMensajes();
	
	public MessageService()
	{
		mensajes.add("ratas");
		mensajes.add("emparedados");
	}
	
	public String firstMessage()
	{
		return mensajes.get(0);
	}
	
	public int count()
	{
		return mensajes.count;
	}

}
