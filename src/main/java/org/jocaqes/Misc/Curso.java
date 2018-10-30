package org.jocaqes.Misc;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

//@XmlRootElement(name="curso")
@XmlAccessorType(XmlAccessType.FIELD)
public class Curso {
	public String nombre;
	public int nota;
	public String fecha;
	public String codigoCurso;
	
	public Curso(){}
		
	public Curso(String nombre, int nota, String fecha, String codigo) {
		this.nombre = nombre;
		this.nota = nota;
		this.fecha = fecha;
		this.codigoCurso = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCodigo() {
		return codigoCurso;
	}
	public void setCodigo(String codigo) {
		this.codigoCurso = codigo;
	}

	@Override
	public int hashCode() {//usada para facilitar sortedAdd en lista ordenada de cursos
		int codigo=Integer.parseInt(codigoCurso);
		return codigo;
	}
	
	
	
	
}
