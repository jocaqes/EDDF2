package org.jocaqes.Misc;

import org.jocaqes.Estructura.ListaCircular;
import org.jocaqes.Estructura.NodoL;

/**
 * Clase para el manejo facil de la insercion y busqueda de cortos
 * @author jocaqes
 *
 */
public class ControlCortos {
	private ListaCircular<Corto> cortos;
	
	/**
	 * Constructor para inicializar la lista de cortos
	 */
	public ControlCortos()
	{
		cortos=new ListaCircular<>();
	}
	
	public void agregarCorto(Corto corto)
	{
		System.out.println("ControlCortos->agregarCorto->"+corto.getTutorName());
		cortos.add(corto);
	}
	public Corto buscarCorto(String room)
	{
		NodoL<Corto> aux=cortos.raiz;
		int limite=cortos.count;
		boolean encontrado=false;
		for(int i=0;i<limite;i++)
		{
			if(aux.item.getRoom().toLowerCase().equals(room.toLowerCase()))
			{
				i=limite;
				encontrado=true;
			}
			else
				aux=aux.siguiente;
		}
		if(encontrado)
			return aux.item;
		return null;
	}
	public String selectCortosTutor(int carne)
	{
		String select="";
		int limite=cortos.count;
		NodoL<Corto> aux = cortos.raiz;
		for(int i=0;i<limite;i++)
		{
			if(aux.item.getTutor()==carne)
			{
				select+="<option>";
				select+=aux.item.getNombre();
				select+="</option>";
			}
			aux=aux.siguiente;
		}
		return select;
	}
	public boolean habilitarDesabilidat(boolean habilitar, String nombre_corto)
	{
		return false;
	}
}
