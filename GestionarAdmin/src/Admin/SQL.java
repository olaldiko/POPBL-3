package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	static Connection con;
	public static void cargarBD(String nombreDeB) {
        try {
        	// Cargar el "driver" para utilizar la BD.
        	System.out.print("Cargando el driver de la base de datos SQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            // Cargar la base de datos desde una URL (Establecer URL, usuario y contraseña)
            System.out.print("Conectando con la base de datos SQL... ");
            String URL = "jdbc:mysql://localhost/"+nombreDeB;
            con = DriverManager.getConnection(URL, "root", "1234");
            System.out.println(" OK!");
        } catch (SQLException ex) {
            System.out.println("ERROR: Imposible conectar con la base de datos.");
        } catch (ClassNotFoundException ex) {
        	System.out.println("ERROR: Imposible cargar el driver.");
        } catch (Exception ex) {
            System.out.println("FATAL ERROR.");
        }
    }
	
	public static void datosEquipos(){
		try {
			Statement estado = con.createStatement();
			ResultSet resultado = estado.executeQuery("SELECT * FROM Equipos");
			while(resultado.next()){
				System.out.println(resultado.getInt("ID")+" - Nombre: "+resultado.getString("NAME")+" / Nombre corto: "+resultado.getString("SHORTNAME"));
			}
		} catch (SQLException e) {
			System.out.println("ERROR: Imposible conectar con la base de datos.");
		}
	}
	
	public static void main(String args[]){
		try{
			cargarBD("Principal");
			datosEquipos();
		} catch (Exception ex) {
            System.out.println("ERROR: Imposible ejecutar petición.");
		}		
	}
}