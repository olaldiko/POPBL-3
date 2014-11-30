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

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class SQL {
	Scanner teclado;
	Connection con;
	boolean exit = false;
	
	public SQL(String URL, int numeroPuerto, String nombreBD, String user, String pass) {
		try {
        	System.out.print("Cargando el driver de la base de datos SQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con la base de datos SQL... ");
            String generalURL = "jdbc:mysql://"+URL+":"+numeroPuerto+"/"+nombreBD;
            con = DriverManager.getConnection(generalURL, user, pass);
            System.out.println(" OK!");
            System.out.println("");
        } catch (SQLException e) {
            System.out.println("ERROR: Imposible conectar con la base de datos.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.out.println("ERROR: Imposible cargar el driver.");
        	e.printStackTrace();
        } catch (Exception e) {
            System.out.println("FATAL ERROR.");
            e.printStackTrace();
        }		
    }
	
	public void SQLback() {
		while (!exit) {
			try{
				teclado = new Scanner(System.in);
				menu();
			} catch (NullPointerException e) {
				System.out.println("ERROR: Imposible cargar Base de Datos, ¿Has probado a cargar la Base de Datos antes?");
			} catch (InputMismatchException e) {
				System.out.println("ERROR: Dato de entrada no soportado en este campo.");
			} catch (Exception e) {
	            System.out.println("ERROR: Imposible ejecutar petición.");
	            e.printStackTrace();
			} finally {
				try {
					if (con != null) con.close();
				} catch (SQLException e) {
					System.out.println("ERROR: Imposible cerrar la Base de Datos.");
				}
			}
		}		
    }
	
	public void cargarBD(String URL, int numeroPuerto, String nombreBD, String user, String pass){
		try {
        	System.out.print("Cargando el driver de la base de datos SQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con la base de datos SQL... ");
            String generalURL = "jdbc:mysql://"+URL+":"+numeroPuerto+"/"+nombreBD;
            con = DriverManager.getConnection(generalURL, user, pass);
            System.out.println(" OK!");
            System.out.println("");
        } catch (SQLException e) {
            System.out.println("ERROR: Imposible conectar con la base de datos.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.out.println("ERROR: Imposible cargar el driver.");
        	e.printStackTrace();
        } catch (Exception e) {
            System.out.println("FATAL ERROR.");
            e.printStackTrace();
        }
	}
	
	public void cargarTablas(){
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
			}
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
			e.printStackTrace();
		} finally {
			try {
				if (rsTables != null) rsTables.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible cargar tablas desde la Base de Datos.");
			}
		}
	}
	
	public void verDatos(String nombreT){
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
			}
			System.out.println(columnHeading);
			while (rsPersonalized.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print("\t"+rsPersonalized.getString(i));
				}
				System.out.println("\n");
			}
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		} catch (MySQLSyntaxErrorException e) {
			System.out.println("ERROR: La tabla no existe.");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Imposible crear un Statement.");
			}
			try {
				if (rsPersonalized != null) rsPersonalized.close();
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
	
	public void borrarDatos(String nombreT){
		Statement stmt = null;
		ResultSet rsColumns = null;
		try {
			verDatos(nombreT);
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

	public void menu(){
		String nombreT;
		int opcion;
		try{
			do {
				System.out.println("");
				System.out.println("SQL");
				System.out.println("	1-. Cargar Base de Datos MySQL.");
				System.out.println("	2-. Leer de tabla desde BD.");
				System.out.println("	3-. Leer datos desde tabla.");
				System.out.println("	4-. Eliminar datos desde tabla.");
				System.out.println("	5-. Insertar datos desde tabla.");
				System.out.println("	6-. Editar datos desde tabla.");
				System.out.println("	0-. EXIT.");
				System.out.println("");
				System.out.print("Selecciona una opción: ");
				opcion = teclado.nextInt(); teclado.nextLine();
				System.out.println("");
				switch(opcion){
					case 1: cargarBD("olaldiko.mooo.com", 23306, "mordorbet", "urko", "123ABCabc");
						break;
					case 2: cargarTablas();
						break;
					case 3: System.out.print("	Inserta el nombre de la tabla: ");
						nombreT = teclado.nextLine();
						System.out.println("");
						verDatos(nombreT);
						break;
					case 4: System.out.print("	Inserta el nombre de la tabla: ");
						nombreT = teclado.nextLine();
						System.out.println("");
						verDatos(nombreT);
						System.out.println("");
						borrarDatos(nombreT);
						break;
					case 5: System.out.print("	Inserta el nombre de la tabla: ");
						nombreT = teclado.nextLine();
						System.out.println("");
						verDatos(nombreT);
						System.out.println("");
						insertarDatos(nombreT);
						break;
					case 6: System.out.print("	Inserta el nombre de la tabla: ");
						nombreT = teclado.nextLine();
						System.out.println("");
						verDatos(nombreT);
						System.out.println("");
						editarDatos(nombreT);
						break;
					case 0: exit = true;
						break;
					default: System.out.println("ERROR: Opción no válida.");
				}
			} while (!exit);
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println("ERROR: Imposible cerrar la Base de Datos.");
					e.printStackTrace();
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("ERROR: Dato de entrada no soportado en este campo.");
		}
	}

	public static void main(String args[]){
		//SQL nuevaBD = new SQL();
	}
}