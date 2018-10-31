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
        raiz_columnas=null;
    	raiz_filas=null;
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
     * @return El nuevo encabezado si la insercion fue exitosa
     * <tt>null</tt> si el encabezado estaba repetido
     */
    public Encabezado<T, S> addColumn(String encabezado)
    {
        if(columnasVacias())
        {
            raiz_columnas=new Encabezado<>(encabezado, columnas,true);
            columnas++;
            return raiz_columnas;
        }
        else
        {
            Encabezado<T,S> aux=raiz_columnas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado.toLowerCase()))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
            	return null;
            aux.siguiente=new Encabezado<>(encabezado, columnas,true);
            columnas++;
            return aux.siguiente;
        }
    }
    /**
     * Agrega una nueva fila a la matriz con el cuidado que el encabezado no este repetido.
     * @param encabezado la etiqueta para identificar la fila
     * @return un encabezado si la insercion fue exitosa
     * <tt>null</tt> si el encabezado estaba repetido
     */
    public Encabezado<T, S> addRow(String encabezado)
    {
        if(filasVacias())
        {
            raiz_filas=new Encabezado<>(encabezado, filas,false);
            filas++;
            return raiz_filas;
        }
        else
        {
            Encabezado<T,S> aux=raiz_filas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado.toLowerCase()))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
            {
            	System.out.println("hubo repetido");
                return null;
            }
            aux.siguiente=new Encabezado<>(encabezado, filas,false);
            filas++;
            return aux.siguiente;
        }
    }
    /**
     * Agrega una nueva fila a la matriz con el cuidado que el encabezado no este repetido.
     * @param encabezado la etiqueta para identificar la fila
     * @param item el valor que se desea guardar si es matriz de adyacencia
     * @return <tt>true</tt> si la insercion fue exitosa
     * <tt>false</tt> si el encabezado estaba repetido
     */
    public Encabezado<T, S> addRow(String encabezado, T item)
    {
        if(filasVacias())
        {
            raiz_filas=new Encabezado<>(encabezado, filas,false,item);
            filas++;
            return raiz_filas;
        }
        else
        {
            Encabezado<T,S> aux=raiz_filas;
            boolean repetido=false;
            while(aux.siguiente!=null&&!repetido)
            {
                if(aux.header.toLowerCase().equals(encabezado.toLowerCase()))
                    repetido=true;
                aux=aux.siguiente;
            }
            if(repetido)
                return null;
            aux.siguiente=new Encabezado<>(encabezado, filas,false,item);
            filas++;
            return aux.siguiente;
        }
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
    	Encabezado<T,S> row_aux=getHeader(encabezado_fila, false);
        if(row_aux==null&&crear)
        {
        	row_aux=addRow(encabezado_fila);
        	addColumn(encabezado_fila);
        	//return addCell(encabezado_columna, encabezado_fila, item, crear);
        }
        if(row_aux==null)
            return false;
        Encabezado<T,S> col_aux=getHeader(encabezado_columna, true);
        if(col_aux==null&&crear)
        {
        	col_aux=addColumn(encabezado_columna);
        	addRow(encabezado_columna);
        	//return addCell(encabezado_columna, encabezado_fila, item, crear);
        }
        if(col_aux==null)
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
        NodoOrto<S> celda=fila.raiz;
        while(celda!=null)
        {
            codigo+=celda.toString();
            celda=celda.right;
        }
        return codigo;
    }
    
    public String graficaAdyacencia() {
    	String codigo="";
    	codigo+="graph G{\n";
    	codigo+="node[shape=plaintext];\n";
    	codigo+="matriz[label=<<table border=\"0\" cellspacing=\"0\" cellborder=\"1\">];\n";
    	codigo+="<tr>\n";
    	for(int i=-1;i<columnas;i++)//generamos primero los encabezados, y una esquina
    	{
    		codigo+="<td width=\"50\" height=\"50\" fixedsize=\"true\">";
    		codigo+=getHeader(i, true);
    		codigo+="</td>";
    	}
    	codigo+="</tr>\n";
    	Encabezado<T, S> fila_actual=raiz_filas;
    	NodoOrto<S> celda_actual;
    	for(int i=0;i<filas;i++)
    	{
    		celda_actual=fila_actual.raiz;
    		codigo+="<tr>\n";
    		codigo+="<td width=\"50\" height=\"50\" fixedsize=\"true\">";
    		codigo+=fila_actual.header;
    		codigo+="</td>\n";
    		for(int j=0;j<columnas;j++)
    		{
    			codigo+="<td width=\"50\" height=\"50\" fixedsize=\"true\">";
    			if(celda_actual!=null&&celda_actual.col==j)
    			{
    				codigo+=celda_actual.item.toString();
    				celda_actual=celda_actual.right;
    			}
    			else
    				codigo+="0";
    			codigo+="</td>\n";
    		}
    		codigo+="</tr>\n";
    		fila_actual=fila_actual.siguiente;
    	}
    	codigo+="</table>>];\n}";
    	return codigo;
    }
    private String getHeader(int index, boolean columna)
    {
    	if((index<0||index>=columnas)&&columna)//se espera matriz cuadrada asi que filas=columnas
    		return "";
    	if((index<0||index>=filas)&&!columna)//se espera matriz cuadrada asi que filas=columnas
    		return "";
    	if(columna)
    	{
    		Encabezado<T,S> aux_columna=raiz_columnas;
    		for(int i=0;i<index;i++)
    		{
    			aux_columna=aux_columna.siguiente;
    		}
    		return aux_columna.header;
    	}
    	else
    	{
    		Encabezado<T,S> aux_fila=raiz_filas;
    		for(int i=0;i<index;i++)
    		{
    			aux_fila=aux_fila.siguiente;
    		}
    		return aux_fila.header;
    	}
    }
    private Encabezado<T, S> getHeader(String header, boolean columna)
    {
    	if(columna)
    	{
    		Encabezado<T,S> aux_columna=raiz_columnas;
    		boolean encontrado=false;
    		while(aux_columna!=null&&!encontrado)
    		{
    			if(aux_columna.header.toLowerCase().equals(header.toLowerCase()))
    				encontrado=true;
    			else
    				aux_columna=aux_columna.siguiente;
    		}
    		return aux_columna;
    	}
    	else
    	{
    		Encabezado<T,S> aux_fila=raiz_filas;
    		boolean encontrado=false;
    		while(aux_fila!=null&&!encontrado)
    		{
    			if(aux_fila.header.toLowerCase().equals(header.toLowerCase()))
    				encontrado=true;
    			else
    				aux_fila=aux_fila.siguiente;
    		}
    		return aux_fila;
    	}
    }
    public T getHeader(String header)
    {
    	Encabezado<T,S> aux_fila=raiz_filas;
		boolean encontrado=false;
		while(aux_fila!=null&&!encontrado)
		{
			if(aux_fila.header.toLowerCase().equals(header.toLowerCase()))
				encontrado=true;
			else
				aux_fila=aux_fila.siguiente;
		}
		if(aux_fila==null)
			return null;		
		return aux_fila.item;
    }
    public Encabezado<T, S> getRaizFila()
    {
    	return raiz_filas;
    }
    

}
