package org.jocaqes.Misc;

import org.jocaqes.Estructura.ListaD;

public class DataBase {
	private static ListaD<String> mensajes = new ListaD<>();

	/**
	 * Toma de la clase la lista de mensajes y la retorna para su uso posterior
	 * @return la lista mensajes de la clase
	 */
	public static ListaD<String> getMensajes() {
		return mensajes;
	}
	
	
}
