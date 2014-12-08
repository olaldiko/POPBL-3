package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Principal implements ActionListener, ItemListener{
	SQL baseDatos;
	Datos nuevosDatos;
	
	JFrame ventana;
	JPanel panelPrincipal, panelN, panelC, panelS, panelPB, panelCB;
	JMenuBar menu;
	JMenu mArch, mEdit, mSalir;
	JMenuItem guardarI, descartarI, recargarI, añadirI, borrarI, opcionesI, salirI;
	JTable tabla;
	JButton guardar, descartar, recargar;
	JComboBox<String> cTablas;
	JProgressBar progressBar;
	JTextArea tArea;
	
	Object [] colNames;
	String seleccionado;
	String ultimoID;
	
	Task guardarDatos;
	
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
	
	private JMenu crearMenuArch(){
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
	
	private JMenu crearMenuEdit(){
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
	
	private JMenu crearMenuSalir(){
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
		panelPrincipal.add(crearPanelSur(), BorderLayout.SOUTH);
		return panelPrincipal;
	}
	
	private Container crearPanelNorte(){
		panelN = new JPanel(new BorderLayout());
		panelN.add(crearCBTablas(),BorderLayout.WEST);
		panelN.add(crearPanelProgressBar(), BorderLayout.CENTER);
		return panelN;
	}
	
	private Container crearPanelCentro(){
		panelC = new JPanel(new BorderLayout());
		panelC.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Datos de la tabla seleccionada")));
		panelC.add(new JScrollPane(crearJTable()), BorderLayout.CENTER);
		return panelC;
	}
	
	private Container crearPanelSur(){
		panelS = new JPanel(new BorderLayout());
		panelS.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Información")));
		panelS.add(crearTextArea(), BorderLayout.CENTER);
		return panelS;
	}
	
	private Container crearPanelProgressBar(){
		panelPB = new JPanel(new BorderLayout());
		panelPB.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Progreso")));
		progressBar = new JProgressBar(SwingConstants.HORIZONTAL,0, 100);
	    progressBar.setValue(0);
	    progressBar.setStringPainted(true);
	    panelPB.add(progressBar, BorderLayout.CENTER);
	    return panelPB;
	}
	
	private Container crearCBTablas(){
		panelCB = new JPanel(new BorderLayout());
		panelCB.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createTitledBorder("Tablas")));
		Tablas nuevasTablas = baseDatos.cargarTablas(ventana);
		cTablas = new JComboBox<String>(nuevasTablas.lista.toArray(new String[nuevasTablas.lista.size()]));
		if (seleccionado != null) {
			cTablas.setSelectedItem(seleccionado);
		}
		cTablas.addItemListener(this);
		panelCB.add(cTablas, BorderLayout.CENTER);
		return panelCB;
	}
	
	private Container crearTextArea(){
		tArea = new JTextArea(5, 20);
		tArea.setMargin(new Insets(5, 5, 5, 5));
		tArea.setEditable(false);
	    JScrollPane panelScroll = new JScrollPane(tArea);
		return panelScroll;
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
				tabla.setModel(tableModel);
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabla.getModel()) {
		            @Override
		            public void toggleSortOrder(int column) {
		                if (column >= 0 && column < getModelWrapper().getColumnCount() && isSortable(column)) {
		                    List<SortKey> keys = new ArrayList<SortKey>(getSortKeys());
		                    if (!keys.isEmpty()) {
		                        SortKey sortKey = keys.get(0);
		                        if (sortKey.getColumn() == column && sortKey.getSortOrder() == SortOrder.DESCENDING) {
		                            setSortKeys(null);
		                            return;
		                        }
		                    }
		                }
		                super.toggleSortOrder(column);
		            }
		        };
		        tabla.setRowSorter(sorter);
				tamX = (nuevosDatos.datos.size() / nuevosDatos.nombreColumnas.size());
				tamY = nuevosDatos.nombreColumnas.size();
			} else {
				JOptionPane.showMessageDialog(ventana, "La tabla está vacía", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
	}
	
	private void guardarModifi(){
		if (tabla != null){
			String datosMatrix [][] = new String [tamX][tamY];
			for (int i = 0; i != tamX; i++){
				for (int j = 0; j != tamY; j++){
					datosMatrix[i][j] = (String) tabla.getModel().getValueAt(i, j);
				}
			}
			ventana.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			guardarDatos = new Task(datosMatrix, nuevosDatos.nombreColumnas, seleccionado, tamX, tamY, ventana, baseDatos, progressBar, tArea, guardarDatos);
			guardarDatos.execute();
		} else {
			JOptionPane.showMessageDialog(ventana, "No has cargado ninguna tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
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