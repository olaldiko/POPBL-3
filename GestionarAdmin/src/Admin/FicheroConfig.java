package Admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FicheroConfig implements IntFicheroConfig{	

	String contFich;
	String delimiter = "-";
	String[] temp;
	File archivo = new File ("config.ini");
	
	
	@Override
	public void cargarDatos(int tamx, int tamy, String URL, int nPort, String nBD,
			String user, String pass){
		try {
	      FileReader fr;
	      fr = new FileReader (archivo);
	      BufferedReader br = new BufferedReader(fr);
	      
	      contFich = br.readLine();
	      temp = contFich.split(delimiter);
	      
	      br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("FETAL ERROR");
			e.printStackTrace();
		}
		URL = temp[0];
		user = temp[1];
		pass = temp[2];
		nBD	= temp[3];
		nPort = Integer.valueOf(temp[4]);
		tamy = Integer.valueOf(temp[5]);
		tamx = Integer.valueOf(temp[6]);
		
	}
	@Override
	public void guardarDatos(int tamx, int tamy, String URL, int nPort,
			String nBD, String user, String pass) {
		try{
        	FileWriter fileOutput = new FileWriter (archivo);
	        PrintWriter pw = new PrintWriter(fileOutput);
	        pw.print(URL + "," + user + "," + pass + "," + nBD + "," + nPort + "," + tamy + "," + tamx);
	        pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
