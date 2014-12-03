package popbl;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Principal {
	Connection con;
	
	public Principal() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://olaldiko.mooo.com:23306/mordorbet", "phpuser", "unibertsitatea");
			cargarLigas();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void cargarLigas() {
		FileReader in = null;
		FileReader clasi = null;
		String sql = "";
		Statement st = null;
		Statement stq = null;

		try {
			in = new FileReader("main.json");
			
			JSONArray array = (JSONArray) (new JSONParser()).parse(in);
			
			int i=0;
			st = con.createStatement();
			stq = con.createStatement();
			
			while (array.size()>i) {
				JSONObject obj = (JSONObject) array.get(i);				

				clasi = new FileReader(obj.get("id")+".json");
				JSONObject obj2 = (JSONObject) (new JSONParser()).parse(clasi);
				
				
				ResultSet rs = stq.executeQuery("SELECT * FROM LIGAS WHERE idLiga="+obj.get("id"));

				if (rs.next()) {
					sql = "UPDATE LIGAS SET Jornada="+obj2.get("matchday")+ " WHERE idLiga="+obj.get("id");
				} else {
					sql = "INSERT INTO LIGAS VALUES (";
					sql+= obj.get("id")+",'"+obj.get("caption")+"', "+obj2.get("matchday")+")";
				}
				
				
				try {
					st.executeUpdate(sql);
					System.out.println("Ligas: Se ha introducido: "+obj.get("id")+" "+obj.get("caption"));
				} catch (MySQLIntegrityConstraintViolationException e) {
					System.out.println("Ligas: Se ha encontrado clave primaria repetida.");
				} finally {
					int ref = Integer.parseInt(obj.get("id").toString());
					
					//cargarEquipos(ref);
					cargarJornadas(ref);
					i++;
				}
			}
		
		} catch (IOException | ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void cargarEquipos(int id_liga) {
		FileReader in = null;
		FileReader clasi = null;
		String sql = "";
		Statement st = null;
		Statement stq = null;
		
		try {
			in = new FileReader(id_liga+"_equipos.json");
			clasi = new FileReader(id_liga+".json");
			JSONObject aux = (JSONObject) (new JSONParser()).parse(clasi);
			JSONArray ranking = (JSONArray) aux.get("ranking");
			
			JSONArray array = (JSONArray) new JSONParser().parse(in);
			
			int i=0;
			int j=0;
			st = con.createStatement();
			stq = con.createStatement();
			//ArrayList<Object> lista = new ArrayList<>();
			while (array.size()>i) {
				j=0;
				JSONObject obj = (JSONObject) array.get(i);
				
				while (!((JSONObject) ranking.get(j)).get("team").equals(obj.get("name"))) j++;
				JSONObject team = (JSONObject) ranking.get(j);
				
				ResultSet rs = stq.executeQuery("SELECT * FROM EQUIPOS WHERE idEquipos="+obj.get("id"));
				if (rs.next()) {
					sql = "UPDATE EQUIPOS SET ";
					sql+= "Puntos="+team.get("points")+",GolesFavor="+team.get("goals")+",GolesContra="+team.get("goalsAgainst");
					sql+= " WHERE idEquipos="+obj.get("id");
				} else {
					sql = "INSERT INTO EQUIPOS VALUES ("+obj.get("id")+",'"+obj.get("name")+"','"+((String)obj.get("shortName")).replaceAll("'", "''")+"',";
					sql+= "'"+obj.get("crestUrl")+"',0,"+id_liga+","+team.get("points")+","+team.get("goals")+","+team.get("goalsAgainst")+")";
				}
				
				
				try {
					st.executeUpdate(sql);
					System.out.println("Equipos: Se ha introducido: "+obj.get("id")+" "+obj.get("name")+" con "+team.get("points")+" puntos");
				} catch (MySQLIntegrityConstraintViolationException e) {
					System.out.println("Equipos: Se ha encontrado clave primaria repetida.");
				} finally {
					i++;
				}
			}
		} catch (IOException e){
			System.out.println("No se ha encontrado el archivo.");		
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				in.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	private void cargarJornadas(int id_liga) {
		FileReader in = null;
		Statement st = null;
		Statement stq = null;
		String sql = "";
		String sqlq = "";
		
		try {
			in = new FileReader(id_liga+"_partidos.json");
			
			JSONArray array = (JSONArray) new JSONParser().parse(in);
			
			int i=0;
			st = con.createStatement();
			stq = con.createStatement();
			//ArrayList<Object> lista = new ArrayList<>();
			while (array.size()>i) {
				JSONObject obj = (JSONObject) array.get(i);
				
				sqlq = "SELECT idJornada FROM JORNADAS WHERE idJornada="+id_liga+obj.get("matchday");
				ResultSet rs = stq.executeQuery(sqlq);
				if (!rs.next()) {
					sql = "INSERT INTO JORNADAS VALUES ("+id_liga+obj.get("matchday")+","+obj.get("matchday")+","+id_liga+")";
					st.executeUpdate(sql);
				}
				
				sqlq = "SELECT idPartidos FROM PARTIDOS WHERE idPartidos="+obj.get("id");
				rs = stq.executeQuery(sqlq);
				if (rs.next()) {
					sql = "UPDATE PARTIDOS SET ";
					if (Integer.parseInt(obj.get("goalsHomeTeam").toString())==-1) sql+="Vigente=TRUE, ";
					else sql+="Vigente=FALSE, ";
					sql+="GolesLocal="+obj.get("goalsHomeTeam")+", GolesVisitante="+obj.get("goalsAwayTeam")+" WHERE idPartidos="+obj.get("id");
				} else {
					sqlq = "SELECT idEquipos, idEstadio FROM EQUIPOS WHERE Nombre='"+obj.get("homeTeam")+"'";
					ResultSet rs2 = stq.executeQuery(sqlq);
					rs2.next();

					int id_local = rs2.getInt("idEquipos");
					int id_estadio = rs2.getInt("idEstadio");
					
					sqlq = "SELECT idEquipos, idEstadio FROM EQUIPOS WHERE Nombre='"+obj.get("awayTeam")+"'";
					rs2 = stq.executeQuery(sqlq);
					rs2.next();
					
					int id_visitante = rs2.getInt("idEquipos");
					
					sql = "INSERT INTO PARTIDOS VALUES (";
					sql+= obj.get("id")+","+id_liga+obj.get("matchday")+",STR_TO_DATE('"+obj.get("date")+"', '%Y-%m-%dT%H:%i:%sZ'),";
					sql+=+id_local+","+id_visitante+","+obj.get("goalsHomeTeam")+","+obj.get("goalsAwayTeam")+",";
					
					if (Integer.parseInt(obj.get("goalsHomeTeam").toString())==-1) sql+="FALSE,";
					else sql+="TRUE,";
					sql+=id_estadio+")";
				}
				
				System.out.println(sql);
				try {
					st.executeUpdate(sql);
					System.out.println("Partido introducido, Jornada "+obj.get("matchday")+":"+obj.get("homeTeam")+" - "+obj.get("awayTeam"));
				} catch (MySQLIntegrityConstraintViolationException e) {
					System.out.println("Partidos: Se ha encontrado clave primaria "+obj.get("id")+" repetida.");
				} finally {
					i++;
				}
			}
		} catch (IOException e){
			System.out.println("No se ha encontrado el archivo.");		
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				in.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		
	}
	
	public static void main (String [] args) {
		Principal app = new Principal();
	}
	
}
