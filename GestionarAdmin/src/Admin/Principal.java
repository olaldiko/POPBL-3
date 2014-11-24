package Admin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Principal implements ActionListener{
	/*
	 * v0.1 - 24/11/2014
	 */
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 4301349494056044897L;
	
	JFrame ventana;
	JPanel panel;
	
	JMenuBar mBar;
	JMenu archivo;
	JMenuItem nEquipo;
	JMenuItem nPartido;
	JMenu editar;
	JMenuItem eEquipo;
	JMenuItem ePartido;
	JMenuItem eCuotas;
	
	public Principal(){
		ventana = new JFrame("Gestión de la aplicación");
		Identificacion nuevoDialogo = new Identificacion(ventana, "Identificación", true);
		if (nuevoDialogo.respuesta){
			ventana.setSize(500, 500);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			ventana.setLocation(dim.width/2-ventana.getSize().width/2, dim.height/2-ventana.getSize().height/2);
			ventana.setJMenuBar(crearMenuBar());
			ventana.setContentPane(crearPanelVentana());
			//ventana.getContentPane().add(crearToolBar(),BorderLayout.NORTH);
			ventana.setVisible(true);
			//ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public JMenuBar crearMenuBar(){
		mBar = new JMenuBar();
		mBar.add(crearMenuArchivo());
		mBar.add(crearMenuEditar());
		return mBar;
	}
	
	public JMenu crearMenuArchivo(){
		archivo = new JMenu("Archivo");
		nEquipo = archivo.add("Nuevo equipo...");
		nPartido = archivo.add("Nuevo partido...");
		nEquipo.setIcon(new ImageIcon("data/images/menu/nuevoEquipo.png"));
		nPartido.setIcon(new ImageIcon("data/images/menu/nuevoPartido.png"));
		nEquipo.setActionCommand("nuevoEquipo");
		nPartido.setActionCommand("nuevoPartido");
		nEquipo.addActionListener(this);
		nPartido.addActionListener(this);
		return archivo;
	}	
	
	public JMenu crearMenuEditar(){
		editar = new JMenu("Editar");
		eEquipo = editar.add("Editar equipo...");
		ePartido = editar.add("Editar partido...");
		eEquipo.setIcon(new ImageIcon("data/images/menu/editarEquipo.png"));
		ePartido.setIcon(new ImageIcon("data/images/menu/editarPartido.png"));
		eEquipo.setActionCommand("editarEquipo");
		ePartido.setActionCommand("editarPartido");
		eEquipo.addActionListener(this);
		ePartido.addActionListener(this);
		return editar;
	}
	
	public Container crearPanelVentana(){
		panel = new JPanel(new BorderLayout());
		return panel;
	}
	
	@SuppressWarnings("unused")
	public static void main (String args[]){
		Principal ini = new Principal();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}