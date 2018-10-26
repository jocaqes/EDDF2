package org.jocaqes.Estructura;

/**
 *Clase que maneja los nodos contenedores de las distintas listas
 * trabaja con valores genericos
 * @author JOSE QUIÑONEZ <jose at jocaqes.org>
 */
public class NodoL <T>{

    /**
     *Nodo frente al nodo actual
     */
	public NodoL<T> siguiente;
    /**
     *Nodo previo al nodo actual 
     */
	public NodoL<T> anterior;
    /**
     *Item contenido dentro del nodo
     */
    public T item;

    /**
     *Genera un nuevo nodo para enlace doble
     * @param item el valor a guardar en el nodo
     */
    public NodoL(T item) {
        this.item = item;
        siguiente=anterior=null;
    }
}
