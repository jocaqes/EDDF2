package org.jocaqes.Estructura;

/**
 * Clase para generar una lista doblemente enlazada
 * de tipo generica.
 * @author JOSE QUIÑONEZ <jose at jocaqes.org>
 */
public class ListaD <T>{
    public NodoL<T> raiz;
    public NodoL<T> fin;

    /**
     *Numero de items en la lista
     */
    public int count;

    /**
     * Constructor para una nueva lista doblemente enlazada
     * vacia
     */
    public ListaD() {
        raiz=fin=null;
        count=0;
    }
    
    /**
     * Agrega un nuevo item al final de la lista, permite
     * items repetidos.
     * @param item el nuevo item a agretar a la lista
     */
    public void add(T item)
    {
        if(isEmpty())
        {
            raiz=fin=new NodoL<>(item);
        }
        else
        {
            fin.siguiente=new NodoL<>(item);
            fin.siguiente.anterior=fin;
            fin=fin.siguiente;
        }
        count++;
    }
    
    /**
     *Agrega un nuevo item a la lista y lo coloca en una posicion ordenada
     * segun el parametro numerico obtenido de la funcion hash del item a 
     * insertar
     * @param item el nuevo valor a insertar
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el valor es repetido
     */
    public boolean sortedAdd(T item)
    {
        if(isEmpty())
        {
            raiz=new NodoL<>(item);
            fin=raiz;
            return true;
        }
        else
        {
            NodoL<T> aux=raiz;
            while(aux!=null)
            {
                if(item.hashCode()<aux.item.hashCode())
                {
                    if(aux.anterior==null)//aux es la raiz
                    {
                        aux.anterior=new NodoL<>(item);//lo agrego antes de aux
                        aux.anterior.siguiente=aux;//apunto el siguiente del nuevo a aux
                        raiz=aux.anterior;//actualizo la raiz
                    }
                    else
                    {
                        aux.anterior.siguiente=new NodoL<>(item);//colocamos al nuevo antes de aux, el puntero anterior apunta ahora a nuevo en lugar de aux
                        aux.anterior.siguiente.anterior=aux.anterior;//el anterior de nuevo(aux.anterior.siguiente) sera el anterior de aux
                        aux.anterior.siguiente.siguiente=aux;//el siguiente de nuevo es aux
                        aux.anterior=aux.anterior.siguiente;//el anterior de aux es el nuevo
                    }
                    return true;
                }
                else if(aux.siguiente==null)//llegue al final, por tanto es el ultimo
                {
                    fin.siguiente=new NodoL<>(item);
                    fin.siguiente.anterior=fin;
                    fin=fin.siguiente;
                    return true;
                }
                else if(item.hashCode()==aux.item.hashCode())//valor repetido
                    return false;
                aux=aux.siguiente;
            }
        }
        return true;
    }
    
    /**
     * Funcion para revisar si la lista se encuentra actualmente vacia
     * @return <tt>true</tt> si la raiz de la lista es nula y por 
     * tanto esta vacia <tt>false</tt> e caso contrario.
     */
    public boolean isEmpty()
    {
        return raiz==null;
    }
    
    /**
     * Recorre la lista en busca de un item que coincida con el patron 
     * de busqueda pasado a la funcion. Solo retorna la primer coincidencia.
     * @param patron el patron para buscar el item
     * @return <tt>null</tt> si no se encontro ningun item que coincidiera
     * con el patron de busqueda 
     */
    public T get(String patron)
    {
        NodoL<T> aux=raiz;
        T salida=null;
        while(aux!=null&&salida==null)
        {
            if(aux.item.toString().equals(patron))
                salida=aux.item;
            aux=aux.siguiente;
        }
        return salida;
    }
    
    /**
     * Recorre la lista en busqueda de un item que se encuentre en la
     * posision solicitada por el usuario, al mismo tiempo se asegura
     * que dicha posicion este entre el rango valido de la lista.
     * @param index la posicion de la que se quiere obtener el item
     * @return <tt>null</tt> si la posicion es negativa o sobrepasa
     * los limites de la lista; el item en la posicion index en caso
     * contrario
     */
    public T get(int index)
    {
        if(index<0||index>=count)
            return null;
        NodoL<T> aux=raiz;
        for(int i=0;i<index;i++)
        {
            aux=aux.siguiente;
        }
        return aux.item;
    }
    
}
