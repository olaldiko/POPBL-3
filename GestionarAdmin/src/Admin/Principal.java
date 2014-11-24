package Admin;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Principal extends JFrame {
	/*
	 * v0.1
	 */
	
	private static final long serialVersionUID = 4301349494056044897L;
	
	JPanel panel;
	
	public Principal(){
		super("Gestión de la aplicación");
		this.setSize(500, 500);
		this.setLocation(100, 100);
		MiDialogo nuevoDialogo = new MiDialogo(this, "Identificación", true);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Container crearPanelVentana(){
		panel = new JPanel(new BorderLayout());
		return panel;
	}
	
	@SuppressWarnings("unused")
	public static void main (String args[]){
		Principal ini = new Principal();
	}
}