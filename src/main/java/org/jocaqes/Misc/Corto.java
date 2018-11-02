package org.jocaqes.Misc;

import org.jocaqes.Estructura.Lista;
import org.jocaqes.Estructura.NodoS;

public class Corto {
	private String nombre;
	private int tutor;
	private String room;
	private Lista<Pregunta> preguntas;
	//private Lista<> respuestas;
	private boolean habilitado;
	
	public Corto()
	{
		preguntas=new Lista<>();
	}
	
	public Corto(String nombre, int tutor, String room, boolean habilitado) {
		this.nombre = nombre;
		this.tutor = tutor;
		this.room = room;
		this.habilitado = habilitado;
		preguntas=new Lista<>();
	}
	

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTutor() {
		return tutor;
	}
	public void setTutor(int tutor) {
		this.tutor = tutor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	public Lista<Pregunta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(Lista<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	public void addPregunta(Pregunta pregunta)
	{
		preguntas.add(pregunta);
	}
	public void eliminarPregunta(String pregunta)
	{
		NodoS<Pregunta> aux=preguntas.raiz;
		boolean encontrado=false;
		int posicion=0;
		while(aux!=null&&!encontrado)
		{
			if(aux.item.getPregunta().equals(pregunta))
				encontrado=true;
			else
			{
				posicion++;
				aux=aux.siguiente;
			}
		}
		preguntas.eliminar(posicion);
	}
	public String getTutorName()
	{
		if(DataBase.getTutores().buscar(tutor)!=null)
			return DataBase.getBtreeEstudiantes().buscar(tutor).getNombre();
		return String.valueOf(tutor);
	}
	public String testPreguntas()
	{
		String salida="";
		NodoS<Pregunta> aux=preguntas.raiz;
		while(aux!=null)
		{
			salida+=aux.item.getPregunta();
			aux=aux.siguiente;
		}
		return salida;
	}
	
	
	
	
	
	/*
	 *  Nombre del Corto
 Tutor académico
 Room
 Lista de Preguntas
 Lista de respuestas
	 */
}

