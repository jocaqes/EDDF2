package org.jocaqes.Misc;

import org.jocaqes.Estructura.Lista;
import org.jocaqes.Estructura.NodoS;

public class Pregunta {
	
	public static enum TipoPregunta{VERDADERO_FALSO,SELECCION}
	private TipoPregunta tipo;
	private String pregunta;
	private String respuesta;
	private Lista<String> opciones;
	
	/**
	 * Constructor para una nueva pregunta 
	 * @param tipo el tipo de pregunta seleccion/verdadero falso
	 * @param respuesta la respuesta correcta
	 * @param opciones las posibles respuestas
	 */
	public Pregunta(TipoPregunta tipo, String respuesta,String pregunta) {
		this.tipo = tipo;
		this.respuesta = respuesta;
		this.pregunta=pregunta;
		this.opciones=new Lista<>();
	}
	
	public Pregunta()
	{
		opciones=new Lista<>();
		tipo=TipoPregunta.VERDADERO_FALSO;
	}
	
	public TipoPregunta getTipo() {
		return tipo;
	}
	public void setTipo(TipoPregunta tipo) {
		if(tipo==TipoPregunta.VERDADERO_FALSO)
			opciones.clear();
		this.tipo = tipo;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public Lista<String> getOpciones() {
		return opciones;
	}
	public void setPregunta(String pregunta)
	{
		this.pregunta=pregunta;
	}
	public String getPregunta()
	{
		if(pregunta==null)
			return "";
		return pregunta;
	}
	public void setOpciones(Lista<String> opciones) {
		this.opciones = opciones;
	}
	public void addOpcion(String opcion)
	{
		opciones.add(opcion);
	}
	public void removeOpcion(String opcion)
	{
		NodoS<String> aux=opciones.raiz;
		boolean encontrado=false;
		int posicion=0;
		while(aux!=null&&!encontrado) {
			if(aux.item.equals(opcion))
				encontrado=true;
			else
			{
				aux=aux.siguiente;
				posicion++;
			}
		}
		opciones.eliminar(posicion);
	}
	public String getSelectTipo()
	{
		String select="";
		if(tipo==TipoPregunta.SELECCION)
		{
			select+="<option>";
			select+=tipo.toString();
			select+="</option>";
			select+="<option>";
			select+=TipoPregunta.VERDADERO_FALSO.toString();
			select+="</option>";
		}
		else
		{
			select+="<option>";
			select+=tipo.toString();
			select+="</option>";
			select+="<option>";
			select+=TipoPregunta.SELECCION.toString();
			select+="</option>";
		}
		return select;
	}
	public String getTextOpcines()
	{
		String text_opciones="";
		NodoS<String> aux=opciones.raiz;
		while(aux!=null)
		{
			text_opciones+="<tr>";
				text_opciones+="<td>";
					text_opciones+="<input type=\"radio\" name=\"respuesta\" value=\""+aux.item+"\">";
				text_opciones+="</td>";
				text_opciones+="<td>";
					text_opciones+=aux.item;
				text_opciones+="</td>";
			text_opciones+="</tr>";
			aux=aux.siguiente;
		}
		return text_opciones;
	}
}
