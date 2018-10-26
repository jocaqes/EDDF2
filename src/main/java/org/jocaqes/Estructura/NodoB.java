package org.jocaqes.Estructura;

/**
*
* @author JOSE QUIÑONEZ <jose at jocaqes.org>
*/
public class NodoB<T> {
   private final int k;
   public NodoB<T>[] hijos;
   public Object[] llaves;//tambien cuenta como llave
   public int ocupados;
   public int hijos_ocupados;
   public NodoB<T> hijo_extra;
   public T item_extra;
   public int llave_extra;
   
   /**
    *Nodo para arbol b vacio
    * @param k el orden de arbol b a utilizar
    */
   @SuppressWarnings("unchecked")
   public NodoB(int k) {
       this.k = k;
       llaves=new Object[k-1];
       hijos=new NodoB[k];
       ocupados=0;
       hijos_ocupados=0;
       hijo_extra=null;
       item_extra=null;
       llave_extra=0;
   }
   
   /**
    *Agrega un nuevo item al arreglo de items en la ultima posicion abierta y luego ordena el arreglo
    * si el arreglo ya esta lleno recupera el valor medio del arreglo tomando en cuenta al nuevo
    * item y actualiza los extra y reordena lel arreglo de ser necesario
    * esto con el fin de saber cuales son los valores que deben subirse a la raiz
    * luego de tener arreglos llenos. No admite valores repetidos
    * @param nuevo el nuevo item a ingresar
    * @param llave la llave del nuevo item
    * @return <tt>true</tt> si la nueva llave no esta repetida,sin inportar si ya esta lleno.
    * <tt>false</tt> si la llave ya existia
    */
   public boolean add(T nuevo, int llave)
   {
       if(existe(llave))
           return false;
       if(estaLLeno())
       {
           updateMiddle(nuevo, llave);//reviso quien sera el de en medio y actualizo los valores extra
           //como no hay insercion no debo actualizar ocupados
       }
       else
       {
           llaves[ocupados]=nuevo;//agrego el nuevo al final
           quickSort(llaves, 0, ocupados);//ordeno el nuevo item
           ocupados++;//actualizo el numero de ocupados
       }
       return true;
   }
   
   
   /**
    *Agrega un nuevo nodo hijo en la ultima posicion abierta del arreglo de 
    * hijos, si el arreglo ya esta lleno hace un ultimo ordenamiento
    * para ver quien queda fuera y lo almacena como hijo_extra
    * @param nuevo el nuevo hijo que se agrega al arreglo de hijos
    */
   public void add(NodoB<T> nuevo)
   {
       if(nuevo==null)
           return;
       if(hijos_ocupados>=k)
       {
           updatePages(nuevo);
           return;
       }
       hijos[hijos_ocupados]=nuevo;
       quickSort(hijos, 0, hijos_ocupados);
       hijos_ocupados++;
   }
   
   
   /**
    * Funcion utilizada para cuando el arreglo de items ya esta lleno,
    * ordena los items anteriores y el nuevo y saca al item a la mitad del arreglo,
    * si es un numero par de items, toma el de la izquierda. Al mismo
    * tiempo ordena las llaves
    * @param nuevo el item que ya no cupo en el arreglo
    * @param llave la llave del que ya no cupo en el arreglo
    */
   @SuppressWarnings("unchecked")
   private void updateMiddle(T nuevo, int llave)
   {
       int mitad=k/2;//cualquier grado impar salvo 3
       if(k%2==0)//grados pares
           mitad--;
       if(mitad<0)
           mitad=0;
       else if(mitad==0)//grado 3
           mitad=1;
       int mitad_=mitad-1;
       if(mitad_<0)
           mitad_=0;
       if(llaves[mitad_].hashCode()<llave&&llave<llaves[mitad].hashCode())//ya es el valor de la mitad:1.-llave[mitad] de por si esta a la mitad, si es menor que la nueva llave, siempre quedaria a la mitad
       {
           item_extra=nuevo;//guardo el item extra
           llave_extra=llave;
       }
       else
       {
           T aux=(T)llaves[k-2];//el ultimo
           llaves[k-2]=nuevo;
           quickSort(llaves, 0, k-2);//ordeno los items
           item_extra=(T)llaves[mitad];//guardo el item a la mitad
           llave_extra=item_extra.hashCode();//guardo la llave del item
           llaves[mitad]=aux;//cambio en el arreglo el auxiliar
           quickSort(llaves, 0, k-2);//vuelvo a ordenar
       }
   }
   /**
    * Funcion utilizada para cuando el arreglo de hijos ya esta lleno,
    * ordena los revisa si el nuevo hijo iria al final del arreglo, de ser
    * asi lo agrega como hijo_extra, de lo contrario extrae el ultimo hijo del
    * arreglo de hijos y el nuevo toma su logar, ordena el arreglo y el hijo
    * extraido se convierte en hijo_extra
    * @param nuevo el nuevo hijo que ya no cupo en el arreglo
    */
   private void updatePages(NodoB<T> nuevo)
   {
       int ultimo_codigo;
       ultimo_codigo=llaves[k-2].hashCode();//recupero la llave del nodo actual
       
       if(nuevo.hashCode()>ultimo_codigo)//si el nuevo nodo tiene una llave mayor a esa el es el extra
       {
           hijo_extra=nuevo;
       }
       else
       {
           hijo_extra=hijos[k-1];//el ultimo hijo
           hijos[k-1]=null;//limpio el espacio
           hijos_ocupados--;
           add(nuevo);
       }
   }
   
   
   /**
   *Metodo de quicksort para los 2 tipos de array que contienen objetos
   * @param array el arreglo a ordenar
   * @param izq limite izquierdo
   * @param der limite derecho
   */
   private void quickSort(Object[] array, int izq, int der)
   {
       if(izq==der)
           return;
       int i=izq;
       int j=der;
       int pivote=array[(izq+der)/2].hashCode();
       
       while(i<=j)
       {
           while(array[i].hashCode()<pivote)
               i++;
           while(array[j].hashCode()>pivote)
               j--;
           if(i<=j)
           {
               Object en_i=array[i];
               Object en_j=array[j];
               array[i]=en_j;
               array[j]=en_i;
               i++;
               j--;
           }
       }
       if(izq<i)
           quickSort(array, izq, j);
       if(i<der)
           quickSort(array, i, der);
   }
   
   private boolean existe(int llave)
   {
       for(int i=0;i<ocupados;i++)
       {
           if(llaves[i].hashCode()==llave)
               return true;
       }
       return false;
   }
   
   /**
    * Revisa si luego de una insercion el numero de items supera el grado del 
    * nodo y por tanto se genero un item extra.
    * @return <tt>true</tt> si hay una llave/item distinto de 0/null
    * <tt>false</tt> en caso contrario
    */
   public boolean hasExtraItem()
   {
       return llave_extra>0;
   }

   /**
    * Revisa si luego de una insercion el numero de hijos/paginas supera el grado
    * del nod y por tanto se genero un hijo/pagina extra
    * @return <tt>true</tt> si hijo_extra tiene un valor distinto de null;
    * <ff>false</ff> en caso contrario
    */
   public boolean hasExtraChild()
   {
       return hijo_extra!=null;
   }
   public boolean estaLLeno()
   {
       return ocupados==k-1;
   }

   /** 
    * Recupera el ultimo item en el arreglo llaves, actualiza el contador de ocupados
    * y limpia el espacio que ocupaba el item
    * @return item T en llaves[ocupados]
    */
   @SuppressWarnings("unchecked")
   public T pop()
   {
       ocupados--;
       T item=(T)llaves[ocupados];
       llaves[ocupados]=null;
       return item;
   }

   /**
    * Recupera el ultimo nodo en el arreglo hijos, actualiza el contador de hijos_ocupados
    * y limpia el espacio que ocupaba el nodo
    * @return NodoB en hijos[hijos_ocupados]
    */
   public NodoB<T> popB()
   {
       hijos_ocupados--;
       NodoB<T> ultimo=hijos[hijos_ocupados];
       hijos[hijos_ocupados]=null;
       return ultimo;
   }

   /**
    * Revisa si el nodo tiene alguna pagina hijo creada al momento
    * @return <tt>true</tt> si por lo menos tiene una pagina hijo
    * <tt>false</tt> en caso contrario
    */
   public boolean hayHijos()
   {
       return hijos[0]==null||hijos_ocupados==0;
   }

   /**
    * Recupera la llave extra que se crea cuando un item ya no cabe en el nodo actual
    * y limpia el registro de llave_extra
    * @return llave_extra del nodo actual
    */
   public int getExtraLLave()
   {
       int llave=llave_extra;
       llave_extra=0;
       return llave;
   }

   /**
    * Recupera el item extra que se crea cuando un item ya no cabe en el nodo actual
    * y limpia el registro de item_extra
    * @return item_extra del nodo actual
    */
   public T getExtraItem()
   {
       T item = item_extra;
       item_extra=null;
       return item;
   }

   /**
    * Recupera el nodo extra que se crea cuando hay multiples divisiones de nodos y
    * ya no cabe en el nodo actual, ademas limpia el registro de hijo_extra
    * @return hijo_extra del nodo actual
    */
   public NodoB<T> getExtraHijo()
   {
       NodoB<T> extra = hijo_extra;
       hijo_extra=null;
       return extra;
   }  
   public int ownHashCode()
   {
       return super.hashCode();
   }

   @Override
   public int hashCode() {
       return llaves[0].hashCode();
   }
   
   
   
   @Override
   public String toString() {
       String codigo="";
       String id="n"+ownHashCode();
       codigo+=id+"[label=\"";
       //e.g. n55[lavel="<f0>|item[0]|<f1>|item[1]|<f2>|item[2]|<f3>"];\n
       int i;
       for(i=0;i<ocupados;i++)
       {
           codigo+="<f"+i+">|";
           codigo+=llaves[i].toString()+"|";
       }
       codigo+="<f"+i+">\"];\n";
       //hasta aqui esta armado el nodo ahora sus apuntadores a sus hijos
       for(i=0;i<hijos_ocupados;i++)//como hay control de los hijos != null no requiero verificar null
       {
           codigo+=id+":f"+i+"->n"+hijos[i].ownHashCode()+";\n";
       }
       return codigo;
   }
   
   
   
   
   
}
