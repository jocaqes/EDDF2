package org.jocaqes.Estructura;

/**
 * Nodo contenedor para la tabla hash con direccionamiento cerrado
 * y uso de lista simple para resolucion de colisiones
 * @author jocaqes
 *
 * @param <T> El tipo de item que se espera guardar en el nodo
 */
public class NodoH <T>{
	
    public boolean eliminado;
    public boolean visitado;
    public T item;
    public NodoH<T> siguiente;
    public String id;

    /**
     * Constructor que inicia un nodoh con su respectivo item
     * @param item el valor a agregar en el nodo
     */
    public NodoH(T item) {
        this.item = item;
        eliminado=visitado=false;
        siguiente=null;
    }

    /**
     * Constructor que inicia un nodoh con un item y un identificador
     * @param item el valor a agregar en el nodo
     * @param id identificador para busqueda
     */
    public NodoH(T item, String id) {
        this.item = item;
        this.id = id;
        eliminado=visitado=false;
        siguiente=null;
    }
    
    /**
     * Agrega un nuevo nodo a la cola de los colisionados
     * @param item el nuevo nodo a ingresar
     * @param id el identificador del nuevo nodo
     */
    public void add(T item, String id)
    {
        if(siguiente==null)
            siguiente=new NodoH<>(item,id);
        else
        {
            NodoH<T> aux=siguiente;
            while(aux.siguiente!=null)
                aux=aux.siguiente;
            aux.siguiente=new NodoH<>(item,id);
        }
    }
    public void add(NodoH<T> nodo)
    {
    	if(siguiente==null)
    		siguiente=nodo;
    	else
    	{
    		 NodoH<T> aux=siguiente;
             while(aux.siguiente!=null)
                 aux=aux.siguiente;
             aux.siguiente=nodo;
    	}
    }
    /**
     * Busca un nodo para eliminarlo de la lista de siguientes, pero no puede
     * eliminar este mismo nodo.
     * @param id identificador para busqueda
     * @return <tt>true</tt> Si encontro y elimino un nodo, <tt>false</tt> en 
     * caso contrario
     */
    public boolean eliminar(String id)
    {
        if(siguiente.id.equals(id))
        {
            this.siguiente=siguiente.siguiente;
        }
        else
        {
            NodoH<T> anterior=siguiente;
            NodoH<T> a_eliminar=this.siguiente.siguiente;
                    
            while(a_eliminar!=null&&!a_eliminar.id.equals(id))//mientras no encuentre el que debe eliminar
            {
                anterior=anterior.siguiente;
                a_eliminar=a_eliminar.siguiente;
            }
            if(a_eliminar==null)
                return false;
            anterior.siguiente=a_eliminar.siguiente;
        }
        return true;
    }
    
    /**
     * Busca un nodo de la lista de siguientes para modificar su contenido,
     * pero no puede modificar este mismo nodo
     * @param id identificador para busqueda
     * @param item el valor que reemplazara al viejo
     * @return <tt>true</tt> Si encontro y modifico un nodo, <tt>false</tt> en 
     * caso contrario
     */
    public boolean modificar(String id, T item)
    {
        if(siguiente.id.equals(id))
        {
            siguiente.item=item;
        }
        else
        {
            NodoH<T> a_modificar=siguiente.siguiente;
            while(a_modificar!=null&&!a_modificar.id.equals(id))
            {
                a_modificar=a_modificar.siguiente;
            }
            if(a_modificar==null)
                return false;
            a_modificar.item=item;
        }
        return true;
    }
    
    /**
     * Busca en el nodo y su lista de nodos el que corresponda al parametro
     * pasado a la funcion entrega su item a cambio.
     * @param id identificador para busqueda
     * @return <tt>null</tt> si no encontro nada, el item del nodo en caso contrario
     */
    public T buscar(String id)
    {
        if(this.id.equals(id))
            return item;
        else
        {
            NodoH<T> aux=siguiente;
            while(aux!=null&&!aux.id.equals(id))
                aux=aux.siguiente;
            if(aux==null)
                return null;
            return aux.item;
        }
    }

	@Override
	public String toString() {
		String codigo="";
		codigo+="[label=\"{<n>";
		NodoH<T> aux=siguiente;
		while(aux!=null)
		{
			codigo+=aux.item.toString();
			codigo+="|";
			aux=aux.siguiente;
		}
		codigo="}\"];\n";
		return codigo;
	}
    
    

}
