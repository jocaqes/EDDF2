package org.jocaqes.Misc;

/**
 * Clase para contener solo informacion básica de un alumno
 * y retornar toda su info al buscarlo en arbol B
 * @author jocaqes
 *
 */
public class Alumno {
	private int carne;
	private String nombre;
	private String apellidos;
	private String correo;
	
	public Alumno(int carne, String nombre, String apellidos ,String correo) {
		this.carne = carne;
		this.nombre = nombre;
		this.correo = correo;
		this.apellidos = apellidos;
	}

	public int getCarne() {
		return carne;
	}
	public void setCarne(int carne) {
		this.carne = carne;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	@Override
	public String toString() {
		if(DataBase.getBtreeEstudiantes().buscar(carne)!=null)
			return DataBase.getBtreeEstudiantes().buscar(carne).toString();
		String codigo="";
		codigo+=nombre+" "+apellidos+"&#92;n";
		codigo+="carne:"+carne+"&#92;n";
		codigo+="correo:"+correo+"&#92;n";
		return codigo;
	}


	
	
	
	
}
