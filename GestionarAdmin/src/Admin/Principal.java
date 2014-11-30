package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Principal implements ActionListener, ItemListener{
	SQL baseDatos;
	Datos nuevosDatos;
	
	JFrame ventana;
	JPanel panelPrincipal, panelN, panelC, panelS;
	JTable tabla;
	JButton guardar, descartar, recargar;
	JComboBox<String> cTablas;
	
	String seleccionado;
	int tamX = 0, tamY = 0;
	
	public Principal(int tamX, int tamY, String URL, int numeroPuerto, String nombreBD, String user, String pass){
		ventana = new JFrame("Gestión de la aplicación");
		Identificacion nuevoDialogo = new Identificacion(ventana, "Identificación", true);
		boolean pruebas = true;
		if (pruebas/*nuevoDialogo.respuesta*/){
			baseDatos = new SQL(URL, numeroPuerto, nombreBD, user, pass, ventana);
			ventana.setSize(tamX, tamY);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			ventana.setLocation(dim.width/2-ventana.getSize().width/2, dim.height/2-ventana.getSize().height/2);
			ventana.setContentPane(crearPanelVentana());
			ventana.setVisible(true);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	private Container crearPanelVentana(){
		panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(crearPanelNorte(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
		panelPrincipal.add(crearPanelSur(), BorderLayout.SOUTH);
		return panelPrincipal;
	}
	
	private Container crearPanelNorte(){
		panelN = new JPanel(new FlowLayout());
		panelN.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Información de tablas")));
		panelN.add(crearCBTablas());
		return panelN;
	}
	
	private Container crearPanelCentro(){
		panelC = new JPanel(new BorderLayout());
		panelC.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Datos de la tabla seleccionada")));
		panelC.add(new JScrollPane(crearJTable()), BorderLayout.CENTER);
		return panelC;
	}
	
	private Container crearPanelSur(){
		panelS = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelS.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Opciones")));
		panelS.add(crearBotones(recargar, "Recargar", "recargar"));
		panelS.add(crearBotones(descartar, "Descartar", "descartar"));
		panelS.add(crearBotones(guardar, "Guardar", "guardar"));
		return panelS;
	}
	
	private Component crearCBTablas(){
		Tablas nuevasTablas = baseDatos.cargarTablas(ventana);
		cTablas = new JComboBox<String>(nuevasTablas.lista.toArray(new String[nuevasTablas.lista.size()]));
		if (seleccionado != null) {
			cTablas.setSelectedItem(seleccionado);
		}
		cTablas.addItemListener(this);
		return cTablas;
	}
	
	private Component crearBotones(JButton boton, String nombre, String comand){
		boton = new JButton(nombre);
		boton.setActionCommand(comand);
		boton.addActionListener(this);
		return boton;
	}
	
	private Component crearJTable(){
		tabla = new JTable();
		if (seleccionado != null) {
			nuevosDatos = baseDatos.verDatos(seleccionado, ventana);
			if (nuevosDatos.datos.size() != 0) {
				String datosMatrix [][] = new String [(nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size())][nuevosDatos.nombreColumnas.size()];
				int num = 0;
				for (int i = 0; i != (nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size()); i++){
					for (int j = 0; j != nuevosDatos.nombreColumnas.size(); j++){
						datosMatrix[i][j] = nuevosDatos.datos.get(num++);
					}
				}
				tabla = new JTable();
				@SuppressWarnings("serial")
				DefaultTableModel tableModel = new DefaultTableModel(datosMatrix, nuevosDatos.nombreColumnas.toArray(new String[nuevosDatos.nombreColumnas.size()])) {
				    @Override
				    public boolean isCellEditable(int row, int column) {
				    	if (column == 0) return false;
				    	else return true;
				    }
				};
				tamX = (nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size());
				tamY = nuevosDatos.nombreColumnas.size();
				tabla.setModel(tableModel);
			} else {
				JOptionPane.showMessageDialog(ventana, "La tabla está vacía", "Advirtencia", JOptionPane.WARNING_MESSAGE);
			}
		}
		return tabla; 
	}
	
	private void repintarPanel(){
		seleccionado = (String) cTablas.getSelectedItem();
		panelPrincipal.removeAll();
		panelPrincipal.add(crearPanelNorte(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
		panelPrincipal.add(crearPanelSur(), BorderLayout.SOUTH);
		panelPrincipal.revalidate(); 
		panelPrincipal.repaint();
	}
	
	private void guardarModifi(){
		if (tabla != null){
			String datosMatrix [][] = new String [tamX][tamY];
			for (int i = 0; i != tamX; i++){
				for (int j = 0; j != tamY; j++){
					datosMatrix[i][j] = (String) tabla.getModel().getValueAt(i, j);
				}
			}
			baseDatos.actualizarDatos(datosMatrix, nuevosDatos.nombreColumnas, seleccionado, tamX, tamY);
		} else {
			JOptionPane.showMessageDialog(ventana, "No has cargado ninguna tabla", "Advirtencia", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@SuppressWarnings("unused")
	public static void main (String args[]){
		Principal ini = new Principal(700, 700, "olaldiko.mooo.com", 23306, "mordorbet", "urko", "123ABCabc");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e.getActionCommand().equals("descartar")) || (e.getActionCommand().equals("recargar"))) {
			repintarPanel();
		}
		if (e.getActionCommand().equals("guardar")) {
			guardarModifi();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		repintarPanel();
	}
}