package org.jocaqes.Misc;

/**
 * Para calcular estadisticos, se asume que entregan arreglos ordenados
 * @author jocaqes
 *
 */
public class Estadisticos {

	/**
	 * Calcula la media de un arreglo de enteros
	 * @param valores el arreglo de los enteros a los que se les quiere calcular la medida
	 * @return un double que representa la media de los valores
	 */
	public static double media(int[] valores)
	{
		double suma=0d;
		for(Integer valor:valores)
		{
			suma+=valor;
		}
		return suma/valores.length;
	}
	/**
	 * Calcula la mediana de los valores pasados a la funcion
	 * @param valores los valores previamente ordenados
	 * @return la mediana de los valores
	 */
	public static int mediana(int[] valores)
	{
		return valores[valores.length/2];
	}
	/**
	 * Calcula la moda de los valores pasados a la funcion
	 * @param valores los valores previamente ordenados
	 * @return la moda de los valores
	 */
	public static int moda(int[] valores)
	{
		int moda=valores[0];
		int moda_actual=valores[0];
		int actual=0;
		int frecuencia_max=0;
		int frecuencia_actual=0;
		int length=valores.length;
		for(int i=0;i<length;i++)
		{
			actual=valores[i];
			if(actual==moda_actual)
				frecuencia_actual++;
			else
			{
				if(frecuencia_actual>frecuencia_max)
				{
					frecuencia_max=frecuencia_actual;
					moda=moda_actual;
					moda_actual=valores[i];
					frecuencia_actual=1;
				}
				else
				{
					moda_actual=valores[i];
					frecuencia_actual=1;
				}
			}
			
		}
		return moda;
	}
	/**
	 * Calcula la varianza de los valores pasados
	 * @param valores los valores a los que se les quiere calcular la varianza
	 * @param media la media de los valores 
	 * @return un double de la varianza de los valores
	 */
	public static double varianza(int[] valores, double media)
	{
		int n=valores.length;
		double suma=0;
		for(int i=0;i<n;i++)
		{
			suma+=Math.pow(valores[i]-media, 2);
		}
		return suma/n;
	}
	/**
	 * Calcula la desviasion estandar
	 * @param varianza valor que se le quiere sacar la desviasion estandar
	 * @return la raiz cuadrada de la varianza
	 */
	public static double desviasionEstandar(double varianza)
	{
		return Math.sqrt(varianza);
	}
	/**
	 * Funcion para ordenar un arreglo de enteros
	 * @param array el arreglo a ordenar
	 * @param izq el limite inferior izquierdo
	 * @param der el limite superior derecho
	 */
	public static void quickSort(int[] array, int izq, int der)
	{
       if(izq==der)
           return;
       int i=izq;
       int j=der;
       int pivote=array[(izq+der)/2];
       
       while(i<=j)
       {
           while(array[i]<pivote)
               i++;
           while(array[j]>pivote)
               j--;
           if(i<=j)
           {
               int en_i=array[i];
               int en_j=array[j];
               array[i]=en_j;
               array[j]=en_i;
               i++;
               j--;
           }
       }
       if(izq<i)
           quickSort(array, izq, j);
       if(i<der)
           quickSort(array, i, der);
   }
	
}
