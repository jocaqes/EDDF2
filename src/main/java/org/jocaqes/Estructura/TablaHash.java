package org.jocaqes.Estructura;

/**
 * Tabla hash con resolucion de colisiones por lista
 * insercion por plegamiento
 * @author jocaqes
 *
 * @param <T>
 */
public class TablaHash <T>{
    private int size=23;
    private NodoH<T> tabla[];
    /**
     * Indica cuantos espacios de la tabla estan ocupados, no cuenta colisiones
     */
    private int ocupados;

    /**
     * Constructor vacio para la tabla hash, con 23 espacios inicialmente
     */
    @SuppressWarnings("unchecked")
	public TablaHash() {
        tabla=new NodoH[size];//porque esto no da Generic array error pero NodoH<> si? no tengo ni idea
        ocupados=0;
    }
    
    /**
     * Inserta un nuevo item a la tabla hash no se preocupa por repetidos.
     * Si la tabla se llena en un 80% realiza un crecimiento y remapeo
     * @param item el valor que se desea guardar en la tabla
     * @param llave el identificador del valor
     * @return <tt>true</tt> En todo caso, no busca repeticiones asi que
     * la insercion siempre se realiza
     */
    public boolean insertar(T item, String llave)
    {
        int key=h(llave);
        if(tabla[key]==null)//si esta vacio
        {
            NodoH<T> nuevo=new NodoH<>(item,llave);
            tabla[key]=nuevo;
            ocupados++;
        }
        else
        {
            tabla[key].add(item, llave);
        }
        if(ocupados>=(size*0.7f))
        {
        	remap();
        }
        return true;
    }
    private void insertar(NodoH<T> nodo, String llave)
    {
    	int key=h(llave);
    	if(tabla[key]==null)
    	{
    		tabla[key]=nodo;
    		ocupados++;
    	}
    	else
    	{
    		tabla[key].add(nodo);
    	}
    }
    /**
     * Busca en la tabla hash un item con el identificador pasado como parametro
     * @param llave el identificador del item 
     * @return el item encontrado o null en caso de que no exista
     */
    public T buscar(String llave)
    {
        int key=h(llave);
        if(tabla[key]==null)
            return null;
        else
            return tabla[key].buscar(llave);
    }
    /**
     * Busca en la tabla hash un item con el identificador pasado como parametro
     * y modifica su contenido dejando intacta la llave
     * @param item el nuevo item
     * @param llave la llave del item a modificar
     * @return <tt>true</tt> si la modificacion fue exitosa
     * <tt>false</tt> si no se encontro ningun valor para modificar
     */
    public boolean modificar(T item, String llave)
    {
        int key=h(llave);
        if(tabla[key]==null)
            return false;
        if(tabla[key].id.equals(llave))
        {
            tabla[key].item=item;
            return true;
        }
        else
        {
            return tabla[key].modificar(llave, item);
        }
    }
    
    /**
     * Busca en la tabla hash un item con el identificador pasado como parametro
     * y lo elimina de la tabla
     * @param llave el identificador del item
     * @return <tt>true</tt> si la eliminacion fue exitosa
     * <tt>false</tt> en caso que no se econtrara ningun item con esa llave
     */
    public boolean eliminar(String llave)
    {
    	int key=h(llave);
    	if(tabla[key]==null)
    		return false;
    	if(tabla[key].id.equals(llave))
    	{
    		tabla[key]=tabla[key].siguiente;
    		ocupados--;
    		return true;
    	}
    	else
    	{
    		return tabla[key].eliminar(llave);
    	}
    }
    
    public String codigoGrafica()
    {
    	String codigo="digraph G{\n";
    	String subcodigo="";
    	codigo+="nodesep=.05;\n";
    	codigo+="rankdir=LR;\n";
    	codigo+="node[shape=record];\n";
    	codigo+="nodo0[label=\"";
    	for(int i=0;i<size;i++)
    	{
    		codigo+="<f"+i+">";
    		if(tabla[i]!=null)
    		{
    			codigo+=tabla[i].item.toString();
    			if(tabla[i].siguiente!=null)
    			{
    				subcodigo+="nodo"+i+tabla[i].siguiente.toString();
    				subcodigo+="nodo0:f"+i+"->nodo"+i+":n;\n";
    			}
    		}
    		if(i<size-1)
    			codigo+="|";
    	}
    	codigo+="}\"];\n";
    	codigo+=subcodigo;
    	codigo+="}";
    	return codigo;
    }
    
    /**
     * Funcion hash basada en el algoritmo de plegamiento
     * @param llave el identificador que se desea someter al hashing
     * @return un entero que representa al identificador
     */
    private int h(String llave)
    {
        char letras[];
        letras = llave.toCharArray();
        int lenght = letras.length;
        int suma=0;
        for(int i=0;i<lenght;i++)
            suma+=letras[i];
        return suma%size;
    }
    @SuppressWarnings("unchecked")
	private void remap()
    {
    	int new_size=size*2;
    	int old_size=size;//guardo el tamaño viejo
    	while(!pseudoPrime(new_size))//busco un nuevo "primo" la funcion no es perfecta pero por cuestion de tiempo que asi se quede
    		new_size++;
    	NodoH<T> oldtabla[]=tabla;//recupero la tabla vieja
    	tabla=new NodoH[new_size];//hago una nueva tabla
    	size=new_size;//cambio el tamaño
    	ocupados=0;//reinicio la cuenta de ocupados
    	for(int i=0;i<old_size;i++)
    	{
    		if(oldtabla[i]!=null)
    			insertar(oldtabla[i],oldtabla[i].id);
    	}
    	oldtabla=null;
    }
    
    private boolean pseudoPrime(int n)
    {
		if(n==1||n==2||n==3||n==5||n==7||n==11)
			return true;
		if(n%2==0)
			return false;
		if(n%3==0)
			return false;
		if(n%5==0)
			return false;
		if(n%7==0)
			return false;
		if(n%11==0)
			return false;
		if(n%13==0)
			return false;
		return true;
    }
}
