package org.jocaqes.Misc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para manejar las actividades disponibles para un tutor academico
 * @author jocaqes
 *
 */
@XmlRootElement(name="actividad")
@XmlAccessorType(XmlAccessType.FIELD)
public class Actividad {
	
	/**
	 *Los posibles tipos para una actividad 
	 */
	public static enum Tipo{TAREA,HOJA_DE_TRABAJO,EXPOSICION,PRACTICA,PROYECTO,CORTO,FINAL}
	private String actividad;
	private String descripcion;
	private int ponderacion;
	private String fecha;
	private Tipo tipo;
	//corto
	
	
	public Actividad() {}
	/**
	 * Constructor para una nueva actividad
	 * @param nombre el nombre de la actividad
	 * @param descripcion una breve descripcion de la actividad
	 * @param ponderacion los puntos que vale la actividad
	 * @param fecha_entrega la fecha limite para entregar la actividad
	 * @param tipo el tipo de actividad
	 */
	public Actividad(String nombre, String descripcion, int ponderacion, String fecha_entrega, Tipo tipo) {
		this.actividad = nombre;
		this.descripcion = descripcion;
		this.ponderacion = ponderacion;
		this.fecha = fecha_entrega;
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return actividad;
	}
	public void setNombre(String nombre) {
		this.actividad = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPonderacion() {
		return ponderacion;
	}
	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}
	public String getFecha_entrega() {
		return fecha;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha = fecha_entrega;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	


}
