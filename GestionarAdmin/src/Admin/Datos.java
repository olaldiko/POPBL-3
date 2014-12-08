package Admin;

import java.util.ArrayList;

public class Datos {
	ArrayList<String> nombreColumnas;
	ArrayList<String> datos;
	
	public Datos(ArrayList<String> nombreColumnas, ArrayList<String> datos){
		this.nombreColumnas = nombreColumnas;
		this.datos = datos;
	}
}