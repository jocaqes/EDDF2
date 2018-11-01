package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase diseñada unicamente para aceptar el json de la forma {"actividades":[{..},{..}]}
 * @author jocaqes
 *
 */
@XmlRootElement(name="actividades")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Actividades {
	private List<Actividad> actividades;
	
	public Actividades() {actividades=new ArrayList<>();}

	
	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	
	
}
