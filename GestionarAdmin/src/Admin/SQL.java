package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class SQL {
	Scanner teclado = new Scanner(System.in);
	Connection con;
	java.sql.DatabaseMetaData dbmd;
	Statement stmt;
	ResultSet rsColumns;
	ResultSet rsTables;
	
	public SQL(String nombreBD) {
        try {
        	System.out.print("Cargando el driver de la base de datos SQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con la base de datos SQL... ");
            String URL = "jdbc:mysql://olaldiko.mooo.com:23306/"+nombreBD;
            con = DriverManager.getConnection(URL, "urko", "123ABCabc");
            System.out.println(" OK!");
            // PRUEBAS
            cargarTablas();
            System.out.print("Nombre de la tabla de la cual queremos cargar los datos: ");
			String nombreT = teclado.nextLine();
			insertarDatos(nombreT);
			//System.out.print("Nombre de la tabla de la cual queremos editar los datos: ");
			//String nombreT1 = teclado.nextLine();
			verDatos(nombreT);
            // PRUEBAS
        } catch (SQLException ex) {
            System.out.println("ERROR: Imposible conectar con la base de datos.");
        } catch (ClassNotFoundException ex) {
        	System.out.println("ERROR: Imposible cargar el driver.");
        } catch (Exception ex) {
            System.out.println("FATAL ERROR.");
        } finally {
            try {
            	if (con != null) con.close();
            } catch (SQLException ex) {
            	System.out.println("ERROR: Imposible conectar con la base de datos.");
            }
        }
    }
	
	public void cargarTablas(){
		try {
			System.out.println("");
			System.out.print("Cargando tablas de la Base de Datos MySQL... ");
			dbmd = con.getMetaData();
			rsTables = dbmd.getTables(null, null, "%", null);
			System.out.println("OK!");
			int i = 1;
			System.out.println("Estas son las tablas disponibles en la Base de Datos:");
			while (rsTables.next()) {
				System.out.println("	"+(i++)+"-. "+rsTables.getString(3));
			}
			rsTables.close();
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	public void insertarDatos(String nombreT){
		try {
			ArrayList<Object> datos = new ArrayList<>();
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			while (rsColumns.next()) {
				boolean datoInsertado = false;
				System.out.print("	"+(i++)+"-. "+rsColumns.getString("COLUMN_NAME")+" ("+rsColumns.getString("TYPE_NAME")+" -> "+rsColumns.getInt("COLUMN_SIZE")+")");
				if (rsColumns.getString("TYPE_NAME").equals("INT")){
					System.out.print(" (Integer) -> ");
					int newDato = teclado.nextInt(); teclado.nextLine();
					datos.add(newDato);
					datoInsertado = true;
				} else if (rsColumns.getString("TYPE_NAME").equals("VARCHAR")){
					System.out.print(" (String) -> ");
					String newDato = teclado.nextLine();
					datos.add(newDato);
					datoInsertado = true;
				} else if (rsColumns.getString("TYPE_NAME").equals("DOUBLE")){
					System.out.print(" (Double) -> ");
					double newDato = teclado.nextDouble(); teclado.nextLine();
					datos.add(newDato);
					datoInsertado = true;
				} else if (rsColumns.getString("TYPE_NAME").equals("DATETIME")){
					System.out.print(" (Tiempo) -> ");
					System.out.print("Año: ");
					int año = teclado.nextInt();
					System.out.print(" Mes: ");
					int mes = teclado.nextInt();
					System.out.print(" Día: ");
					int dia = teclado.nextInt();
					Tiempo nuevoD = new Tiempo(año, mes, dia);
					datos.add(nuevoD);
					datoInsertado = true;
				} else if (rsColumns.getString("TYPE_NAME").equals("BIT")){
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
				} else System.out.println("ERROR: Tipo de dato no soportado.");
				if (!datoInsertado){
					String nulo = "";
					datos.add(nulo);
				}
			}
			rsColumns.close();
			System.out.println("");
			Statement st = con.createStatement();
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
					Tiempo insertD = (Tiempo) valorActual;
					exeUpdate += "TO_DATE('"+insertD.año+"-"+insertD.mes+"-"+insertD.dia+"', '%y-%m-%d')";
				}
				if (j == (i - 1)) exeUpdate += ")";
				else exeUpdate += ", ";
				j++;
			}
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			//System.out.println(exeUpdate);
			st.executeUpdate(exeUpdate); 
			System.out.println("");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		}catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
		}
	}

	public void editarDatos(String nombreT){
		try {
			verDatos(nombreT);
			ArrayList<Object> datos = new ArrayList<>();
			ArrayList<String> colNames = new ArrayList<>();
			String priCol = null;
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			dbmd = con.getMetaData();
			rsColumns = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			System.out.println("");
			System.out.print("Inserta el ID de la línea que queires modificar: ");
			int idModifi = teclado.nextInt(); teclado.nextLine();
			System.out.println("");
			boolean primero = true;
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
				} else if (rsColumns.getString("TYPE_NAME").equals("BIT")){
					System.out.print(" (true/false) -> ");
					String temp = teclado.nextLine();
					boolean newDato = false;
					if (temp.toLowerCase().equals("true")) newDato = true;
					if (temp.toLowerCase().equals("false")) newDato = false;
					datos.add(newDato);
				} else System.out.println("ERROR: Tipo de dato no soportado.");
				if (primero) priCol = rsColumns.getString("COLUMN_NAME"); primero = false;
				colNames.add(rsColumns.getString("COLUMN_NAME"));
			}
			rsColumns.close();
			System.out.println("");
			Statement st = con.createStatement();
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
				if (j != (i - 1)) exeUpdate += ", ";
				j++;
			}
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			exeUpdate += " WHERE "+priCol+"="+idModifi;
			st.executeUpdate(exeUpdate); 
			System.out.println("");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		}catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
		}
	}
	
	public void verDatos(String nombreT){
		try {
			String columnHeading = "";
			stmt = con.createStatement();
			ResultSet rsPersonalized = stmt.executeQuery("SELECT * FROM "+nombreT);
			if (rsPersonalized.next()) {
			   ResultSetMetaData rsmd = rsPersonalized.getMetaData();
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
			} else {
				System.out.println("ERROR: La tabla está vacía.");
			}
		} catch (MySQLSyntaxErrorException e) {
			System.out.println("ERROR: La tabla no existe.");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		} 
	}
	
	public static void main(String args[]){
		try{
			@SuppressWarnings("unused")
			SQL nuevaBD = new SQL("mordorbet");
		} catch (Exception ex) {
            System.out.println("ERROR: Imposible ejecutar petición.");
		}		
	}
}