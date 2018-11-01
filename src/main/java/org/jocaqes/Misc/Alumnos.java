package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase contenedora para carga masiva de alumnos a un curso
 * @author jocaqes
 *
 */
@XmlRootElement(name="estudiantes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Alumnos {
	private List<Integer> estudiantes;
	
	public Alumnos() {estudiantes=new ArrayList<>();}

	
	
	public List<Integer> getEstudiantes() {
		return estudiantes;
	}
	public void setEstudiantes(List<Integer> estudiantes) {
		this.estudiantes = estudiantes;
	}


	
	

}
