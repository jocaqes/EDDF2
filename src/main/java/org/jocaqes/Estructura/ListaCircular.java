package org.jocaqes.Estructura;

public class ListaCircular <T>{
	   	public NodoL<T> raiz;
	    public int count;

	    /**
	     *Inicia la lista circular con un 0 items y una raiz nula
	     */
	    public ListaCircular() {
	        raiz=null;
	        count=0;
	    }
	    
	    /**
	     *Agrega un nuevo item al final de la lista circular, en esta 
	     * lista son validos los valores repetidos
	     * @param item
	     */
	    public void add(T item)
	    {
	        if(isEmpty())
	        {
	            raiz=new NodoL<>(item);
	            raiz.siguiente=raiz;
	            raiz.anterior=raiz;
	        }
	        else
	        {
	            raiz.anterior.siguiente=new NodoL<>(item);//el siguiente del ultimo es el nuevo
	            raiz.anterior.siguiente.siguiente=raiz;//el siguiente del nuevo es raiz
	            raiz.anterior.siguiente.anterior=raiz.anterior;//el anterior del nuevo es el previo ultimo
	            raiz.anterior=raiz.anterior.siguiente;//el anterior de la raiz es el nuevo, ahora el ultimo nodo
	        }
	        count++;
	    }
	    
	    
	    /**
	     *Revisa si la lista circular esta vacia
	     * @return <tt>true</tt> si la raiz es nula y por 
	     *tanto la lista esta vacia <tt>false</tt> en caso
	     * contrario
	     */
	    public boolean isEmpty()
	    {
	        return raiz==null;
	    }
	    
	    

}
