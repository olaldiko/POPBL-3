package datos;

import java.sql.SQLException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import application.ManteniException;

public class ModeloApuestas{

	static ModeloApuestas modelo;
	/**
	 * 
	 */
	public static ModeloApuestas getInstance()throws ManteniException{
		if(modelo != null){
			return modelo;
		}else{
			modelo = new ModeloApuestas();
			return modelo;
		}
	}
	//Parametros de conexion
	private final String urlBase = "192.168.1.210";
	private final int puertoBase = 3306;
	private final String baseBase = "mordorbet";
	private final String userBase = "frontend";
	private final String passBase = "frontend";
	
	
	SQLFrontEnd bd;
	ObservableList<Apuesta> apuestasuser;
	ObservableList<Partido> partidosprincipal;
	ObservableList<Liga>	ligas;
	ObservableList<Partido> partidosemaitzak;
	
	ObjectProperty<Partido> partidoApuesta = new SimpleObjectProperty<Partido>();
	int diasPartidos = 10;
	int defaultLiga = 358;
	/*Para hacer checker de modificacion en tablas
	 * http://dba.stackexchange.com/questions/9569/fastest-way-to-check-if-innodb-table-has-changed
	 * 
	 */
	int tablaPartidosCont = 0;
	
	private ModeloApuestas() throws ManteniException{
		try{
			bd = new SQLFrontEnd(urlBase, puertoBase , baseBase, userBase, passBase);
			this.initLigas();
			this.initPartidosPr();
			this.initPartidosEmaitzak();
		}catch(SQLException e){
				e.printStackTrace();
				throw new ManteniException(0);
		} catch (ClassNotFoundException e) {
			    throw new ManteniException(1);
		}

	}
	
	public ObservableList<Apuesta> getApuestasuser() {
		return apuestasuser;
	}
	public void setApuestasuser(ObservableList<Apuesta> apuestasuser) {
		this.apuestasuser = apuestasuser;
	}
	public ObservableList<Partido> getPartidosprincipal() {
		return partidosprincipal;
	}
	public void setPartidosprincipal(ObservableList<Partido> partidosprincipal) {
		this.partidosprincipal = partidosprincipal;
	}
	public ObservableList<Liga> getLigas() {
		return ligas;
	}
	public void setLigas(ObservableList<Liga> ligas) {
		this.ligas = ligas;
	}
	public ObservableList<Partido> getPartidosemaitzak() {
		return partidosemaitzak;
	}
	public void setPartidosemaitzak(ObservableList<Partido> partidosemaitzak) {
		this.partidosemaitzak = partidosemaitzak;
	}
	
	public void initLigas() throws ManteniException{
		try {
			this.ligas = bd.getLigas();
			assert this.ligas != null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManteniException(3);
		}
	}
	public void initPartidosPr() throws ManteniException{
		try {
			this.partidosprincipal = bd.getPartidos(diasPartidos, defaultLiga, false);
		}catch(SQLException e){
			e.printStackTrace();
			throw new ManteniException(3);
		}
	}
	public void initPartidosEmaitzak() throws ManteniException{
		try {
			this.partidosemaitzak = bd.getPartidos(diasPartidos, defaultLiga, true);
			if(partidosemaitzak == null)System.out.println("Partidosemaitzak es null");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManteniException(3);
		}
	}
	public void updatePartidosPr(int ligaselect) throws ManteniException{
		try {
			this.partidosprincipal.setAll(bd.getPartidos(diasPartidos, ligaselect, false));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManteniException(3);
		}
	}
	public void updatePartidosEmaitzak(int ligaselect) throws ManteniException{
		try {
			this.partidosemaitzak.setAll(bd.getPartidos(diasPartidos, ligaselect, true));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManteniException(3);
		}
	}
	public void newApuesta(int idUsuario, int idPartido, int tipo, Double apostado, Double coef) throws ManteniException{
		try {
			bd.crearApuesta(idUsuario, idPartido, tipo, apostado, coef);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ManteniException(4);
		}
	}
	public void setPartidoApuesta(Partido p){
		partidoApuesta.set(p);
	}
	public Partido getPartidoApuesta(){
		return partidoApuesta.get();
	}
	public boolean loginuser(String user, String pass) throws ManteniException{
		boolean resultado = false;
			try{
			resultado = bd.loginUser(user, pass);
			}catch(SQLException e){
				throw new ManteniException(2);
			}
		return resultado;
	}
}
