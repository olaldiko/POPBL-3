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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NuevoDato implements ActionListener {
	JDialog dialog;
	JPanel panel, panelTitulo, panelDatos, panelBotones;
	JButton guardar, cancelar;
	JLabel tituloL, generalL;
	
	int tamX = 0;
	int tamY = 0;
	Object [] colNames;
	String seleccionado;
	String ultimoID;
	
	ArrayList<String> nuevosDatos;
	Map <String, JTextField> datos = new HashMap<>();
	
	boolean respuesta = false;
	
	public NuevoDato (JFrame ventana, String titulo, boolean modo, int tamX, int tamY, Object [] colNames, String seleccionado, String ultimoID) {
		this.tamX = tamX;
		this.tamY = tamY;
		this.colNames = colNames;
		this.seleccionado = seleccionado;
		this.ultimoID = ultimoID;
		dialog = new JDialog(ventana, titulo, modo);
		dialog.setSize(500, 300);
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
		String titulo = "Añadir dato a la tabla "+seleccionado;
		tituloL = new JLabel(titulo);
		tituloL.setFont(new Font(null, Font.BOLD, 25));
		tituloL.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitulo.add(tituloL);
		return panelTitulo;
	}
	
	public Container crearPanelDatos(){
		panelDatos = new JPanel(new GridLayout(tamY, 4, 0, 0));
		int id = Integer.parseInt(ultimoID);
		System.out.println(String.valueOf(id + 1));
		datos.put(((String) colNames[0]), new JTextField(String.valueOf(id + 1), 10));
		for (int i = 1; i != tamY; i++){
			datos.put(((String) colNames[i]), new JTextField("", 10));
			JPanel tempPanel = new JPanel();
			tempPanel.add(crearLabel(generalL, ((String) colNames[i])));
			tempPanel.add(datos.get(((String) colNames[i])));
			panelDatos.add(tempPanel);
		}
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
			nuevosDatos = new ArrayList<>();
			for (int i = 0; i != tamY; i++){
				nuevosDatos.add(datos.get(((String) colNames[i])).getText());
			}
			respuesta = true;
			dialog.dispose();
		} else if (e.getActionCommand().equals("cancelar")) {
			respuesta = false;
			dialog.dispose();
		}		
	}
}