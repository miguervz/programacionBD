package es.studium.programacionBDPRACTICA;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Metodos {
	String dir;

	static FileWriter archivo;
public static String convertirFecha(String fecha) {
	
	int posicion = fecha.indexOf("-");
	String dia = fecha.substring(0,posicion);
	String subcadena=fecha.substring(posicion+1);
	int posicion2 = subcadena.indexOf("-");
	String mes = subcadena.substring(0,posicion2);
	String año=subcadena.substring(posicion2+1);
	String fechaValida=(año+"-"+mes+"-"+dia);
	return fechaValida;
}
public static String convertirFechaInversa(String fecha) {

	int posicion = fecha.indexOf("-");
	String año = fecha.substring(0,posicion);
	String subcadena=fecha.substring(posicion+1);
	int posicion2 = subcadena.indexOf("-");
	String mes = subcadena.substring(0,posicion2);
	String dia=subcadena.substring(posicion2+1);
	String fechaValidaInversa=(dia+"-"+mes+"-"+año);
	return fechaValidaInversa;
}
public static void InfoLog(String Operacion) throws IOException{


	//Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
	if (new File("movimientos.log").exists()==false){archivo=new FileWriter(new File("movimientos.log"),false);}
	archivo = new FileWriter(new File("movimientos.log"), true);
	Calendar fechaActual = Calendar.getInstance(); //Para poder utilizar el paquete calendar
	//Empieza a escribir en el archivo
	archivo.write("["+(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
	+"-"+String.valueOf(fechaActual.get(Calendar.MONTH)+1)
	+"-"+String.valueOf(fechaActual.get(Calendar.YEAR))
	+" "+String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
	+":"+String.valueOf(fechaActual.get(Calendar.MINUTE))
	+":"+String.valueOf(fechaActual.get(Calendar.SECOND)))+"]"+ "["+Login.usuarioLog+"] ["+Operacion+"]\r\n");
	archivo.close(); }
}