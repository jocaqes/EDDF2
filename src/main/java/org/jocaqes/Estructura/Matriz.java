package org.jocaqes.Estructura;

/**
 * Clase para manejo de matrices, si se desea manejar matriz ortogonal es necesario
 * agregar metodos para manejar niveles
 * @author jocaqes
 *
 * @param <T> El valor que se espera guardar en los encabezados si es matriz de adyacencia
 * @param <S> El valor que se espera guardar en los nodos ortogonales, pueden ser iguales
 */
public class Matriz <T,S>{
    private Encabezado<T,S> raiz_columnas;
    private Encabezado<T,S> raiz_filas;
    public int columnas;
    public int filas;

    /**
     * Constructor para generar una matriz vacia
     */
    public Matriz() {
        columnas=filas=0;
        raiz_columnas=raiz_filas=null;
    }
    /**
     * Revisa si no hay ninguna columna actualmente en la matriz
     * @return <tt>true</tt> si la matriz no tiene columnas
     * <tt>false</tt> en caso contrario
     */
    private boolean columnasVacias()
    {
        return raiz_columnas==null;
    }
    /**
     * Revisa si no hay ninguna fila actualmente en la matriz
     * @return <tt>true</tt> si la matriz no tiene filas
     * <tt>false</tt> en caso contrario
     */
    private boolean filasVacias()
    {
        return raiz_filas==null;
    }
    /**
     * Agrega una nueva columna a la matriz con el cuidado que el encabezado no este repetido.
     * @param encabezado la etiqueta para identificar la columna
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el encabezado estaba repetido
     */
    public boolean addColumn(String encabezado)
    {
        if(columnasVacias())
        {
            raiz_columnas=new Encabezado<>(encabezado, columnas,true);
        }
        else
        {
            Encabezado<T,S> aux=raiz_columnas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
                return false;
            
            aux.siguiente=new Encabezado<>(encabezado, columnas+1,true);
        }
        columnas++;
        return true;
    }
    /**
     * Agrega una nueva fila a la matriz con el cuidado que el encabezado no este repetido.
     * @param encabezado la etiqueta para identificar la fila
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el encabezado estaba repetido
     */
    public boolean addRow(String encabezado)
    {
        if(filasVacias())
        {
            raiz_filas=new Encabezado<>(encabezado, filas,false);
        }
        else
        {
            Encabezado<T,S> aux=raiz_filas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
                return false;
            aux.siguiente=new Encabezado<>(encabezado, filas+1,false);
        }
        filas++;
        return true;
    }
    /**
     * Agrega una nueva fila a la matriz con el cuidado que el encabezado no este repetido.
     * @param encabezado la etiqueta para identificar la fila
     * @param item el valor que se desea guardar si es matriz de adyacencia
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el encabezado estaba repetido
     */
    public boolean addRow(String encabezado, T item)
    {
        if(filasVacias())
        {
            raiz_filas=new Encabezado<>(encabezado, filas,false,item);
        }
        else
        {
            Encabezado<T,S> aux=raiz_filas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
                return false;
            aux.siguiente=new Encabezado<>(encabezado, filas+1,false,item);
        }
        filas++;
        return true;
    }
    
    /**
     * Agrega una nueva celda a la matriz, teniendo cuidado de que no haya ya 
     * una celda en el espacio que desea ocupar la nueva celda
     * @param encabezado_columna nombre de la columna donde se desea insertar la celda
     * @param encabezado_fila nombre de la fila donde se desea insertar la celda
     * @param item el valor que se desea guardar en la celda
     * @param crear indica si al no existir una fila/columna debe crearla
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el espacio estaba ocupado
     */
    public boolean addCell(String encabezado_columna, String encabezado_fila, S item, boolean crear)
    {
        Encabezado<T,S> col_aux=raiz_columnas;
        boolean encontrado=false;
        while(col_aux!=null&&!encontrado)
        {
            if(col_aux.header.toLowerCase().equals(encabezado_columna))
                encontrado=true;
            else
                col_aux=col_aux.siguiente;
        }
        if(col_aux==null&&crear)
        {
        	addColumn(encabezado_columna);
        	return addCell(encabezado_columna, encabezado_fila, item, crear);
        }
        if(!encontrado||col_aux==null)
            return false;
        Encabezado<T,S> row_aux=raiz_filas;
        encontrado=false;
        while(row_aux!=null&&!encontrado)
        {
            if(row_aux.header.toLowerCase().equals(encabezado_fila))
                encontrado=true;
            else
                row_aux=row_aux.siguiente;
        }
        if(row_aux==null&&crear)
        {
        	addRow(encabezado_fila);
        	return addCell(encabezado_columna, encabezado_fila, item, crear);
        }
        if(!encontrado||row_aux==null)
            return false;
        int column=col_aux.index;
        int row=row_aux.index;
        NodoOrto<S> nuevo=new NodoOrto<>(item,column,row);
        if(addToRow(nuevo,row_aux))
        {
            addToCol(nuevo,col_aux);
            return true;
        }
        return false;
    }
    /**
     * Revisa si una celda intenta ser insertada en un espacio ocupado
     * @param col la columna del espacio ocupado
     * @param row la fila del espacio ocupado
     * @param celda contiene los valores de referencia
     * @return <tt>true</tt> si el espacio esta ocupado
     * <tt>false</tt> si el espacio esta libre
     */
    private boolean celdaIgual(int col, int row, NodoOrto<S> celda)
    {
        return celda.col==col&&celda.row==row;
    }
    /**
     * Maneja la insercion de una celda en la posicion correcta dentro de una fila
     * y se encarga de los "apuntadores" a su izquierda y derecha
     * @param nuevo la nueva celda a insertar
     * @param fila la fila donde se desea insertar la celda
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el espacio estaba ocupado
     */
    private boolean addToRow(NodoOrto<S> nuevo, Encabezado<T,S> fila)
    {
        if(fila.raiz==null)//esta vacia
        {
            fila.raiz=nuevo;
        }
        else
        {
            NodoOrto<S> aux=fila.raiz;
            boolean insertado=false;
            boolean repetido=false;
            while(aux!=null&&!insertado&&!repetido)
            {
                if(celdaIgual(aux.col, aux.row, nuevo))
                    repetido=true;
                else if(nuevo.col<aux.col)
                {
                    if(aux.left==null)//aux es raiz
                    {
                        nuevo.right=aux;
                        aux.left=nuevo;
                        fila.raiz=nuevo;//cambio la raiz
                    }
                    else
                    {
                        nuevo.left=aux.left;
                        aux.left.right=nuevo;
                        aux.left=nuevo;
                        nuevo.right=aux;
                    }
                    insertado=true;
                }
                else if(aux.right==null)//me toca al final
                {
                    aux.right=nuevo;
                    nuevo.left=aux;
                    insertado=true;
                }
                aux=aux.right;   
            }
            return insertado;
        }
        return true;
    }
    /**
     * Maneja la insercion de una celda en la posicion correcta dentro de una columna
     * y se encarga de los "apuntadores" arriba y abajo de si
     * @param nuevo la nueva celda a insertar
     * @param columna la columna donde se desea insertar la celda
     */
    private void addToCol(NodoOrto<S> nuevo, Encabezado<T,S> columna)
    {
        if(columna.raiz==null)//esta vacia
        {
            columna.raiz=nuevo;
        }
        else
        {
            NodoOrto<S> aux=columna.raiz;
            boolean insertado=false;
            boolean repetido=false;
            while(aux!=null&&!insertado&&!repetido)
            {
                if(celdaIgual(aux.col, aux.row, nuevo))//innecesario
                    repetido=true;
                else if(nuevo.row<aux.row)
                {
                    if(aux.up==null)//aux es raiz
                    {
                        nuevo.down=aux;
                        aux.up=nuevo;
                        columna.raiz=nuevo;//cambio la raiz
                    }
                    else
                    {
                        nuevo.up=aux.up;
                        aux.up.down=nuevo;
                        aux.up=nuevo;
                        nuevo.down=aux;
                    }
                    insertado=true;
                }
                else if(aux.down==null)//me toca al final
                {
                    aux.down=nuevo;
                    nuevo.up=aux;
                    insertado=true;
                }
                aux=aux.down;   
            }
        }
    }
    /**
     * Genera un texto con la representacion grafica de la matriz en codigo
     * para graphviz
     * @return una cadena con el codigo graphviz de la matriz
     */
    public String graficaDispersa()
    {
        String codigo="digraph G{\n";
        codigo+="node[shape=box,height=0.5,width=1.5,fixedsize=true];\n";
        codigo+="splines=line;\n";
        codigo+=codigoColumnas(raiz_columnas);
        codigo+=codigoFilas(raiz_filas);
        codigo+="}";
        return codigo;
    }
    private String codigoColumnas(Encabezado<T,S> raiz)
    {
        String codigo="";
        if(raiz==null)
            return codigo;
        codigo+="esquina[color=white;label=\"\"];\n";
        codigo+="{rank=same;\n";
        codigo+="esquina->n"+raiz.hashCode()+"[color=transparent];\n";
        codigo+="};\n";
        Encabezado<T,S> aux=raiz;
        while(aux!=null)
        {
            codigo+=aux.toString();
            aux=aux.siguiente;
        }
        return codigo;
    }
    private String codigoFilas(Encabezado<T,S> raiz)
    {
        String codigo="";
        if(raiz==null)
            return codigo;
        codigo+="esquina->n"+raiz.hashCode()+"[color=transparent];\n";
        Encabezado<T,S> aux=raiz;
        while(aux!=null)
        {
            codigo+=aux.toString();
            codigo+=codigoCeldas(aux);
            aux=aux.siguiente;
        }
        return codigo;
    }
    private String codigoCeldas(Encabezado<T,S> fila)
    {
        String codigo="";
        if(fila==null||fila.raiz==null)
            return codigo;
        /*NodoOrto<S> celda=fila.raiz;
        while(celda!=null)
        {
            codigo+=celda.toString();
            celda=celda.right;
        }*/
        NodoOrto<S> celda=fila.raiz;
        for(int i=0;i<columnas;i++)
        {
        	if(celda!=null&&celda.col!=i)
        	{
        		if(i+1<columnas)
        		{
	        		codigo+="n"+fila.index+"_"+i+"[label=\"\",color=white];\n";
	        		codigo+="n"+fila.index+"_"+i+"->n"+fila.index+"_"+(i+1)+"[color=transparent];\n";
        		}
        	}
        	else if(celda!=null)
        	{
        		codigo+=celda.toString();
        		celda=celda.right;
        	}
        }
        return codigo;
    }

}
