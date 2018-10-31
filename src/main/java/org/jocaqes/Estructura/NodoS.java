package org.jocaqes.Estructura;

/**
 * Clase contenedora para listas simbles
 * @author jocaqes
 *
 * @param <T> el tipo de objeto que se espera guardar en la lista
 */
public class NodoS <T>{
	
	public NodoS<T> siguiente;
	public T item;
	
	/**
	 * Constructor para un nodo con un item dentro
	 * @param item el item que se desea guardar en el nodo
	 */
	public NodoS(T item) {
		this.item = item;
		siguiente=null;
	}
	
}
