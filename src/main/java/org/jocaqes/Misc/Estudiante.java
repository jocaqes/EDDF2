package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jocaqes.Estructura.ListaD;
import org.jocaqes.Estructura.NodoL;
import org.jocaqes.NewMappables.MyDListAdapter;

@XmlRootElement(name="estudiante")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Estudiante {
	private int carne;
	//private long dpi;
	private String password;
	private String token;
	private String nombre;
	private String apellido;
	private int creditos;
	private String correo;
	private ListaD<Curso> cursos;
	//private List<Curso> cursos;
	
	public Estudiante() {}
	
	public Estudiante(int carne, String password, String token, String nombre, String apellido, int creditos,
			String correo) {
		this.carne = carne;
		//this.dpi = dpi;
		this.password = password;
		this.token = token;
		this.nombre = nombre;
		this.apellido = apellido;
		this.creditos = creditos;
		this.correo = correo;
		cursos=new ListaD<>();
		cursos.add(new Curso("mate", "matematica", 6, 106));
		cursos.add(new Curso("fisica1", "fisica", 4, 202));
	}

	
	public int getCarne() {
		return carne;
	}

	public void setCarne(int carne) {
		this.carne = carne;
	}
	/*
	public long getDpi() {
		return dpi;
	}
	public void setDpi(long dpi) {
		this.dpi = dpi;
	}*/
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@XmlElement(name="cursos")
	public List<Curso> getCursos() {
		List<Curso> out=new ArrayList<>();
		NodoL<Curso> aux=cursos.raiz;
		while(aux!=null)
		{
			out.add(aux.item);
			aux=aux.siguiente;
		}
		return out;
	}

	public void setCursos(List<Curso> cursos) {
		for(Curso curso:cursos)
		{
			this.cursos.add(curso);
		}
	}
	
	





	
	/*public java.util.List<Curso> getCursos() {
		java.util.List<Curso> cursos = new java.util.ArrayList<>();
		NodoL<Curso> aux=this.cursos.raiz;
		while(aux!=null)
		{
			cursos.add(aux.item);
			aux=aux.siguiente;
		}
		return cursos;
	}

	public void setCursos(java.util.List<Curso> cursos ) {
		for(Curso curso:cursos)
			this.cursos.add(curso);
	}*/
	
	
	
	
	
	
}
