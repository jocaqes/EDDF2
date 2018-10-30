package org.jocaqes.EDDF2.services;

import org.jocaqes.Estructura.ArbolB;
import org.jocaqes.Estructura.Grafo;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Estudiante;

/**
 * Clase para manejo de servicios miscelaneos como graficas, login, etc
 * @author jocaqes
 *
 */
public class MiscService {
	private ArbolB<Estudiante> arbol_b=DataBase.getBtreeEstudiantes();
	private Grafo pensum=DataBase.getPensum();
	
	/**
	 * Busca un estudiante por su carne y luego verifica si la contraseña
	 * provista coincide con la del estudiante en base de datos.
	 * @param carne usuario con el que el estudiante ingresa a la plataforma
	 * @param password contraseña del estudiante
	 * @return <tt>true</tt> si el carne y el password coinciden para el estudiante
	 * encontrado; <tt>false</tt> si no coinciden o si el estudiante no existe
	 */
	public boolean login(int carne, String password)
	{
		Estudiante encontrado=arbol_b.buscar(carne);
		if(encontrado==null)
			return false;
		if(encontrado.getPassword().equals(password))
			return true;
		return false;
	}
	
	/**
	 * Obtiene el codigo para generar la grafica del arbol b
	 * @return Una cadena con codigo para graphviz del arbol b
	 */
	public String graficaArbolB()
	{
		return arbol_b.grafica();
	}
	
	public String codigoMatrizAdyacencia()
	{
		return pensum.codigoMatriz();
	}
	
	
	/**
	 * Revisa si una cadena es un numero o no
	 * @param number la cadena que se quiere revisar
	 * @return <tt>true</tt> si efectivamente es un numero,
	 * <tt>false</tt> en caso contrario
	 */
	public boolean isNumber(String number)
	{
		try
		{
			Integer.parseInt(number);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
}
