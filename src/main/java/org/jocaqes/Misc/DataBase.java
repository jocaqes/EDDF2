package org.jocaqes.Misc;

import org.jocaqes.Estructura.AVL;
import org.jocaqes.Estructura.ArbolB;
import org.jocaqes.Estructura.Grafo;
import org.jocaqes.Estructura.Lista;

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
	private static Grafo pensum=new Grafo(); 
	private static Lista<Estudiante> posibles_tutores=new Lista<>();
	private static AVL<Tutor> tutores=new AVL<>(); 
	
	public static ArbolB<Estudiante> getBtreeEstudiantes()
	{
		return b_estudiantes;
	}
	public static Grafo getPensum()
	{
		return pensum;
	}
	public static Lista<Estudiante> getPosiblesTutores()
	{
		return posibles_tutores;
	}
	public static AVL<Tutor> getTutores()
	{
		return tutores;
	}
	
	
	
	
}
