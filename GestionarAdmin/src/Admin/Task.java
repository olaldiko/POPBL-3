package Admin;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class Task extends SwingWorker<Void, Integer> {
	SQL baseDatos;
	
	JFrame ventana;
	int tamX;
	int tamY;
	String seleccionado;
	String [][] nuevosDatos;
	ArrayList<String> nombreColumnas;
	JProgressBar progressBar;
	JTextArea tArea;
	Task task;
	
	double progress = 0;
	
	public Task(String [][] nuevosDatos, ArrayList<String> nombreColumnas, String nombreT, int tamX, int tamY, JFrame ventana, SQL baseDatos, JProgressBar progressBar, JTextArea tArea, Task task){
		this.ventana = ventana;
		this.tamX = tamX;
		this.tamY = tamY;
		this.seleccionado = nombreT;
		this.nombreColumnas = nombreColumnas;
		this.nuevosDatos = nuevosDatos;
		this.baseDatos = baseDatos;
		this.progressBar = progressBar;
		this.tArea = tArea;
		this.task = task;
	}
	
    @Override
    public Void doInBackground() {
    	setProgress((int) progress);
    	for (int i = 1; i != tamX; i++) {
    		baseDatos.actualizarDatos(nuevosDatos, nombreColumnas, seleccionado, tamX, tamY, ventana, i);
    		progress = (((double) i) / ((double) tamX) * 100);
    		if ((i + 1) == tamX) {
    			setProgress(100);
    			publish(100);
    		} else {
    			setProgress((int) progress);
    			publish((int) progress);
    		}
    		
    	}
    	return null;
    }
    
    public void process(List<Integer> valores){
    	for (Integer valor: valores){
	        progressBar.setValue(valor);
	        tArea.append(String.format("Completado %d%% de la tarea.\n", (int) progress)); 
 	   }
    }

    @Override
    public void done() {
    	Toolkit.getDefaultToolkit().beep();
    	ventana.setCursor(null);
    	tArea.append("Listo!\n");
    }
}
