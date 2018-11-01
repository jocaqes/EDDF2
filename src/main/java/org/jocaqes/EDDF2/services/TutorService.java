package org.jocaqes.EDDF2.services;

import java.util.List;

import org.jocaqes.Misc.Actividad;
import org.jocaqes.Misc.DataBase;
import org.jocaqes.Misc.Tutor;

/**
 * Clase para el manejo de los servicios de untutor
 * @author jocaqes
 *
 */
public class TutorService {

	public TutorService() {
	}
	
	public boolean cargar(List<Actividad> actividades, int carne)
	{
		Tutor tutor = DataBase.getTutores().buscar(carne);
		if(tutor==null)
			return false;
		while(!actividades.isEmpty()) {
			Actividad nueva = actividades.remove(0);
			tutor.agregarActividad(nueva);
		}
		return true;
	}
}
