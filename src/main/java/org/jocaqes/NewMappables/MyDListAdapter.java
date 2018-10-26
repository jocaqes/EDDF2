package org.jocaqes.NewMappables;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jocaqes.Estructura.ListaD;
import org.jocaqes.Estructura.NodoL;
import org.jocaqes.Misc.Curso;

public class MyDListAdapter extends XmlAdapter<List<Curso>,ListaD<Curso>>{

	@Override
	public List<Curso> marshal(ListaD<Curso> v) throws Exception {
		NodoL<Curso> aux=v.raiz;
		List<Curso> out=new ArrayList<>();
		while(aux!=null)
		{
			out.add(aux.item);
			aux=aux.siguiente;
		}
		return out;
	}

	@Override
	public ListaD<Curso> unmarshal(List<Curso> v) throws Exception {
		ListaD<Curso> out = new ListaD<>();
		for(Curso curso:v)
			out.add(curso);
		return out;
	}



}
