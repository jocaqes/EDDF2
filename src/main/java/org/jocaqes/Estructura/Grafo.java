package org.jocaqes.Estructura;

import org.jocaqes.Misc.CursoP;

public class Grafo {
	
	/**
	 * matriz de adyacencia representacion de un grafo con Cursos del pensum como su contenido
	 */
	private Matriz<CursoP,String> matriz_adyacencia;

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
		if(matriz_adyacencia.addRow(encabezado,item))
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
	
	public String codigoGrafo()
	{
		return matriz_adyacencia.graficaDispersa();
	}
	

}
