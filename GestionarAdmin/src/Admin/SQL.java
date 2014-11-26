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

public class SQL {
	Scanner teclado = new Scanner(System.in);
	Connection con;
	
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
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, "%", null);
			System.out.println("OK!");
			int i = 1;
			System.out.println("Estas son las tablas disponibles en la Base de Datos:");
			while (rs.next()) {
				System.out.println("	"+(i++)+"-. "+rs.getString(3));
			}
			rs.close();
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
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			while (rs.next()) {
				System.out.print("	"+(i++)+"-. "+rs.getString("COLUMN_NAME")+" ("+rs.getString("TYPE_NAME")+" -> "+rs.getInt("COLUMN_SIZE")+")");
				if (rs.getString("TYPE_NAME").equals("INT")){
					System.out.print(" (Integer) -> ");
					int newDato = teclado.nextInt(); teclado.nextLine();
					datos.add(newDato);
				} else if (rs.getString("TYPE_NAME").equals("VARCHAR")){
					System.out.print(" (String) -> ");
					String newDato = teclado.nextLine();
					datos.add(newDato);
				} else if (rs.getString("TYPE_NAME").equals("DOUBLE")){
					System.out.print(" (Double) -> ");
					double newDato = teclado.nextDouble(); teclado.nextLine();
					datos.add(newDato);
				} else if (rs.getString("TYPE_NAME").equals("BIT")){
					System.out.print(" (true/false) -> ");
					String temp = teclado.nextLine();
					boolean newDato = false;
					if (temp.toLowerCase().equals("true")) newDato = true;
					if (temp.toLowerCase().equals("false")) newDato = false;
					datos.add(newDato);
				} else System.out.println("ERROR: Tipo de dato no soportado.");
			}
			rs.close();
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
				if (j == (i - 1)) exeUpdate += ")";
				else exeUpdate += ", ";
				j++;
			}
			System.out.print("Escribiendo nuevo dato en la Base de Datos...");
			st.executeUpdate(exeUpdate); 
			System.out.println("");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR: Datos repetidos en la Base de Datos. / Dato relacionado no encontrado.");
		}catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la Base de Datos.");
		}
	}

	public void editarDatos(String nombreT){
		
	}
	
	public void verDatos(String nombreT){
		try {
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getColumns(null, null, nombreT, null);
		    /*
		    for (int i = 1; i != numCols; i++){
		    	
		    }
		    */
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
			e.printStackTrace();
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