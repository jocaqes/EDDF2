package org.jocaqes.Misc;

import org.jocaqes.Estructura.Encabezado;
import org.jocaqes.Estructura.Matriz;

/**
 * Clase para manejar un tutor, sus alumnos y su control de notas
 * @author jocaqes
 *
 */
public class Tutor {

	private int carnet;
	private String curso;
	private Matriz<Integer, Integer, Actividad> control_notas;
	private int periodo;
	//private Hash<Integer> mis_alumnos;
	
	/**
	 * Constructor para un nuevo tuor
	 * @param carnet el carnet del tutor
	 * @param curso el curso que imparte el tutor
	 * @param periodo el año en que se convirtio en tutor
	 */
	public Tutor(int carnet, String curso, int periodo) {
		this.carnet = carnet;
		this.curso = curso;
		this.periodo = periodo;
		control_notas=new Matriz<>();
	}

	public int getCarnet() {
		return carnet;
	}

	public void setCarnet(int carnet) {
		this.carnet = carnet;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	
	public Matriz<Integer,Integer,Actividad> getControlNotas()
	{
		return control_notas;
	}

	public boolean agregarActividad(Actividad nueva)
	{
		if(control_notas.addColumn(nueva.getActividad(), nueva)==null) 
			return false;
		return true;
	}
	/**
	 * Suma la ponderacion de todas las actividades de control de notas y la retorna
	 * @return un entero con la suma de ponderaciones, si esta vacio el control de notas retorna 0
	 */
	public int getPonderacionTotal()
	{
		Encabezado<Actividad,Integer> aux = control_notas.getRaizColumna();
		int ponderacion_total=0;
		while(aux!=null)
		{	
			ponderacion_total+=aux.item.getPonderacion();
			aux=aux.siguiente;
		}
		return ponderacion_total;
	}
	/**
	 * Codigo para agregar en jsp en un select con todas las actividades cargadas por el tutor
	 * @return una cadena para jsp
	 */
	public String actividades()
	{
		String select="";
		Encabezado<Actividad,Integer> aux = control_notas.getRaizColumna();
		while(aux!=null)
		{
			select+="<option>";
			if(aux.item!=null)
				select+=aux.item.getActividad();
			else
				select+=aux.header;
			select+="</option>\n";
			aux=aux.siguiente;
		}
		return select;
	}
	/**
	 * Revisa el control de notas y obtiene un codigo en formato de graphviz para poder graficar la matriz
	 * @return una cadena con codigo de graphviz
	 */
	public String graficaControlNotas()
	{
		return control_notas.graficaDispersa();
	}
	/**
	 * Elimina una actividad del control de notas 
	 * @param nombre el nombre de la actividad a eliminar
	 * @return <tt>true</tt> si una actividad fue eliminada,
	 * <tt>false</tt> en caso contrario que no existiese la actividad
	 */
	public boolean eliminarActividad(String nombre)
	{
		return control_notas.eliminarColumna(nombre);
	}
	
	@Override
	public String toString() {
		String codigo="";
		codigo+=carnet+"\n"+curso+"\n"+periodo;
		return codigo;
	}
	
	
	
	
	
	
}
