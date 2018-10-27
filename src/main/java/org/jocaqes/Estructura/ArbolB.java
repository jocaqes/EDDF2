package org.jocaqes.Estructura;


/**
 *
 * @author JOSE QUIÑONEZ <jose at jocaqes.org>
 */
public class ArbolB <T>{
    public NodoB<T> raiz;
    private final int k;//el numero maximo de llaves/items en un nodo

    /**
     * Constructor para un arbol b con grado igual a su parametro k
     * @param k grado para el arbol
     */
    public ArbolB(int k) {
        this.k=k;
    }
    
    /**
     * Agrega un nuevo item a el arbol B, tomando en cuenta que valores repetidos son descartados
     * @param item el nuevo item a agregar
     * @param llave identificador unico del item
     * @return <tt>true</tt> si la insercion fue exitosa, <tt>false</tt> en caso que la llave este
     * repetida
     */
    public boolean add(T item, int llave)
    {
        if(raiz==null)
        {
           raiz=new NodoB<>(k);
           raiz.add(item, llave);
        }
        else
        {
        	T encontrado=buscar(llave);
        	if(encontrado!=null)
        		return false;
            raiz=add(raiz,null,item,llave);
        }
        return true;
    }
    private NodoB<T> add(NodoB<T> actual,NodoB<T> padre, T nuevo, int llave)
    {
        if(actual.hayHijos())//si no hay paginas hijo
        {
            actual.add(nuevo, llave);//agregamos en actual
        }
        else//tiene por lo menos una pagina hijo
        {
            NodoB<T> aux=null;
            int limite=actual.ocupados;//cuantas paginas hay que revisar maximo
            for(int i=0;i<limite;i++)
            {
                if(nuevo.hashCode()<actual.llaves[i].hashCode())//el nuevo va en pagina i
                {
                    aux=actual.hijos[i];//recupero la pagina donde hay que insertar
                    i=limite;//termino el ciclo for
                }
                else//toca revisar la siguiente pagina
                {
                    aux=actual.hijos[i+1];//el numero de hijos siempre es 1+ que ocupados
                }
            }
            add(aux,actual,nuevo,llave);//agregamos en la pagina adecuada
        }
        if(actual.hasExtraItem())//el nuevo insertado ya no cupo, hay una llave/item extra
        {
            T extra=actual.getExtraItem();//el item que se va a subir(el de en medio)
            int extra_llave=actual.getExtraLLave();//la llave que se va a subir(la de en medio)
            boolean padre_inicio_null=false;
            if(padre==null)//si el padre es nulo
            {
                padre=new NodoB<>(k);//hay que generar un nuevo padre, el seria una nueva raiz
                padre_inicio_null=true;
            }
            
            padre.add(extra,extra_llave);//agregamos el extra al padre
            NodoB<T> der=new NodoB<>(k);//generamos un nuevo nodo hijo/pagina
            int i;
            T item_a_pasar=null;
            for(i=0;i<k/2;i++)//agregamos el numero items que le tocan a der
            {
                item_a_pasar=actual.pop();
                der.add(item_a_pasar,item_a_pasar.hashCode());
            }
            if(padre_inicio_null)//agregamos a padre las 2 paginas(si es necesario)
                padre.add(actual);
            padre.add(der);
            if(actual.hasExtraChild())//tiene mas paginas de las que deberia
            {
                for(i=0;i<k/2;i++)//agregamos a derecho las paginas que le corresponden
                {
                    der.add(actual.popB());
                }
                //mas la pagina extra
                der.add(actual.getExtraHijo());
            }
        }
        if(padre==null)
            return actual;
        return padre;
    }
    
    /**
     * Busca un item en el arbol que corresponda con el parametro proporcionado
     * @param llave el parametro de busqueda
     * @return el objeto encontrado si es que existe, en caso contrario retorna 
     * <tt>null</tt>
     */
    public T buscar(int llave)
    {
    	if(isEmpty())
    		return null;
    	T item = buscar(llave,raiz);
    	return item;
    }
    @SuppressWarnings("unchecked")
	private T buscar(int llave, NodoB<T> raiz)
    {
    	if(raiz==null)
    		return null;
    	int index=0;
    	boolean encontrado=false;
    	while(index<raiz.ocupados&&llave>=raiz.llaves[index].hashCode()&&!encontrado)
    	{
    		if(llave==raiz.llaves[index].hashCode())
    			encontrado=true;
    		else
    			index++;
    	}
    	if(encontrado)
    		return (T)raiz.llaves[index];
    	return buscar(llave,raiz.hijos[index]);
    }
    /**
     * Busca en el arbol la posicion que corresponde al parametro proporcionado y actualiza
     * sus valores con los valores proporcionados
     * @param llave identificador de la posicion a buscar
     * @param item los nuevos valores para la actualizacion
     * @return <tt>true</tt> si la modificacion fue exitosa; <tt>false</tt> si no se encontro
     * ningun valor que corresponda con la llave
     */
    public boolean modificar(int llave, T item)
    {
    	return modificar(llave,item,raiz);
    }
    private boolean modificar(int llave, T item, NodoB<T> raiz)
    {
    	if(raiz==null)
    		return false;
    	int index=0;
    	boolean encontrado=false;
    	while(index<raiz.ocupados&&llave>=raiz.llaves[index].hashCode()&&!encontrado)
    	{
    		if(llave==raiz.llaves[index].hashCode())
    		{
    			encontrado=true;
    			raiz.llaves[index]=item;
    		}
    		else
    			index++;
    	}
    	if(encontrado)
    		return true;
    	return modificar(llave,item,raiz.hijos[index]);
    }
    
    /**
     * Revisa si el arbol esta vacio
     * @return <tt>true</tt> si esta vacio(su raiz es null) <tt>false</tt> en caso contrario
     */
    public boolean isEmpty()
    {
    	return raiz==null;
    }
    /*public void graficar(String url)
    {
        String codigo="digraph G{\n";
        codigo+="node [shape=record];\n";
        codigo+=codigoNodos(raiz);
        codigo+="}";
        Archivo.graficar(codigo, url);
    }*/
    public String codigoNodos(NodoB<T> raiz)
    {
        String codigo="";
        if(raiz!=null)
        {
            codigo+=raiz.toString();
            for(int i=0;i<raiz.hijos_ocupados;i++)
            {
                codigo+=codigoNodos(raiz.hijos[i]);
            }
        }
        return codigo;
    }

    
}
