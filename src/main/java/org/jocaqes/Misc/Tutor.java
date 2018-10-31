package org.jocaqes.Misc;

import org.jocaqes.Estructura.Matriz;

/**
 * Clase para manejar un tutor, sus alumnos y su control de notas
 * @author jocaqes
 *
 */
public class Tutor {

	private int carnet;
	private String curso;
	private Matriz<Integer, Integer> control_notas;
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
	
	public Matriz<Integer,Integer> getControlNotas()
	{
		return control_notas;
	}

	@Override
	public String toString() {
		String codigo="";
		codigo+=carnet+"\n"+curso+"\n"+periodo;
		return codigo;
	}
	
	
	
	
	
	
}
