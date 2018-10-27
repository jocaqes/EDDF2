package org.jocaqes.Misc;

import org.jocaqes.Estructura.ArbolB;

/**
 * Base de datos en memoria para todas las estructuras
 * @author jocaqes
 *
 */
public class DataBase {
	/**
	 * Arbol B de estudiantes de grado 5
	 */
	private static ArbolB<Estudiante> b_estudiantes = new ArbolB<>(5); 
	
	public static ArbolB<Estudiante> getBtreeEstudiantes()
	{
		return b_estudiantes;
	}
	
	
	
	
}
