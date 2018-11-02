package org.jocaqes.Estructura;

public class Lista <T>{
	public NodoS<T> raiz;
	public NodoS<T> fin;
	public int count;
	
	public Lista() {
		count=0;
		raiz=fin=null;
	}
	
	/**
	 * Revisa si la lista esta vacia
	 * @return <tt>true</tt> si la lista esta vacia
	 * <tt>false</tt> en caso contrario
	 */
	public boolean isEmpty()
	{
		return raiz==null;
	}
	
	/**
	 * Agrega un nuevo item a la lista, no importa si esta repetido
	 * @param item el nuevo valor que se desea guardar en la lista
	 */
	public void add(T item)
	{
		if(isEmpty())
		{
			raiz=new NodoS<T>(item);
			fin=raiz;
		}
		else
		{
			fin.siguiente=new NodoS<T>(item);
			fin=fin.siguiente;
		}
		count++;
	}
	
	/**
	 * Recupera el item que se encuentra en la posicion dada por el parametro pasado a la funcion
	 * @param index la posicion del item que se desea recuperar
	 * @return el item encontrado o null si el index no forma parte de la lista
	 */
	public T get(int index)
	{
		if(index<0||index>=count)
			return null;
		NodoS<T> aux=raiz;
		for(int i=0;i<index;i++)
		{
			aux=aux.siguiente;
		}
		return aux.item;
	}
	
	public void eliminar(int index)
	{
		if(index<0||index>=count)
			return;
		if(index==0)
			raiz=raiz.siguiente;
		else
		{
			NodoS<T> anterior=raiz;
			for(int i=0;i<index-1;i++)
				anterior=anterior.siguiente;
			anterior.siguiente=anterior.siguiente.siguiente;
		}
	}
	/**
	 * Recupera el item que corresponde con un id entregado.
	 * Hace uso de la funcion gethash para recuperar un id
	 * @param id un entero que identifica al item
	 * @return <tt>true</tt> si se encontro un item con el id
	 * proporcionado, <tt>false</tt> en caso contrario.
	 */
	public boolean buscar(int id)
	{
		NodoS<T> aux=raiz;
		boolean encontrado=false;
		while(aux!=null&&!encontrado)
		{
			if(aux.item.hashCode()==id)
				encontrado=true;
			else
				aux=aux.siguiente;
		}
		if(encontrado)
			return true;
		return false;
	}
	
	public void clear()
	{
		raiz=null;
		count=0;
	}
	
	

}
