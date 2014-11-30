package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
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
            System.out.print("Conectando con Ba base de Datos MySQL... ");
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
	
	public void actualizarDatos(String [][] nuevosDatos, ArrayList<String> nombreColumnas, String nombreT, int tamX, int tamY) {
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
						exeUpdate += nombreColumnas.get(j)+"="+nuevosDatos[i][j]+" ";
					} else if (nuevosDatos[i][j].equals("true") || nuevosDatos[i][j].equals("false")) {
						exeUpdate += nombreColumnas.get(j)+"="+nuevosDatos[i][j]+" ";
					} else if (nuevosDatos[i][j].matches("^\\d{4}-\\d{2}-\\d{2}.*$")) {
						int año = Integer.parseInt(nuevosDatos[i][j].substring(0,  4));
						int mes = Integer.parseInt(nuevosDatos[i][j].substring(5,  7));
						int dia = Integer.parseInt(nuevosDatos[i][j].substring(8,  10));
						int horas = Integer.parseInt(nuevosDatos[i][j].substring(11,  13));
						int minutos = Integer.parseInt(nuevosDatos[i][j].substring(14,  16));
						double segundos = Double.parseDouble(nuevosDatos[i][j].substring(17));
						String nuevaFecha = año+"-"+mes+"-"+dia+"T"+horas+":"+minutos+":"+segundos+"Z";
						exeUpdate += nombreColumnas.get(j)+"=STR_TO_DATE('"+nuevaFecha+"','%Y-%m-%dT%H:%i:%sZ')";
					} else {
						exeUpdate += nombreColumnas.get(j)+"='"+nuevosDatos[i][j]+"' ";
					}
				}
				exeUpdate += "WHERE "+nombreColumnas.get(0)+"="+nuevosDatos[i][0];
				System.out.print("Escribiendo nuevo dato en la Base de Datos... ");
				stmt.executeUpdate(exeUpdate); 
				System.out.println("OK!");
				System.out.println("");
			}
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un Statement.");
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un ResultSet.");
			}
		}
	}
	
	public void insertarDatos(String nombreT){
		ArrayList<Object> datos = new ArrayList<>();
		ResultSet rsColumns = null;
		Statement stmt = null;
		try {
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			while (rsColumns.next()) {
				System.out.print("	"+(i++)+"-. "+rsColumns.getString("COLUMN_NAME")+" ("+rsColumns.getString("TYPE_NAME")+" -> "+rsColumns.getInt("COLUMN_SIZE")+")");
				if (rsColumns.getString("TYPE_NAME").equals("INT")){
					System.out.print(" (Integer) -> ");
					int newDato = teclado.nextInt(); teclado.nextLine();
					datos.add(newDato);
				} else if (rsColumns.getString("TYPE_NAME").equals("VARCHAR")){
					System.out.print(" (String) -> ");
					String newDato = teclado.nextLine();
					datos.add(newDato);
				} else if (rsColumns.getString("TYPE_NAME").equals("DOUBLE")){
					System.out.print(" (Double) -> ");
					double newDato = teclado.nextDouble(); teclado.nextLine();
					datos.add(newDato);
				} else if (rsColumns.getString("TYPE_NAME").equals("DATETIME")){
					System.out.println(" (Tiempo) -> ");
					System.out.print("		Año: ");
					int año = teclado.nextInt();
					System.out.print("		Mes: ");
					int mes = teclado.nextInt();
					System.out.print("		Día: ");
					int dia = teclado.nextInt();
					System.out.print("		Hora: ");
					int hora = teclado.nextInt();
					System.out.print("		Minutos: ");
					int minutos = teclado.nextInt();
					System.out.print("		Segundos: ");
					int segundos = teclado.nextInt();
					Tiempo nuevoD = new Tiempo(año, mes, dia, hora, minutos, segundos);
					datos.add(nuevoD);
				} else if (rsColumns.getString("TYPE_NAME").equals("BIT")){
					boolean datoInsertado = false;
					do{
						datoInsertado = false;
						System.out.print(" (true/false) -> ");
						String temp = teclado.nextLine();
						boolean newDato = true;
						if (temp.toLowerCase().equals("true")){
							newDato = true;
							datoInsertado = true;
							datos.add(newDato);
						}
						else if (temp.toLowerCase().equals("false")){
							newDato = false;
							datoInsertado = true;
							datos.add(newDato);
						}
						else System.out.println("ERROR: Dato no valido.");
					} while (!datoInsertado);
				} else {
					System.out.println("ERROR: Tipo de dato no soportado.");
					datos.add("");
				}
			}
			System.out.println("");
			stmt = con.createStatement();
			Iterator<Object> it = datos.iterator();
			String exeUpdate = "INSERT INTO "+nombreT+" VALUES (";
			int j = 1;
			while(it.hasNext()){
				Object valorActual = it.next();
				if ((valorActual instanceof Integer) || (valorActual instanceof Double) || (valorActual instanceof Boolean)){
					exeUpdate += valorActual;
				}
				if (valorActual instanceof String){
					exeUpdate += "'"+valorActual+"'";
				}
				if (valorActual instanceof Tiempo){
					exeUpdate += "STR_TO_DATE('"+valorActual.toString()+"','%Y-%m-%dT%H:%i:%sZ')";
				}
				if (j == (i - 1)) exeUpdate += ")";
				else exeUpdate += ", ";
				j++;
			}
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			stmt.executeUpdate(exeUpdate); 
			System.out.println("");
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un Statement.");
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un ResultSet.");
			}
		}
	}

	public void editarDatos(String nombreT){
		ArrayList<Object> datos = new ArrayList<>();
		ArrayList<String> colNames = new ArrayList<>();
		ResultSet rsColumns = null;
		Statement stmt = null;
		try {
			String priCol = null;
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			System.out.println("");
			System.out.print("Inserta el ID de la línea que queires modificar: ");
			int idModifi = teclado.nextInt(); teclado.nextLine();
			System.out.println("");
			boolean primero = true;
			while (rsColumns.next()) {
				if (!primero) {
					System.out.print("	"+(i++)+"-. "+rsColumns.getString("COLUMN_NAME")+" ("+rsColumns.getString("TYPE_NAME")+" -> "+rsColumns.getInt("COLUMN_SIZE")+")");
					if (rsColumns.getString("TYPE_NAME").equals("INT")){
						System.out.print(" (Integer) -> ");
						int newDato = teclado.nextInt(); teclado.nextLine();
						datos.add(newDato);
					} else if (rsColumns.getString("TYPE_NAME").equals("VARCHAR")){
						System.out.print(" (String) -> ");
						String newDato = teclado.nextLine();
						datos.add(newDato);
					} else if (rsColumns.getString("TYPE_NAME").equals("DOUBLE")){
						System.out.print(" (Double) -> ");
						double newDato = teclado.nextDouble(); teclado.nextLine();
						datos.add(newDato);
					} else if (rsColumns.getString("TYPE_NAME").equals("DATETIME")){
						System.out.println(" (Tiempo) -> ");
						System.out.print("		Año: ");
						int año = teclado.nextInt();
						System.out.print("		Mes: ");
						int mes = teclado.nextInt();
						System.out.print("		Día: ");
						int dia = teclado.nextInt();
						System.out.print("		Hora: ");
						int hora = teclado.nextInt();
						System.out.print("		Minutos: ");
						int minutos = teclado.nextInt();
						System.out.print("		Segundos: ");
						int segundos = teclado.nextInt();
						Tiempo nuevoD = new Tiempo(año, mes, dia, hora, minutos, segundos);
						datos.add(nuevoD);
					} else if (rsColumns.getString("TYPE_NAME").equals("BIT")){
						boolean datoInsertado = false;
						do{
							datoInsertado = false;
							System.out.print(" (true/false) -> ");
							String temp = teclado.nextLine();
							boolean newDato = true;
							if (temp.toLowerCase().equals("true")){
								newDato = true;
								datoInsertado = true;
								datos.add(newDato);
							}
							else if (temp.toLowerCase().equals("false")){
								newDato = false;
								datoInsertado = true;
								datos.add(newDato);
							}
							else System.out.println("ERROR: Dato no valido.");
						} while (!datoInsertado);
					} else {
						System.out.println("ERROR: Tipo de dato no soportado.");
						datos.add("");
					}
					colNames.add(rsColumns.getString("COLUMN_NAME"));
				}
				if (primero) priCol = rsColumns.getString("COLUMN_NAME"); primero = false;
			}
			System.out.println("");
			stmt = con.createStatement();
			Iterator<Object> it = datos.iterator();
			Iterator<String> itC = colNames.iterator();
			String exeUpdate = "UPDATE "+nombreT+" SET ";
			int j = 1;
			while(it.hasNext()){
				Object valorActual = it.next();
				String colActual = itC.next();
				if ((valorActual instanceof Integer) || (valorActual instanceof Double) || (valorActual instanceof Boolean)){
					exeUpdate += colActual+"="+valorActual;
				}
				if (valorActual instanceof String){
					exeUpdate += colActual+"='"+valorActual+"'";
				}
				if (valorActual instanceof Tiempo){
					exeUpdate += colActual+"=STR_TO_DATE('"+valorActual.toString()+"','%Y-%m-%dT%H:%i:%sZ')";
				}
				if (j != (i - 1)) exeUpdate += ", ";
				j++;
			}
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			exeUpdate += " WHERE "+priCol+"="+idModifi;
			stmt.executeUpdate(exeUpdate); 
			System.out.println("");
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un Statement.");
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un ResultSet.");
			}
		}
	}
	
	public void borrarDatos(String nombreT, JFrame ventana){
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
			System.out.print("Inserta el ID de la línea que quieres eliminar: ");
			int idModifi = teclado.nextInt(); teclado.nextLine();
			boolean primero = true;
			while (rsColumns.next()) {
				if (primero) priCol = rsColumns.getString("COLUMN_NAME"); primero = false;
			}
			System.out.println("");
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			stmt.executeUpdate("DELETE FROM "+nombreT+" WHERE "+priCol+"="+idModifi); 
			System.out.println("");
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un Statement.");
			}
			try {
				if (rsColumns != null) rsColumns.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un ResultSet.");
			}
		}
	}
}