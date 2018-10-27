package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase diseñada unicamente para aceptar el json de la forma {"estudiantes":[{..},{..}]}
 * @author jocaqes
 *
 */
@XmlRootElement(name="estudiantes")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Estudiantes {

	private List<Estudiante> estudiantes;
	
	public Estudiantes() {estudiantes=new ArrayList<>();}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}
	
}
