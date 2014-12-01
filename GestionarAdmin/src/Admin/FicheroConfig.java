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
    FileReader fr;
	BufferedReader br;
	FileWriter fileOutput;
	PrintWriter pw;
    
	@Override
	public void cargarDatos(int tamx, int tamy, String URL, int nPort, String nBD,
			String user, String pass){
		
		try {
	      fr = new FileReader (archivo);
	      br = new BufferedReader(fr);
	      
	      contFich = br.readLine();
	      temp = contFich.split(delimiter);
	      	      
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("FETAL ERROR");
			e.printStackTrace();
		} finally{
			URL = temp[0];
			user = temp[1];
			pass = temp[2];
			nBD	= temp[3];
			nPort = Integer.valueOf(temp[4]);
			tamy = Integer.valueOf(temp[5]);
			tamx = Integer.valueOf(temp[6]);			
		    try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void guardarDatos(int tamx, int tamy, String URL, int nPort,
			String nBD, String user, String pass) {
		try{
        	fileOutput = new FileWriter (archivo);
	        pw = new PrintWriter(fileOutput);
	        pw.print(URL + "," + user + "," + pass + "," + nBD + "," + nPort + "," + tamy + "," + tamx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			 pw.close();
		}
	}
}
