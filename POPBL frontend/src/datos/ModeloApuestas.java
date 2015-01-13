package datos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
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
	private final String urlBase = "192.168.1.210";
	private final int puertoBase = 3306;
	private final String baseBase = "mordorbet";
	private final String userBase = "frontend";
	private final String passBase = "frontend";
	private SQLFrontEnd bd;
	private ServicioUpdate servicio = new ServicioUpdate();
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
	private final String puertoserie = "/dev/tty.usbmodem1411";
	private SerialIO serial;
	
	//Login
	int destLogin = 0;
	public static final int DEST_NIREAPOSTUAK = 0;
	public static final int DEST_APOSTUBERRI = 1;
	int idUserLogin = -1;
	


	
	private ModeloApuestas() throws ManteniException{
		try{
			bd = new SQLFrontEnd(urlBase, puertoBase , baseBase, userBase, passBase);
			this.initLigas();
			this.initPartidosPr();
			this.initPartidosEmaitzak();
			this.initSerial();
			this.serial.bindDirua(dirua);
			this.servicio.setDelay(Duration.seconds(60));
			this.servicio.setPeriod(Duration.seconds(60));
			this.servicio.start();
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
	
	
	/*Funciones para inicializar todos los datos
	 * 
	 * 
	 * 
	 * 
	 */
	public void initSerial() throws ManteniException{
		try {
			serial = new SerialIO(puertoserie);
		} catch (SerialPortException e) {
			throw new ManteniException(5, e);
		}
		
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

	public void initApuestasUser() throws ManteniException{
		try {
			this.apuestasuser = bd.getApuestas(idUserLogin);
		} catch (SQLException e) {
			throw new ManteniException(3, e);
		}
		
	}

	/*Funciones para actualizacion de datos
	 * 
	 * 
	 */
	public void updatePartidosPr(int ligaselect) throws ManteniException{
		try {
			this.partidosprincipal.setAll(bd.getPartidos(diasPartidos, ligaselect, false));
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
	public void confirmApuesta() throws ManteniException{
		int idApuesta = 0;
		try {
			idApuesta = bd.crearApuesta(apuestaInProgress.getIdUsuario(), apuestaInProgress.partido.get().getIdPartido(), apuestaInProgress.getTipoApuesta(), apuestaInProgress.getApostado(), apuestaInProgress.getCoeficiente(), apuestaInProgress.getPremio());
			apuestaInProgress.setIdApuesta(idApuesta);
		} catch (SQLException e) {
			throw new ManteniException(4, e);
		}
	}
	/*Funciones para seccion misApuestas
	 * 
	 * 
	 */
	public ObservableList<PieChart.Data> getEstadisticasUser(){
		return estadisChart;
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
		} catch (SerialPortException e) {
			throw new ManteniException(5, e);
		}
	}
	public void setPartidoApuesta(Partido p){
		partidoApuesta.set(p);
	}
	public Partido getPartidoApuesta(){
		return partidoApuesta.get();
	}
	public Double cobrarApuestas() throws ManteniException{
		ArrayList<Apuesta> a = new ArrayList<>();
		Double dinero = 0.0;
		for(Apuesta i : apuestasuser){
			if(i.isGanado() && !i.isCobrado()){
				a.add(i);
			}
		}
		try {
			dinero = bd.cobrarApuestas(a);
		} catch (SQLException e) {
			throw new ManteniException(4, e);
		}
		return dinero;
	}
	/*
	 * Funciones para seccion de login y creacion de user
	 */
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
	
	/*
	 * Funciones seccion nueva apuesta
	 */
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
		dirua.set(d);
	}
	public void setApuestaInProgress(Double apostado, int tipo){
		apuestaInProgress = new Apuesta();
		apuestaInProgress.setApostado(apostado);
		apuestaInProgress.setTipoApuesta(tipo);
		apuestaInProgress.setCobrado(false);
		apuestaInProgress.setIdPartido(partidoApuesta.get().getIdPartido());
		apuestaInProgress.setPartido(partidoApuesta.get());
		switch(tipo){
		case 1:
			apuestaInProgress.setCoeficiente(apuestaInProgress.partido.get().getCoefLocal());
			break;
		case 2:
			apuestaInProgress.setCoeficiente(apuestaInProgress.partido.get().getCoefEmpate());
			break;
		case 3:
			apuestaInProgress.setCoeficiente(apuestaInProgress.partido.get().getCoefVisitante());
			break;
		}
		apuestaInProgress.setPremio(apuestaInProgress.getCoeficiente()*apostado);
	}
}
