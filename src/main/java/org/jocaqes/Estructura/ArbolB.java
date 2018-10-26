package org.jocaqes.Estructura;


/**
 *
 * @author JOSE QUIÑONEZ <jose at jocaqes.org>
 */
public class ArbolB <T>{
    public NodoB<T> raiz;
    private final int k;//el numero maximo de llaves/items en un nodo

    public ArbolB(int k) {
        this.k=k;
    }
    
    public void add(T item, int llave)
    {
        if(raiz==null)
        {
           raiz=new NodoB<>(k);
           raiz.add(item, llave);
        }
        else
        {
            raiz=add(raiz,null,item,llave);
        }
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
