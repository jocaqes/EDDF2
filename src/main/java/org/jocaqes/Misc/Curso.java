package org.jocaqes.Misc;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name="curso")
@XmlAccessorType(XmlAccessType.FIELD)
public class Curso {
	public String nombre;
	public String area;
	public int creditos;
	public int codigo;
	
	public Curso(){}
		
	public Curso(String nombre, String area, int creditos, int codigo) {
		this.nombre = nombre;
		this.area = area;
		this.creditos = creditos;
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
}
