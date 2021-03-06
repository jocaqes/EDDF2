package org.jocaqes.Estructura;

import org.jocaqes.Misc.CursoP;

/**
 * Clase grafo lista para manejar cursos del pensum
 * solo puede trabajar con matriz de adyacencia
 * @author jocaqes
 *
 */
public class Grafo {
	
	/**
	 * matriz de adyacencia representacion de un grafo con Cursos del pensum como su contenido
	 */
	private Matriz<CursoP,String,Integer> matriz_adyacencia;

	public Grafo() {
		matriz_adyacencia=new Matriz<>();
	}
	
	/**
	 * Agrega una pareja fila/columna a la matriz, con el mismo encabezado cada una
	 * respectivamente y agrega el curso en la fila. 
	 * @param encabezado el titulo o encabezado para la nueva fila/columna
	 * @param item el curso que se desea guardar en la fila
	 */
	public void agregarEncabezado(String encabezado, CursoP item)
	{
		if(matriz_adyacencia.addRow(encabezado,item)!=null)
			matriz_adyacencia.addColumn(encabezado);
	}
	/**
	 * Agrega los prerequisitos de un curso a la matriz de adyacencia
	 * @param curso El curso al que se desean agregar los prerequisitos
	 */
	public void agregarPre(CursoP curso)
	{
		
		for(String columna:curso.getPre())
		{
			matriz_adyacencia.addCell(columna, curso.getCodigo(), "1",true);
		}
	}
	/**
	 * Recupera el codigo en graphviz del grafo representado como matriz de adyacencia
	 * @return una cadena con codigo en formato de graphviz
	 */
	public String codigoMatriz()
	{
		return matriz_adyacencia.graficaAdyacencia();
	}
	
	public int getCreditos(String codigo_curso)
	{
		CursoP curso=matriz_adyacencia.getRowItem(codigo_curso);
		if(curso==null)
			return 0;
		return curso.getCreditos();
	}
	
	public String getCursos()
	{
		Encabezado<CursoP,String> aux = matriz_adyacencia.getRaizFila();
		String select="";
		while(aux!=null)
		{
			select+="<option>";
			if(aux.item!=null)
				select+=aux.item.getNombre();
			else
				select+=aux.header;
			select+="</option>\n";
			aux=aux.siguiente;
		}
		return select;
	}
	public CursoP getCurso(String nombre)
	{
		return matriz_adyacencia.getRowItem(nombre);
	}
	
	

}
