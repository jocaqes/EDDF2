package org.jocaqes.Misc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase diseñada unicamente para aceptar el json de la forma {"cursos":[{..},{..}]}
 * @author jocaqes
 *
 */
@XmlRootElement(name="cursos")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Cursos {
	private List<CursoP> cursos;
	
	public Cursos()
	{
		cursos=new ArrayList<>();
	}

	public List<CursoP> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoP> cursos) {
		this.cursos = cursos;
	}
	
	
}

