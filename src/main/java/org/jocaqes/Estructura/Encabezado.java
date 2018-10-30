package org.jocaqes.Estructura;

/**
 * Clase usada como contenedor para los encabezados de una matriz
 * @author jocaqes
 *
 * @param <T> El valor que se espera guardar en el encabezado, solo se usa en matriz de adyacencia 
 * @param <S> El valor que se espera guardar en el nodo, puede ser igual al del encabezado
 */
public class Encabezado <T,S>{
    public String header;
    public int index;
    public NodoOrto<S> raiz;
    public Encabezado<T,S> siguiente;
    public boolean columna;
    public T item;

    /**
     * Constructor para generar un encabezado con un nombre y posicion especificos
     * @param header el nombre para el encabezado
     * @param index  la posicion del encabezado
     * @param columna indica si es columna o fila
     */
    public Encabezado(String header, int index, boolean columna) {
        this.header = header;
        this.index = index;
        this.columna=columna;
        raiz=null;
        siguiente=null;
    }
    
    /**
     * Constructor para generar un encabezado con nombre, posicion especificos y un 
     * item para matriz de adyacencia
     * @param header el nombre para el encabezado
     * @param index la posicvion del encabezado
     * @param columna indica si es columna o fila
     * @param item valor para el encabezado si se usa en matriz de adyacencia
     */
    public Encabezado(String header, int index, boolean columna, T item) {
		super();
		this.header = header;
		this.index = index;
		this.columna = columna;
		this.item = item;
		raiz=null;
		siguiente=null;
	}


	/**
     * Metodo sobrecargado para generar codigo apto para graphviz
     */
    @Override
    public String toString() {
        String codigo="";
        String id="n"+this.hashCode();
        codigo+=id+"[label=\""+header+"\"];\n";
        if(columna)//raiz columna
        {
            if(siguiente!=null)
            {
                codigo+="{rank=same;\n";
                codigo+=id+"->n"+siguiente.hashCode()+"[dir=both];\n";
                codigo+="};\n";
            }
            if(raiz!=null)
            {
                codigo+=id+"->n"+raiz.id()+"[dir=both];\n";
                if(raiz.row>0)
                	codigo+=id+"->n0_"+index+"[color=transparent];\n";
            }
            
        }
        else
        {
            if(siguiente!=null)
                codigo+=id+"->n"+siguiente.hashCode()+"[dir=both];\n";
            if(raiz!=null)
            {
                codigo+="{rank=same;\n";
                codigo+=id+"->n"+raiz.id()+"[dir=both,weight=0];\n";   
                if(raiz.col>0)
                	codigo+=id+"->n"+index+"_0"+"[color=transparent];\n";
                codigo+="};\n";
            }
        }
        return codigo;
    }
}
