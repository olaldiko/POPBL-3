package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class SQL {
	Scanner teclado;
	Connection con;
	
	public SQL(String URL, int numeroPuerto, String nombreBD, String user, String pass, JFrame ventana) {
		try {
        	System.out.print("Cargando el driver de la Base de Datos MySQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con Base de Datos MySQL... ");
            String generalURL = "jdbc:mysql://"+URL+":"+numeroPuerto+"/"+nombreBD;
            con = DriverManager.getConnection(generalURL, user, pass);
            System.out.println(" OK!");
            System.out.println("");
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
        	JOptionPane.showMessageDialog(ventana, "Imposible cargar el driver.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(ventana, "FATAL ERROR.", "Error", JOptionPane.ERROR_MESSAGE);
        }		
    }
	
	public Tablas cargarTablas(JFrame ventana){
		Tablas nuevasTablas = null;
		ArrayList<String> listaTablas = new ArrayList<>();
		ResultSet rsTables = null;
		try {
			System.out.print("Cargando tablas de la Base de Datos MySQL... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsTables = dbmd.getTables(null, null, "%", null);
			System.out.println("OK!");
			int i = 1;
			System.out.println("Estas son las tablas disponibles en la Base de Datos:");
			while (rsTables.next()) {
				System.out.println("	"+(i++)+"-. "+rsTables.getString(3));
				listaTablas.add(rsTables.getString(3));
			}
			System.out.println("");
			nuevasTablas = new Tablas(listaTablas, null);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rsTables != null) rsTables.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible cargar tablas desde la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return nuevasTablas;
	}
	
	public Datos verDatos(String nombreT, JFrame ventana){
		Datos nuevosDatos = null;
		ArrayList<String> datos = new ArrayList<>();
		ArrayList<String> nombreColumnas = new ArrayList<>();
		Statement stmt = null;
		ResultSet rsPersonalized = null;
		try {
			String columnHeading = "";
			System.out.print("Cargando la tabla establecida... ");
			stmt = con.createStatement();
			rsPersonalized = stmt.executeQuery("SELECT * FROM "+nombreT);	
			ResultSetMetaData rsmd = rsPersonalized.getMetaData();
			System.out.println("OK!");
			System.out.println("");
			System.out.println("Estos son los datos de la tabla "+nombreT+":");
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columnHeading = columnHeading+"\t"+rsmd.getColumnName(i);
				nombreColumnas.add(rsmd.getColumnName(i));
			}
			System.out.println(columnHeading);
			while (rsPersonalized.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print("\t"+rsPersonalized.getString(i));
					datos.add(rsPersonalized.getString(i));
				}
				System.out.println("\n");
			}
			nuevosDatos = new Datos(nombreColumnas, datos);
		} catch (InputMismatchException e) {
			JOptionPane.showMessageDialog(ventana, "Dato de entrada no soportado en este campo.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (MySQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(ventana, "La tabla no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un Statement.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				if (rsPersonalized != null) rsPersonalized.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un ResultSet.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return nuevosDatos;
	}
	
	public void actualizarDatos(String [][] nuevosDatos, ArrayList<String> nombreColumnas, String nombreT, int tamX, int tamY, JFrame ventana) {
		ResultSet rsColumns = null;
		Statement stmt = null;
		try {
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			stmt = con.createStatement();
			for (int i = 0; i != tamX; i++){
				String exeUpdate = "UPDATE "+nombreT+" SET ";
				for (int j = 1; j != tamY; j++){
					if (nuevosDatos[i][j].matches("[0-9]+")) {
						exeUpdate += nombreColumnas.get(j)+"="+nuevosDatos[i][j];
					} else if (nuevosDatos[i][j].equals("true") || nuevosDatos[i][j].equals("false")) {
						exeUpdate += nombreColumnas.get(j)+"="+nuevosDatos[i][j];
					} else if (nuevosDatos[i][j].matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
						int ano = Integer.parseInt(nuevosDatos[i][j].substring(0,  4));
						int mes = Integer.parseInt(nuevosDatos[i][j].substring(5,  7));
						int dia = Integer.parseInt(nuevosDatos[i][j].substring(8,  10));
						int horas = Integer.parseInt(nuevosDatos[i][j].substring(11,  13));
						int minutos = Integer.parseInt(nuevosDatos[i][j].substring(14,  16));
						int segundos = Integer.parseInt(nuevosDatos[i][j].substring(17, 19));
						String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
						exeUpdate += nombreColumnas.get(j)+"=STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
					} else {
						nuevosDatos[i][j].replaceAll("'", "''");
						exeUpdate += nombreColumnas.get(j)+"='"+nuevosDatos[i][j]+"'";
					}
					if ((j + 1) == tamY) exeUpdate += " ";
					else exeUpdate += ", ";
				}
				exeUpdate += "WHERE "+nombreColumnas.get(0)+"="+nuevosDatos[i][0];
				System.out.print("Escribiendo nuevo dato en la Base de Datos... ");
				stmt.executeUpdate(exeUpdate); 
				System.out.println("OK!");
				System.out.println("");
			}
		} catch (InputMismatchException e) {
			JOptionPane.showMessageDialog(ventana, "Dato de entrada no soportado en este campo.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(ventana, "Datos repetidos en la Base de Datos o Dato relacionado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un Statement.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un ResultSet.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void insertarDato(ArrayList<String> listaDatos, Object [] colNames, String nombreT, int tamY, JFrame ventana){
		ResultSet rsColumns = null;
		Statement stmt = null;
		try {
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			stmt = con.createStatement();
			String exeUpdate = "INSERT INTO "+nombreT+" VALUES (";
			for (int j = 0; j != tamY; j++){
				if (listaDatos.get(j).matches("[0-9]+")) {
					exeUpdate += listaDatos.get(j);
				} else if (listaDatos.get(j).equals("true") || listaDatos.get(j).equals("false")) {
					exeUpdate += listaDatos.get(j);
				} else if (listaDatos.get(j).matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
					int ano = Integer.parseInt(listaDatos.get(j).substring(0,  4));
					int mes = Integer.parseInt(listaDatos.get(j).substring(5,  7));
					int dia = Integer.parseInt(listaDatos.get(j).substring(8,  10));
					int horas = Integer.parseInt(listaDatos.get(j).substring(11,  13));
					int minutos = Integer.parseInt(listaDatos.get(j).substring(14,  16));
					int segundos = Integer.parseInt(listaDatos.get(j).substring(17, 19));
					String nuevaFecha = ano+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
					exeUpdate += "STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
				} else {
					listaDatos.get(j).replaceAll("'", "''");
					exeUpdate += "'"+listaDatos.get(j)+"'";
				}
				if ((j + 1) == tamY) exeUpdate += ")";
				else exeUpdate += ", ";
			}
			System.out.println(exeUpdate);
			System.out.print("Escribiendo nuevo dato en la Base de Datos... ");
			stmt.executeUpdate(exeUpdate); 
			System.out.println("OK!");
			System.out.println(exeUpdate);
		} catch (InputMismatchException e) {
			JOptionPane.showMessageDialog(ventana, "Dato de entrada no soportado en este campo.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(ventana, "Datos repetidos en la Base de Datos o Dato relacionado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un Statement.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un ResultSet.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void borrarDato(String nombreT, String id, JFrame ventana){
		Statement stmt = null;
		ResultSet rsColumns = null;
		try {
			verDatos(nombreT, ventana);
			String priCol = null;
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			stmt = con.createStatement();
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			System.out.println("");
			boolean primero = true;
			while (rsColumns.next()) {
				if (primero) priCol = rsColumns.getString("COLUMN_NAME"); primero = false;
			}
			System.out.println("");
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			stmt.executeUpdate("DELETE FROM "+nombreT+" WHERE "+priCol+"="+id); 
			System.out.println("");
		} catch (InputMismatchException e) {
			JOptionPane.showMessageDialog(ventana, "Dato de entrada no soportado en este campo.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(ventana, "Datos repetidos en la Base de Datos o Dato relacionado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con la Base de Datos.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un Statement.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ventana, "Imposible crear un ResultSet.", "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
	}
}