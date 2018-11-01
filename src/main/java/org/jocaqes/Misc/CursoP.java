package org.jocaqes.Misc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jocaqes.Estructura.TablaHash;

/**
 * Clase de curso con formato para pensum no para estudiante, 
 * eso significa que incluye cuantos creditos da, prerequisitos, etc
 * @author jocaqes
 *
 */
@XmlRootElement(name="curso")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CursoP {
	private String nombre;
	private String codigo;
	private int creditos;
	private String area;
	private List<String> pre; 
	@XmlTransient
	private TablaHash<Alumno> alumnos;
	
	public CursoP() {alumnos=new TablaHash<>();}

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public List<String> getPre() {
		return pre;
	}
	public void setPre(List<String> pre) {
		this.pre = pre;
	}
	@XmlTransient
	public TablaHash<Alumno> getAlumnos() {
		return alumnos;
	}

	@XmlTransient
	@Override
	public String toString() {
		return nombre;
	};
	
	
	
	
	
	
}
