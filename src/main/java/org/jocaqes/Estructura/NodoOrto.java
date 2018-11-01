package org.jocaqes.Estructura;

/**
 * Clase hecha para matrices de adyacencia, dispersas u ortononales(en este ultimo caso
 * hay que añadir un atributo para la altura)
 * @author jocaqes
 *
 * @param <S> El valor que se desea almacenar en la matriz, se usa su funcion
 * toString sobrecargada para facilitar la generacion de graficas
 */
public class NodoOrto <S>{
    public NodoOrto<S> up;
    public NodoOrto<S> down;
    public NodoOrto<S> left;
    public NodoOrto<S> right;
    public S item;
    public int col;
    public int row;
    

    /**
     * Genera un objeto NodoOrto on el los valores recibidos como parametros y todos
     * sus lados nulos
     * @param item el valor que guarda el nodo ortogonal
     * @param col posicion numerica de la columna a la que pertenece
     * @param row posicion numerica de la fila a la que pertenece
     */
    public NodoOrto(S item, int col, int row) {
        this.item = item;
        this.col = col;
        this.row=row;
        up=down=left=right=null;
    }

   
    /**
     * Recupera un identificador para su uso en graphviz
     * @return una cadena que identifica el nodo NodoOrto 
     * compuesta de su fila un _ y su columna
     */
    public String id()
    {
        return row+"_"+col;
    }
    
    public void setItem(S item)
    {
    	this.item=item;
    }
    
    @Override
    public String toString() {
        String codigo="";
        String id="n"+row+"_"+col;
        codigo+=id+"[label=\""+item.toString()+"\"];\n";
        /*if(up!=null)//y no es la raiz columna
            codigo+=id+"->n"+up.id()+"[weight=0];\n";*/
        if(down!=null)
        {
    		codigo+=id+"->n"+down.id()+"[dir=both];\n";
        }
        if(right!=null)
        {
            codigo+="{rank=same;\n";
            codigo+=id+"->n"+right.id()+"[dir=both];\n";
            codigo+="};\n";
        }
        //no hay left porque uso dir both para right
        return codigo;
    }
	
}
