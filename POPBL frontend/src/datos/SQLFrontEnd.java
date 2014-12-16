package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.JFrame;



public class SQLFrontEnd {
	Connection base;
	
	public SQLFrontEnd(String URL, int numeroPuerto, String nombreBD, String user, String pass, JFrame ventana) throws ClassNotFoundException, SQLException {
        	System.out.print("Cargando el driver de la Base de Datos MySQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con Base de Datos MySQL... ");
            String generalURL = "jdbc:mysql://"+URL+":"+numeroPuerto+"/"+nombreBD;
            base = DriverManager.getConnection(generalURL, user, pass);
            System.out.println(" OK!");
            System.out.println("");	
    }
	public ObservableList<Partido> getPartidos(int dias, int idLiga) throws SQLException{
		ArrayList<Partido> partidos = new ArrayList<>();
		ObservableList<Partido> lista = FXCollections.observableArrayList(partidos);
		Statement stat;
		ResultSet resultados;
		Partido p;
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT PARTIDOS.idPartidos, PARTIDOS.idJornada, PARTIDOS.Fecha, PARTIDOS.GolesLocal, PARTIDOS.GolesVisitante, "
				+ "PARTIDOS.idLocal, eq_local.Nombre as local, PARTIDOS.idVisitante, eq_visit.Nombre as visitante FROM PARTIDOS"
				+"INNER JOIN mordorbet.EQUIPOS eq_local ON PARTIDOS.idLocal = eq_local.idEquipos" 
				+"INNER JOIN mordorbet.EQUIPOS eq_visit ON PARTIDOS.idVisitante = eq_visit.idEquipos"
				+"WHERE  (DATEDIFF(PARTIDOS.Fecha , DATE_ADD(CURDATE(), INTERVAL "+dias+" DAY) )< "+dias+") AND ((eq_local.idLiga = "+idLiga+") OR (eq_visit.idLiga = "+idLiga+"));");
		while(resultados.next()){
			p = new Partido();
			p.setIdPartido(resultados.getInt(0));
			p.setIdJornada(resultados.getInt(1));
			p.setFecha(resultados.getTimestamp(2));
			p.setGolesLocal(resultados.getInt(3));
			p.setGolesVisitante(resultados.getInt(4));
			p.setIdLocal(resultados.getInt(5));
			p.setIdVisitante(resultados.getInt(6));
			p.setLocal(resultados.getString(7));
			p.setVisitante(resultados.getString(8));
			lista.add(p);
		}
		resultados.close();
		return lista;
	}
	public Partido getPartido(int idPartido) throws SQLException{
		Partido p = new Partido();
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		
		resultado = stat.executeQuery("SELECT PARTIDOS.idPartidos, PARTIDOS.idJornada, PARTIDOS.Fecha, PARTIDOS.GolesLocal, PARTIDOS.GolesVisitante, "
				+ "PARTIDOS.idLocal, eq_local.Nombre as local, PARTIDOS.idVisitante, eq_visit.Nombre as visitante FROM PARTIDOS"
				+"INNER JOIN mordorbet.EQUIPOS eq_local ON PARTIDOS.idLocal = eq_local.idEquipos" 
				+"INNER JOIN mordorbet.EQUIPOS eq_visit ON PARTIDOS.idVisitante = eq_visit.idEquipos"
				+"WHERE PARTIDOS.idPartido = "+idPartido+";");
		while(resultado.next()){
			p.setIdPartido(resultado.getInt(0));
			p.setIdJornada(resultado.getInt(1));
			p.setFecha(resultado.getTimestamp(2));
			p.setGolesLocal(resultado.getInt(3));
			p.setGolesVisitante(resultado.getInt(4));
			p.setIdLocal(resultado.getInt(5));
			p.setIdVisitante(resultado.getInt(6));
			p.setLocal(resultado.getString(7));
			p.setVisitante(resultado.getString(8));
		}
		resultado.close();
		return p;
		
	}
	public ObservableList<Apuesta> getApuestas(int idUser) throws SQLException{
		Apuesta a;
		ArrayList<Apuesta> apuestas = new ArrayList<>();
		ObservableList<Apuesta> lista = FXCollections.observableArrayList(apuestas);
		Statement stat;
		ResultSet resultados;
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT * FROM APUESTAS WHERE idUsuarios = "+idUser+";");
		while(resultados.next()){
			a = new Apuesta();
			a.setIdApuesta(resultados.getInt(0));
			a.setIdUsuario(resultados.getInt(1));
			a.setPartido(getPartido(resultados.getInt(2)));
			a.setTipoApuesta(resultados.getInt(3));
			a.setPremio(resultados.getDouble(4));
			a.setApostado(resultados.getDouble(5));
			a.setCoeficiente(resultados.getDouble(6));
			a.setCobrado(resultados.getBoolean(7));
		}
		resultados.close();
		return lista;
	}
	
	public ObservableList<Liga> getLigas() throws SQLException{
		Liga l;
		ArrayList<Liga> ligas = new ArrayList<>();
		ObservableList<Liga> lista = FXCollections.observableArrayList(ligas);
		Statement stat;
		ResultSet resultados;
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT idLiga, Nombre FROM LIGAS;");
		while(resultados.next()){
			l = new Liga();
			l.setIdLiga(resultados.getInt(0));
			l.setNombre(resultados.getString(1));
		}
		resultados.close();
		return lista;
	}
	public boolean crearUser(Usuario u) throws SQLException{
		Statement stat;
		int resultado;
		stat = base.createStatement();
		resultado = stat.executeUpdate("INSERT INTO USUARIOS (username, Password, Dinero, Correo, Idal)"
				+ " values ('"+u.getNombre()+"' , '"+u.getPassword()+"' , 0, '"+u.getCorreo()+"' , '"+u.getIdal()+"' );");
		if(resultado == 0){
			return false;
		}else{
			return true;
		}
	}
	public boolean loginUser(String user, String pass) throws SQLException{
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT * FROM USUARIOS WHERE username = \""+user+"\" AND Password = \""+pass+"\";");
		resultado.first();
		if(resultado.wasNull()){
			return false;
		}else{
			return true;
		}
	}
}
