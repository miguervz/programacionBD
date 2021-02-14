package es.studium.programacionBDPRACTICA;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
public class Ayuda {
	{
		//Accedemos al pdf que hemos guardado en nuestro proyecto
		try {
			File path = new File ("src/ManualUsuarioG.pdf");
			Desktop.getDesktop().open(path);
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}