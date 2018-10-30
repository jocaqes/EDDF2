package org.jocaqes.EDDF2.services;

import java.util.List;

import org.jocaqes.Estructura.ArbolB;
import org.jocaqes.Estructura.Grafo;
import org.jocaqes.Misc.CursoP;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Estudiante;

/**
 * Clase para el manejo de los servicios del administrador
 * @author jocaqes
 *
 */
public class AdminService {
	private ArbolB<Estudiante> arbol_b=DataBase.getBtreeEstudiantes();
	private Grafo pensum=DataBase.getPensum();
	
	public AdminService() {}
	
	
	/**
	 * Agrega un nuevo estudiante a la base de datos dentro de un arbol B 
	 * @param nuevo el estudiante a agregar
	 * @param carne el carne del estudiante que es su identificador unico
	 * @return <tt>true</tt> si la insercion fue exitosa;<tt>false</tt> en 
	 * caso contrario. Ver clase Arbol B para mas informacion.
	 */
	public boolean agregar(Estudiante nuevo, int carne)
	{
		return arbol_b.add(nuevo, carne);
	}
	/**
	 * Busca en la base de datos en el arbol B el estudiante con el carne proporcinado
	 * @param carne el carne del estudiante
	 * @return el estudiante encontrado; en caso de que no exista retorna null
	 */
	public Estudiante buscar(int carne)
	{
		return arbol_b.buscar(carne);
	}
	
	/**
	 * Toma todos los estudiantes de una lista y los agrega al arbol B
	 * @param estudiantes lista que contiene los estudiantes a almacenar
	 */
	public void cargar(List<Estudiante> estudiantes)//la lista solo se usa para agilizar la respuesta json, recupero una lista y agrego los items al arbol
	{
		while(!estudiantes.isEmpty())
		{
			Estudiante nuevo=estudiantes.remove(0);
			arbol_b.add(nuevo, nuevo.getCarnet());
		}
	}
	/**
	 * Toma todos los cursos de una lista y los agrega en un grafo
	 * @param cursos lista de todos los cursos que se desean guardar en el pensum 
	 */
	public void cargarCursos(List<CursoP> cursos)
	{
		for(CursoP curso:cursos)
		{
			pensum.agregarEncabezado(curso.getCodigo(), curso);
		}
		while(!cursos.isEmpty())
		{
			CursoP nuevo=cursos.remove(0);
			pensum.agregarPre(nuevo);
		}
	}
	
	public String debug()
	{
		return pensum.codigoGrafo();
	}
	
}
