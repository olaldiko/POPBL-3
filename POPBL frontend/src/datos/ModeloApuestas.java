package datos;

import java.sql.SQLException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
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
	//Parametros de conexion y base de datos
	private final String urlBase = "olaldiko.mooo.com";
	private final int puertoBase = 23306;
	private final String baseBase = "mordorbet";
	private final String userBase = "frontend";
	private final String passBase = "frontend";
	private SQLFrontEnd bd;
	
	//Apuestas usuario
	ObservableList<Apuesta> apuestasuser;
	ObservableList<PieChart.Data> estadisChart;
	
	//Pantalla principal y emaitzak
	ObservableList<Partido> partidosprincipal;
	ObservableList<Liga>	ligas;
	ObservableList<Partido> partidosemaitzak;
	int diasPartidos = 10;
	int defaultLiga = 358;
	int tablaPartidosCont = 0;
	/*Para hacer checker de modificacion en tablas
	 * http://dba.stackexchange.com/questions/9569/fastest-way-to-check-if-innodb-table-has-changed
	 * 
	 */
	
	//Nueva apuesta
	ObjectProperty<Partido> partidoApuesta = new SimpleObjectProperty<Partido>();
	DoubleProperty dirua = new SimpleDoubleProperty(0.0);
	Apuesta apuestaInProgress;
	
	
	//Interface Serial
	private final String puertoserie = "COM1";
	private SerialIO serial;
	
	//Login
	int destLogin = 0;
	public final int DEST_NIREAPOSTUAK = 0;
	public final int DEST_APOSTUBERRI = 1;
	int idUserLogin = -1;
	


	
	private ModeloApuestas() throws ManteniException{
		try{
			bd = new SQLFrontEnd(urlBase, puertoBase , baseBase, userBase, passBase);
			this.initLigas();
			this.initPartidosPr();
			this.initPartidosEmaitzak();
			this.serial = SerialIO.getInstance();
			this.serial.bindDirua(dirua);
			
		}catch(SQLException e){
				throw new ManteniException(0, e);
		} catch (ClassNotFoundException e) {
			    throw new ManteniException(1, e);
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
			throw new ManteniException(3, e);
		}
	}
	public void initPartidosPr() throws ManteniException{
		try {
			this.partidosprincipal = bd.getPartidos(diasPartidos, defaultLiga, false);
		}catch(SQLException e){
			throw new ManteniException(3, e);
		}
	}
	public void initPartidosEmaitzak() throws ManteniException{
		try {
			this.partidosemaitzak = bd.getPartidos(diasPartidos, defaultLiga, true);
			if(partidosemaitzak == null)System.out.println("Partidosemaitzak es null");
		} catch (SQLException e) {
			throw new ManteniException(3,e);
		}
	}
	public void updatePartidosPr(int ligaselect) throws ManteniException{
		try {
			this.partidosprincipal.setAll(bd.getPartidos(diasPartidos, ligaselect, false));
		} catch (SQLException e) {
			throw new ManteniException(3, e);
		}
	}
	public void initApuestasUser() throws ManteniException{
		try {
			this.apuestasuser = bd.getApuestas(idUserLogin);
		} catch (SQLException e) {
			throw new ManteniException(3, e);
		}
		
	}
	public void updatePartidosEmaitzak(int ligaselect) throws ManteniException{
		try {
			this.partidosemaitzak.setAll(bd.getPartidos(diasPartidos, ligaselect, true));
		} catch (SQLException e) {
			throw new ManteniException(3, e);
		}
	}
	public void newApuesta(int idUsuario, int idPartido, int tipo, Double apostado, Double coef) throws ManteniException{
		try {
			bd.crearApuesta(idUsuario, idPartido, tipo, apostado, coef);
		} catch (SQLException e) {
			throw new ManteniException(4, e);
		}
	}
	public void loadEstadisticas() throws ManteniException{
		Estadisticas estadis = new Estadisticas();
		try{
			estadis.setGanadas(bd.getApuestasGanadas(idUserLogin));
			estadis.setPendientes(bd.getApuestasPendientes(idUserLogin));
			estadis.setPerdidas(bd.getApuestasPerdidas(idUserLogin)); 
			estadisChart = estadis.estadisToPie();
			if(estadis.ganadas >=1){
				serial.setArgiak();
			}
		}catch(SQLException e){
			throw new ManteniException(3, e);
		}
	}
	public ObservableList<PieChart.Data> getEstadisticasUser(){
		return estadisChart;
	}
	public void setPartidoApuesta(Partido p){
		partidoApuesta.set(p);
	}
	public Partido getPartidoApuesta(){
		return partidoApuesta.get();
	}
	public int loginuser(String user, String pass) throws ManteniException{
			try{
			idUserLogin = bd.loginUser(user, pass);
			}catch(SQLException e){
				throw new ManteniException(2, e);
			}
		return idUserLogin;
	}
	public void setDestLogin(final int i){
		destLogin = i;
	}
	public int getDestLogin(){
		return destLogin;
	}
	public int getIdUserLogin(){
		return idUserLogin;
	}
	public void addUser(String user, String pass, String nombre, String apellido, String email, int idal) throws ManteniException{
		try {
			bd.addUser(user, nombre, apellido, email, pass, idal);
		} catch (SQLException e) {
			throw new ManteniException(3, e);
		}
	}
	public Apuesta getApuestaInProgress(){
		return apuestaInProgress;
	}
	public Double getDirua(){
		return dirua.get();
	}
	public DoubleProperty getDiruaProperty(){
		return dirua;
	}
	public void setDirua(Double d){
	
	}
}
