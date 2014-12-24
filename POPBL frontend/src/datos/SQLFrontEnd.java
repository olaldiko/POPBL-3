package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class SQLFrontEnd {
	Connection base;
	
	public SQLFrontEnd(String URL, int numeroPuerto, String nombreBD, String user, String pass) throws ClassNotFoundException, SQLException {
        	System.out.print("Cargando el driver de la Base de Datos MySQL... ");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(" OK!");
            System.out.print("Conectando con Base de Datos MySQL... ");
            String generalURL = "jdbc:mysql://"+URL+":"+numeroPuerto+"/"+nombreBD;
            base = DriverManager.getConnection(generalURL, user, pass);
            
            System.out.println(" OK!");
            System.out.println("");	
    }
	public ObservableList<Partido> getPartidos(int dias, int idLiga, boolean jugados) throws SQLException{
		ArrayList<Partido> partidos = new ArrayList<>();
		ObservableList<Partido> lista = FXCollections.observableArrayList(partidos);
		Statement stat;
		ResultSet resultados;
		ResultSetMetaData meta;
		Partido p;
		String queryjugados;
		if(jugados){
			queryjugados = "PARTIDOS.VIGENTE = 0";
		}else{
			queryjugados = "PARTIDOS.VIGENTE = 1";
		}
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT PARTIDOS.idPartidos, PARTIDOS.idJornada, PARTIDOS.Fecha, PARTIDOS.GolesLocal, PARTIDOS.GolesVisitante, "
				+ "PARTIDOS.idLocal, eq_local.Nombre as local, PARTIDOS.idVisitante, eq_visit.Nombre as visitante , eq_local.Escudo as esclocal, eq_visit.Escudo as escvisit , "
				+ "PARTIDOS.CoefLocal, PARTIDOS.CoefEmpate, PARTIDOS.CoefVisitante "
				+ "FROM PARTIDOS "
				+ "INNER JOIN mordorbet.EQUIPOS eq_local ON PARTIDOS.idLocal = eq_local.idEquipos " 
				+ "INNER JOIN mordorbet.EQUIPOS eq_visit ON PARTIDOS.idVisitante = eq_visit.idEquipos "
				+ "WHERE  (DATEDIFF(PARTIDOS.Fecha , DATE_ADD(CURDATE(), INTERVAL "+dias+" DAY) )< "+dias+") AND ((eq_local.idLiga = "+idLiga+") OR (eq_visit.idLiga = "+idLiga+")) AND "+queryjugados+" ;");
		meta = resultados.getMetaData();
		while(resultados.next()){
			p = new Partido();
			p.setIdPartido(resultados.getInt("PARTIDOS.idPartidos"));
			p.setIdJornada(resultados.getInt("PARTIDOS.idJornada"));
			p.setFecha(resultados.getTimestamp("PARTIDOS.Fecha"));
			p.setGolesLocal(resultados.getInt("PARTIDOS.GolesLocal"));
			p.setGolesVisitante(resultados.getInt("PARTIDOS.GolesVisitante"));
			p.getLocal().setIdEquipo(resultados.getInt("PARTIDOS.idLocal"));
			p.getLocal().setNombre(resultados.getString("local"));
			p.getVisitante().setIdEquipo(resultados.getInt("PARTIDOS.idVisitante"));
			p.getVisitante().setNombre(resultados.getString("visitante"));
			p.getLocal().setEscudo(resultados.getURL("esclocal"));
			p.getVisitante().setEscudo(resultados.getURL("escvisit"));
			p.setCoefLocal(resultados.getDouble("PARTIDOS.CoefLocal"));
			p.setCoefEmpate(resultados.getDouble("PARTIDOS.CoefEmpate"));
			p.setCoefVisitante(resultados.getDouble("PARTIDOS.CoefVisitante"));
			lista.add(p);
		}
		resultados.close();
		stat.close();
		return lista;
	}
	public Partido getPartido(int idPartido) throws SQLException{
		Partido p = new Partido();
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		
		resultado = stat.executeQuery("SELECT PARTIDOS.idPartidos, PARTIDOS.idJornada, PARTIDOS.Fecha, PARTIDOS.GolesLocal, PARTIDOS.GolesVisitante, "
				+ "PARTIDOS.idLocal, eq_local.Nombre as local, PARTIDOS.idVisitante, eq_visit.Nombre as visitante , eq_local.Escudo as esclocal, eq_visit.Escudo as escvisit, "
				+ "PARTIDOS.CoefLocal, PARTIDOS.CoefEmpate, PARTIDOS.CoefVisitante "
				+ "FROM PARTIDOS "
				+ "INNER JOIN mordorbet.EQUIPOS eq_local ON PARTIDOS.idLocal = eq_local.idEquipos " 
				+ "INNER JOIN mordorbet.EQUIPOS eq_visit ON PARTIDOS.idVisitante = eq_visit.idEquipos "
				+ "WHERE PARTIDOS.idPartido = "+idPartido+";");
		while(resultado.next()){
			p.setIdPartido(resultado.getInt("PARTIDOS.idPartidos"));
			p.setIdJornada(resultado.getInt("PARTIDOS.idJornada"));
			p.setFecha(resultado.getTimestamp("PARTIDOS.Fecha"));
			p.setGolesLocal(resultado.getInt("PARTIDOS.GolesLocal"));
			p.setGolesVisitante(resultado.getInt("PARTIDOS.GolesVisitante"));
			p.getLocal().setIdEquipo(resultado.getInt("PARTIDOS.idLocal"));
			p.getVisitante().setIdEquipo(resultado.getInt("eq_local.Nombre"));
			p.getLocal().setNombre(resultado.getString("PARTIDOS.idVisitante"));
			p.getVisitante().setNombre(resultado.getString("eq_visit.Nombre"));
			p.getLocal().setEscudo(resultado.getURL("esclocal"));
			p.getVisitante().setEscudo(resultado.getURL("escvisit"));
			p.setCoefLocal(resultado.getDouble("PARTIDOS.CoefLocal"));
			p.setCoefEmpate(resultado.getDouble("PARTIDOS.CoefEmpate"));
			p.setCoefVisitante(resultado.getDouble("PARTIDOS.CoefVisitante"));
		}
		resultado.close();
		stat.close();
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
			a.setIdApuesta(resultados.getInt(1));
			a.setIdUsuario(resultados.getInt(2));
			a.setPartido(getPartido(resultados.getInt(3)));
			a.setTipoApuesta(resultados.getInt(4));
			a.setPremio(resultados.getDouble(5));
			a.setApostado(resultados.getDouble(6));
			a.setCoeficiente(resultados.getDouble(7));
			a.setCobrado(resultados.getBoolean(8));
			lista.add(a);
		}
		resultados.close();
		stat.close();
		return lista;
	}
	
	public ObservableList<Liga> getLigas() throws SQLException{
		Liga l;
		ArrayList<Liga> ligas = new ArrayList<>();
		ObservableList<Liga> lista = FXCollections.observableArrayList(ligas);
		Statement stat;
		ResultSet resultados;
		ResultSetMetaData meta;
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT * FROM LIGAS;");
		meta = resultados.getMetaData();
		while(resultados.next()){
			l = new Liga();
			l.setIdLiga(resultados.getInt(1));
			l.setNombre(resultados.getString(2));
			lista.add(l);
		}
		resultados.close();
		stat.close();
		return lista;
	}
	public void crearApuesta(int idUsuario, int idPartido, int tipo, Double apostado, Double coef) throws SQLException{
		Statement stat;
		stat = base.createStatement();
		stat.executeUpdate("INSERT INTO APUESTAS (idUsuarios, idPartidos, Apuesta, Premio, Apostado, Coeficiente, Cobrado) "
				+ "values ("+idUsuario+" , "+idPartido+" , "+tipo+" , "+(apostado*coef)+" , "+apostado+" , "+coef+" , 0);"); 
		stat.close();
	}
	public void crearUser(Usuario u) throws SQLException{
		Statement stat;
		stat = base.createStatement();
		stat.executeUpdate("INSERT INTO USUARIOS (username, Password, Dinero, Correo, Idal)"
						+ " values ('"+u.getNombre()+"' , '"+u.getPassword()+"' , 0, '"+u.getCorreo()+"' , '"+u.getIdal()+"' );");
		stat.close();
	}
	public boolean loginUser(String user, String pass) throws SQLException{
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT count(username) as users from USUARIOS where username = '"+user+"'and Password = '"+pass+"';");
		resultado.first();
		if(resultado.getInt(1) == 1){
			return true;
		}else{
			return false;
		}
	}
}
