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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	JMenuBar menu;
	JMenu mArch, mEdit, mSalir;
	JMenuItem guardarI, descartarI, recargarI, añadirI, borrarI, opcionesI, salirI;
	JTable tabla;
	JButton guardar, descartar, recargar;
	JComboBox<String> cTablas;
	
	Object [] colNames;
	String seleccionado;
	String ultimoID;
	
	DefaultTableModel tableModel;

	int tamX = 0, tamY = 0;
	
	public Principal(int tamX, int tamY, String URL, int numeroPuerto, String nombreBD, String user, String pass){
		ventana = new JFrame("Gestión de la aplicación");
		Identificacion nuevoDialogo = new Identificacion(ventana, "Identificación", true);
		if (nuevoDialogo.respuesta){
			baseDatos = new SQL(URL, numeroPuerto, nombreBD, user, pass, ventana);
			ventana.setSize(tamX, tamY);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			ventana.setLocation(dim.width/2-ventana.getSize().width/2, dim.height/2-ventana.getSize().height/2);
			ventana.setJMenuBar(crearMenuBar());
			ventana.setContentPane(crearPanelVentana());
			ventana.setVisible(true);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	private JMenuBar crearMenuBar(){
		menu = new JMenuBar();
		menu.add(crearMenuArch());
		menu.add(crearMenuEdit());
		menu.add(Box.createHorizontalGlue());
		menu.add(crearMenuSalir());
		return menu;
	}
	
	public JMenu crearMenuArch(){
		mArch = new JMenu("Archivo");
		guardarI = mArch.add("Guardar cambios");
		descartarI = mArch.add("Deshacer cambios");
		recargarI = mArch.add("Recargar tabla");
		guardarI.addActionListener(this);
		descartarI.addActionListener(this);
		recargarI.addActionListener(this);
		guardarI.setActionCommand("mGuardar");
		descartarI.setActionCommand("mDescartar");
		recargarI.setActionCommand("mRecargar");
		return mArch;
	}
	
	public JMenu crearMenuEdit(){
		mEdit = new JMenu("Editar");
		añadirI = mEdit.add("Añadir dato");
		borrarI = mEdit.add("Borrar dato");
		opcionesI = mEdit.add("Opciones");
		añadirI.addActionListener(this);
		opcionesI.addActionListener(this);
		borrarI.addActionListener(this);
		añadirI.setActionCommand("mAñadir");
		opcionesI.setActionCommand("mOpciones");
		borrarI.setActionCommand("mBorrar");
		return mEdit;
	}
	
	public JMenu crearMenuSalir(){
		mSalir = new JMenu("Salir");
		salirI = mSalir.add("Salir");
		salirI.addActionListener(this);
		salirI.setActionCommand("mSalir");
		return mSalir;
	}
	
	private Container crearPanelVentana(){
		panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.add(crearPanelNorte(), BorderLayout.NORTH);
		panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
		return panelPrincipal;
	}
	
	private Container crearPanelNorte(){
		panelN = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
	
	private Component crearCBTablas(){
		Tablas nuevasTablas = baseDatos.cargarTablas(ventana);
		cTablas = new JComboBox<String>(nuevasTablas.lista.toArray(new String[nuevasTablas.lista.size()]));
		if (seleccionado != null) {
			cTablas.setSelectedItem(seleccionado);
		}
		cTablas.addItemListener(this);
		return cTablas;
	}
	
	@SuppressWarnings("serial")
	private Component crearJTable(){
		tabla = new JTable();
		if (seleccionado != null) {
			nuevosDatos = baseDatos.verDatos(seleccionado, ventana);
			if (nuevosDatos.datos.size() != 0) {
				String datosMatrix [][] = new String [(nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size())][nuevosDatos.nombreColumnas.size()];
				int num = 0;
				for (int i = 0; i != (nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size()); i++){
					for (int j = 0; j != nuevosDatos.nombreColumnas.size(); j++){
						if (((i + 1) == (nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size()) && (j == 0))){
							ultimoID = nuevosDatos.datos.get(num);
						}
						datosMatrix[i][j] = nuevosDatos.datos.get(num++);
					}
				}
				colNames = nuevosDatos.nombreColumnas.toArray(new String[nuevosDatos.nombreColumnas.size()]);
				tabla = new JTable();
				tableModel = new DefaultTableModel(datosMatrix, colNames) {
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
			baseDatos.actualizarDatos(datosMatrix, nuevosDatos.nombreColumnas, seleccionado, tamX, tamY, ventana);
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
		if ((e.getActionCommand().equals("mDescartar")) || (e.getActionCommand().equals("mRecargar"))) {
			repintarPanel();
		}
		if (e.getActionCommand().equals("mGuardar")) {
			guardarModifi();
		}
		if (e.getActionCommand().equals("mAñadir")) {
			NuevoDato añadirNuevoDato = new NuevoDato(ventana, "Añadir nuevo dato", true, tamX, tamY, colNames, seleccionado, ultimoID);
			if (añadirNuevoDato.respuesta) {
				baseDatos.insertarDato(añadirNuevoDato.nuevosDatos, colNames, seleccionado, tamY, ventana);
				repintarPanel();
			}
		}
		if (e.getActionCommand().equals("mBorrar")) {
			BorrarDato borrarDato = new BorrarDato(ventana, "Borrar dato", true, seleccionado);
			if (borrarDato.respuesta) {
				baseDatos.borrarDato(seleccionado, borrarDato.field.getText(), ventana);
				repintarPanel();
			}
		}
		if (e.getActionCommand().equals("mOpciones")) {
			// Ventana con todas las opciones que escribimos en un fichero.
		}
		if (e.getActionCommand().equals("mSalir")) {
			ventana.dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		repintarPanel();
	}
}