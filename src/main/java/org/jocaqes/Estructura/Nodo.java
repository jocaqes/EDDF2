package org.jocaqes.Estructura;

/**
*
* @author JOSE QUIÑONEZ <jose at jocaqes.org>
*/
public class Nodo <T>{

   /**
    *Rama izquierda del nodo
    */
   public Nodo<T> izq;

   /**
    *Rama derecha del nodo
    */
   public Nodo<T> der;
   public int llave;
   public T item;

   /**
    *Factor de equilibrio, un factor |fe|>1 requiere rotacion
    */
   public int fe;

   /**
    *Construye un nuevo nodo con sus nodos hijo nulos
    */
   public Nodo() {
       izq=der=null;
       fe=0;
   }

   /**
    *Construye un nuevo nodo con sus nodos hijo nulos
    * @param llave el valor con el que se va a ordenar el nodo
    * @param item el item que sera guardado en el nodo
    */
   public Nodo(int llave, T item) {
       this.llave = llave;
       this.item = item;
       izq=der=null;
       fe=0;
   }
   
   /**
    *Genera una rotacion derecha del nodo y sus hijos 
    * @return un nuevo nodo raiz rotado
    */
   public Nodo<T> rotacionDer()
   {
       Nodo<T> q = izq;
       Nodo<T> p = this;
       p.izq=q.der;
       q.der=p;
       return q;
   }
   
   /**
    *Genera una rotaicon izquierda del nodo y sus hijos
    * @return un nuevo nodo raiz rotado
    */
   public Nodo<T> rotacionIzq()
   {
       Nodo<T> q = der;
       Nodo<T> p = this;
       p.der=q.izq;//q.izq siempre existe
       q.izq=p;
       return q;
   }

   @Override
   public String toString() {
       String codigo="";
       String id="n"+this.hashCode();
       codigo+=id+"[label=\""+item.toString()+"\"];\n";
       //codigo+=id+"[label=\""+llave+"\"];\n";
       if(izq!=null)
       {
           codigo+=id+"->n"+izq.hashCode()+";\n";
       }
       if(der!=null)
       {
           codigo+=id+"->n"+der.hashCode()+";\n";
       }
       return codigo;
   }
   
   
}

