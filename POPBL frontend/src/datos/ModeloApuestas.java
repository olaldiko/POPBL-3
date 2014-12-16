package datos;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import application.ManteniException;

public class ModeloApuestas{
	/**
	 * 
	 */
	private static final long serialVersionUID = 409072967557047817L;
	SQLFrontEnd bd;
	ObservableList<Apuesta> apuestasuser;
	ObservableList<Partido> partidosprincipal;
	ObservableList<Liga>	ligas;
	ObservableList<Partido> partidosemaitzak;
	int diasPartidos = 10;
	int defaultLiga = 358;
	/*Para hacer checker de modificacion en tablas
	 * http://dba.stackexchange.com/questions/9569/fastest-way-to-check-if-innodb-table-has-changed
	 * 
	 */
	int tablaPartidosCont = 0;
	
	public ModeloApuestas() throws ManteniException{
		try{
			bd = new SQLFrontEnd("olaldeko.mooo.com", 3306, "mordorbet", "frontend", "frontend");
		}catch(SQLException e){
				e.printStackTrace();
				throw new ManteniException(0);
		} catch (ClassNotFoundException e) {
			    throw new ManteniException(1);
		}

	}
	public void initLigas() throws ManteniException{
		try {
			this.ligas = bd.getLigas();
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
			this.partidosprincipal.setAll(bd.getPartidos(diasPartidos, ligaselect, true));
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
}
