package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jocaqes.Estructura.ListaD;
import org.jocaqes.Estructura.NodoL;

@XmlRootElement(name="estudiante")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Estudiante {
	private int carnet;
	private double dpi;
	private String password;
	private String token;
	private String nombre;
	private String apellidos;
	private int creditos;
	private String correo;
	public ListaD<Curso> cursos;
	
	public Estudiante() {cursos=new ListaD<>();}
	
	public Estudiante(int carnet, double dpi, String password, String token, String nombre, String apellidos, int creditos,
			String correo) {
		this.carnet = carnet;
		this.dpi = dpi;
		this.password = password;
		this.token = token;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.creditos = creditos;
		this.correo = correo;
		cursos=new ListaD<>();
	}

	
	public int getCarnet() {
		return carnet;
	}

	public void setCarnet(int carnet) {
		this.carnet = carnet;
	}
	public double getDpi() {
		return dpi;
	}
	public void setDpi(double dpi) {
		this.dpi = dpi;
	}
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
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellido) {
		this.apellidos = apellido;
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
		if(this.cursos==null)
			return out;
		NodoL<Curso> aux=cursos.raiz;
		while(aux!=null)
		{
			out.add(aux.item);
			aux=aux.siguiente;
		}
		return out;
	}

	public void setCursos(List<Curso> cursos) {
		if(cursos!=null&&!cursos.isEmpty()&&this.cursos!=null)
		{
			for(Curso curso:cursos)
			{
				this.cursos.sortedAdd(curso);
			}
		}
	}

	@XmlTransient
	@Override
	public int hashCode() {
		return carnet;
	}
	
	
	
	
	
	
	
}
