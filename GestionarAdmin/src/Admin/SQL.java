package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SQL {
	Scanner teclado = new Scanner(System.in);
	Connection con;
	
	public SQL(String nombreBD) {
        try {
        	// Cargar el "driver" para utilizar la BD.
        	System.out.print("Cargando el driver de la base de datos SQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            // Cargar la base de datos desde una URL (Establecer URL, usuario y contraseña)
            System.out.print("Conectando con la base de datos SQL... ");
            String URL = "jdbc:mysql://olaldiko.mooo.com:23306/"+nombreBD;
            con = DriverManager.getConnection(URL, "urko", "123ABCabc");
            System.out.println(" OK!");
            // PRUEBAS
            cargarT();
            System.out.print("Nombre de la tabla de la cual queremos cargar los datos: ");
			String nombreT = teclado.nextLine();
			insertarD(nombreT);
            // PRUEBAS
        } catch (SQLException ex) {
            System.out.println("ERROR: Imposible conectar con la base de datos.");
        } catch (ClassNotFoundException ex) {
        	System.out.println("ERROR: Imposible cargar el driver.");
        } catch (Exception ex) {
            System.out.println("FATAL ERROR.");
        }
    }
	
	public void cargarT(){
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
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	public void insertarD(String nombreT){
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
				}
				else if (rs.getString("TYPE_NAME").equals("VARCHAR")){
					System.out.print(" (String) -> ");
					String newDato = teclado.nextLine();
					datos.add(newDato);
				}
				else if (rs.getString("TYPE_NAME").equals("BIT")){
					System.out.print(" (true/false) -> ");
					String temp = teclado.nextLine();
					boolean newDato = false;
					if (temp.toLowerCase().equals("true")) newDato = true;
					if (temp.toLowerCase().equals("false")) newDato = false;
					datos.add(newDato);
				} else System.out.println("ERROR: Tipo de dato no soportado.");
			}
			
			//st.executeUpdate("INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)"); 
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	public void cargarD(String nombreT){
		try {
			System.out.println("");
			System.out.print("Cargando datos de la tabla... ");
			java.sql.DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getColumns(null, null, nombreT, null);
			System.out.println("OK!");
			int i = 1;
			while (rs.next()) {
				System.out.println("	"+(i++)+"-. "+rs.getString("COLUMN_NAME")+" ("+rs.getString("TYPE_NAME")+" -> "+rs.getInt("COLUMN_SIZE")+")");
			}
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	/*
	private void cargarDatos(){
		try {
			Statement estado = con.createStatement();
			ResultSet resultado = estado.executeQuery("SELECT * FROM Equipos");
			while(resultado.next()){
				Equipo nuevoEquipo  = new Equipo(resultado.getInt("ID"), resultado.getString("NAME"), resultado.getString("SHORTNAME"), resultado.getString("CRESTURL"));
				System.out.println(resultado.getInt("ID")+" - Nombre: "+resultado.getString("NAME")+" / Nombre corto: "+resultado.getString("SHORTNAME"));
			}
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	private void leerDatos(){
		try {
			Statement estado = con.createStatement();
			ResultSet resultado = estado.executeQuery("SELECT * FROM Equipos");
			while(resultado.next()){
				Equipo nuevoEquipo  = new Equipo(resultado.getInt("ID"), resultado.getString("NAME"), resultado.getString("SHORTNAME"), resultado.getString("CRESTURL"));
				System.out.println(resultado.getInt("ID")+" - Nombre: "+resultado.getString("NAME")+" / Nombre corto: "+resultado.getString("SHORTNAME"));
			}
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	*/
	
	public static void main(String args[]){
		try{
			@SuppressWarnings("unused")
			SQL nuevaBD = new SQL("mordorbet");
		} catch (Exception ex) {
            System.out.println("ERROR: Imposible ejecutar petición.");
		}		
	}
}