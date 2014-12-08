package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BorrarDato implements ActionListener{
	JDialog dialog;
	JPanel panel, panelTitulo, panelDatos, panelBotones;
	JButton guardar, cancelar;
	JLabel tituloL, generalL;
	JTextField field;
	
	String id;
	String seleccionado;
	
	boolean respuesta = false;
	
	public BorrarDato (JFrame ventana, String titulo, boolean modo, String seleccionado) {
		this.seleccionado = seleccionado;
		dialog = new JDialog(ventana, titulo, modo);
		dialog.setSize(500, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setResizable(false);
		dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
		dialog.setContentPane(crearPanelDialogo());
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}

	public Container crearPanelDialogo(){
		panel = new JPanel(new BorderLayout());
		panel.add(crearPanelTitulo(), BorderLayout.NORTH);
		panel.add(crearPanelDatos(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Container crearPanelTitulo(){
		panelTitulo = new JPanel();
		String titulo = "Borrar dato de la tabla "+seleccionado;
		tituloL = new JLabel(titulo);
		tituloL.setFont(new Font(null, Font.BOLD, 25));
		tituloL.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(tituloL);
		return panelTitulo;
	}
	
	public Container crearPanelDatos(){
		panelDatos = new JPanel(new GridLayout(1, 2, 0, 0));
		JPanel tempPanel = new JPanel();
		tempPanel.add(crearLabel(generalL, "Introduce el ID del dato que quieres borrar"));
		field = new JTextField("", 10);
		tempPanel.add(field);
		panelDatos.add(tempPanel);
		return panelDatos;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(crearBoton(guardar, "Guardar", "guardar"));
		panel.add(crearBoton(cancelar, "Cancelar", "cancelar"));
		return panel;
	}
	
	public Component crearLabel(JLabel label, String texto){
		label = new JLabel(texto+": ");
		return label;		
	}
	
	private Component crearBoton(JButton button, String texto, String comand) {
		button = new JButton (texto);
		button.setActionCommand(comand);
		button.addActionListener(this);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("guardar")) {
			respuesta = true;
			dialog.dispose();
		} else if (e.getActionCommand().equals("cancelar")) {
			respuesta = false;
			dialog.dispose();
		}		
	}
}