package org.jocaqes.Misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;


/**
 * Clase para el manejo de guardado y lectura de informacion 
 * en carpetas ubicadas en la carpeta webapp
 * @author jocaqes
 *
 */
public class Archivo {
	
	/**
	 * Maneja el guadar codigo como un archivo extension txt para su uso 
	 * por graphviz
	 * @param nombre el nombre del archivo a guardar, la extension siempre es txt
	 * @param codigo el contenido a guardar en el archivo
	 */
	public static void guardarCodigo(String ruta, String nombre, String codigo){
        FileWriter archivo=null;
        PrintWriter pw = null;
        try{
            archivo = new FileWriter(ruta+"/"+nombre+".txt");
            pw = new PrintWriter(archivo);
            pw.println(codigo);
            
        }catch(Exception e){
            
        }finally{
            try{
                archivo.close();
            }catch(Exception e){
                
            }
        }
    }
	
    public static void generarGrafica(String ruta, String nombre, String codigo){
        String ruta_ = ruta+"/"+nombre+".txt";//nombre del archivo con el codigo de graphviz
        String ruta_imagen = ruta+"/"+nombre+".png";//nombre de la imagen
        //String codigo_salida = codigo;
        guardarCodigo(ruta,nombre,codigo);
        
        ProcessBuilder proc = new ProcessBuilder("dot","-Tpng",ruta_,"-o",ruta_imagen);//.start();
		proc.redirectErrorStream(true);
		try {
			@SuppressWarnings("unused")
			Process p=proc.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 
    
    public static String graficaBase64(String ruta, String nombre)
    {
    	File imagen=new File(ruta+"/"+nombre+".png");
    	String base64="";
    	boolean bandera=true;
    	if(imagen.exists())
    	{
    		try {
				FileInputStream in = new FileInputStream(imagen);
				byte[] bytes = new byte[(int)imagen.length()];
				in.read(bytes);
				base64=Base64.getEncoder().encodeToString(bytes);
				in.close();
			} catch (FileNotFoundException e) {
				bandera=false;
				e.printStackTrace();
			} catch (IOException e) {
				bandera=false;
				e.printStackTrace();
			}
    	}
    	if(bandera)
    		return base64;
    	return null;
    }
	

}
