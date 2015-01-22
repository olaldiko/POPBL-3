package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ListIterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase con todas las funciones necesarias para interactuar con la base de datos
 * @author gorkaolalde
 *
 */

public class SQLFrontEnd {
	/*TODO: Seria mejor tratar las SQLException aqui para hacer finalys y cerrar las conexiones? 
	 * Asi se simplificaria ModeloApuestas
	 * 
	 */
	
	
	Connection base;
/**
 * Inicializa la conexion a la base con los siguientes parametros	
 * @param URL direccion de la base
 * @param numeroPuerto puerto de la base
 * @param nombreBD nombre de la base
 * @param user username
 * @param pass clave
 * @throws ClassNotFoundException Si no puede encontrar el jar del conector a MySQL
 * @throws SQLException Si ha ocurrido algun error en la conexion.
 */
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
	/**
	 * Devuelve partidos desde la base de datos
	 * @param dias intervalo de dias que tiene que mostrar
	 * @param idLiga liga de la que mostrar los partidos 
	 * @param jugados si estos se han jugado o no
	 * @return Observable de partidos
	 * @throws SQLException si falla la consulta o la conexion
	 */
	public ObservableList<Partido> getPartidos(int dias, int idLiga, boolean jugados) throws SQLException{
		ArrayList<Partido> partidos = new ArrayList<>();
		ObservableList<Partido> lista = FXCollections.observableArrayList(partidos);
		Statement stat;
		ResultSet resultados;
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
				//La siguente parte puede que de problemas al cargar partidos jugados, estamos mirando diferencia con fecha futura
				+ "WHERE  (DATEDIFF(PARTIDOS.Fecha , DATE_ADD(CURDATE(), INTERVAL "+dias+" DAY) )< "+dias+") AND ((eq_local.idLiga = "+idLiga+") OR (eq_visit.idLiga = "+idLiga+")) AND "+queryjugados+" ;");
		
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
	/**
	 * devuelve un partido en concreto desde la base
	 * @param idPartido id del partido a monstrar
	 * @return objeto partido
	 * @throws SQLException Tira si da problemas al ejecutar la consulta
	 */
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
				+ "WHERE PARTIDOS.idPartidos = "+idPartido+";");
		while(resultado.next()){
			p.setIdPartido(resultado.getInt("PARTIDOS.idPartidos"));
			p.setIdJornada(resultado.getInt("PARTIDOS.idJornada"));
			p.setFecha(resultado.getTimestamp("PARTIDOS.Fecha"));
			p.setGolesLocal(resultado.getInt("PARTIDOS.GolesLocal"));
			p.setGolesVisitante(resultado.getInt("PARTIDOS.GolesVisitante"));
			p.getLocal().setIdEquipo(resultado.getInt("PARTIDOS.idLocal"));
			p.getVisitante().setIdEquipo(resultado.getInt("PARTIDOS.idLocal"));
			p.getLocal().setNombre(resultado.getString("local"));
			p.getVisitante().setNombre(resultado.getString("visitante"));
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
	/**
	 * Devuelve las apuestas de un usuario desde la base de datos
	 * @param idUser id de usuario
	 * @return observableList de apuesta
	 * @throws SQLException Si falla la consulta
	 */
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
			a.setVigente(resultados.getBoolean("APUESTAS.Vigente"));
			a.setGanado(resultados.getBoolean("APUESTAS.Ganado"));
			lista.add(a);
		}
		resultados.close();
		stat.close();
		return lista;
	}
	/**
	 * Devuelve la cuenta de las apuestas que ha ganado el usuario
	 * @param idUser id de usuario
	 * @return numero de apuestas ganandas
	 * @throws SQLException Si falla la consulta
	 */
	public int getApuestasGanadas(int idUser) throws SQLException{
		int ganadas = 0;
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT COUNT(idApuesta) FROM mordorbet.APUESTAS WHERE Ganado = 1 AND Vigente = 0 AND idUsuarios = "+idUser+" ;");
		resultado.first();
		ganadas = resultado.getInt(1);
		resultado.close();
		stat.close();
		return ganadas;
	}
	/**
	 * Devuelve la cuenta de apuestas pendientes de un usuario
	 * @param idUser id de usuario
	 * @return numero de apuestas pendientes
	 * @throws SQLException Si falla la consulta
	 */
	public int getApuestasPendientes(int idUser) throws SQLException{
		int pendientes = 0;
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT COUNT(idApuesta) FROM mordorbet.APUESTAS WHERE Vigente = 1 AND idUsuarios = "+idUser+" ;");
		resultado.first();
		pendientes = resultado.getInt(1);
		resultado.close();
		stat.close();
		return pendientes;
	}
	/**
	 * Devuelve la cuenta de apuestas perdidas de un usuario
	 * @param idUser id de usuario
	 * @return numero de apuestas perdidas
	 * @throws SQLException Si falla la consulta
	 */
	public int getApuestasPerdidas(int idUser) throws SQLException{
		int pendientes = 0;
		Statement stat;
		ResultSet resultado;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT COUNT(idApuesta) FROM mordorbet.APUESTAS WHERE Vigente = 0 AND Ganado = 0 AND idUsuarios = "+idUser+" ;");
		resultado.first();
		pendientes = resultado.getInt(1);
		resultado.close();
		stat.close();
		return pendientes;
	}
	/**
	 * Devuelve todas las ligas contenidas en la base de datos
	 * @return observableList de las ligas
	 * @throws SQLException
	 */
	public ObservableList<Liga> getLigas() throws SQLException{
		Liga l;
		ArrayList<Liga> ligas = new ArrayList<>();
		ObservableList<Liga> lista = FXCollections.observableArrayList(ligas);
		Statement stat;
		ResultSet resultados;
		stat = base.createStatement();
		resultados = stat.executeQuery("SELECT * FROM LIGAS;");
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
	/**
	 * Crea una apuesta con los siguientes datos en la base de datos
	 * @param idUsuario id del usuario
	 * @param idPartido id del partido
	 * @param tipo tipo de apuesta, 0 empate, 1 local, 2 visitante
	 * @param apostado dinero apostado
	 * @param coef coeficiente en el momento de hacer la apuesta
	 * @param premio premio que conseguiria el usuario
	 * @return devuelve el id de apuesta si se ha completado bien, sino 
	 * @throws SQLException
	 */
	public int crearApuesta(int idUsuario, int idPartido, int tipo, Double apostado, Double coef, Double premio) throws SQLException{
		Statement stat;
		ResultSet resultado;
		int idApuesta = 0;
		stat = base.createStatement();
		stat.executeUpdate("INSERT INTO APUESTAS(idUsuarios, idPartidos, Apuesta, Premio, Apostado, Coeficiente, Cobrado, Ganado, Vigente) "
				+ "VALUES ('"+idUsuario+"' , '"+idPartido+"' , '"+tipo+"' , '"+premio+"' , '"+apostado+"' , '"+coef+"' , 0 , 0 , 1)"); 
		resultado = stat.executeQuery("SELECT LAST_INSERT_ID()");
		resultado.next();
		if(resultado.first()){
			idApuesta = resultado.getInt(1);
		}
		stat.close();
		return idApuesta;
	}
	/**
	 * Inicia sesion con los siguientes datos
	 * @param user username
	 * @param pass contrasena
	 * @return el id de usuario si existe, sino -1
	 * @throws SQLException 
	 */
	public int loginUser(String user, String pass) throws SQLException{
		Statement stat;
		ResultSet resultado;
		int idUser;
		stat = base.createStatement();
		resultado = stat.executeQuery("SELECT idUsuarios from USUARIOS where username = '"+user+"'and Password = '"+pass+"';");
		if(!resultado.next()){
			resultado.close();
			stat.close();
			return -1;
		}else{
			idUser = resultado.getInt("USUARIOS.idUsuarios");
			resultado.close();
			stat.close();
			return idUser;
		}
	}
	/**
	 * Anade un usuario a la base con lo siguientes datos
	 * @param nick username
	 * @param nombre nombre de usuario
	 * @param apellido apellido
	 * @param email email del usuario
	 * @param pass contrasena
	 * @param idal idal o dni
	 * @throws SQLException
	 */
	public void addUser(String nick, String nombre, String apellido, String email, String pass, int idal) throws SQLException{
		Statement stat;
		stat = base.createStatement();
		//TODO: Por ahora en la base no metemos nombre y apellidos, dejo para luego, asi como verificar que no hay user con mismo nombre(hacer primary key a username?);
		stat.executeUpdate("INSERT INTO USUARIOS (username, Password, Dinero, Correo, idal)"
				+ "values('"+nick+"', '"+pass+"', 0, '"+email+"' , "+idal+");");
		stat.close();
	}
	/**
	 * Marca las apuestas en la base de datos como cobradas
	 * @param apuestas apuestas para cobrar
	 * @return dinero ganado
	 * @throws SQLException
	 */
	public Double cobrarApuestas(ArrayList<Apuesta> apuestas)throws SQLException{
		Statement stat;
		Double dinero = 0.0;
		stat = base.createStatement();
		for(Apuesta i : apuestas){
				stat.executeUpdate("UPDATE APUESTAS SET Cobrado = 1 WHERE idApuesta = "+i.getIdApuesta()+";");
				dinero += i.getPremio();
		}
		stat.close();
		return dinero;
		
	}
}
