package datos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Clase que contendra todos los datos de una apuesta. Utiliza ObjectPropertys para guardar los datos.
 * @author gorkaolalde
 *
 */
public class Apuesta {
IntegerProperty idApuesta, idUsuario, idPartido, tipoApuesta;
DoubleProperty premio, apostado, coeficiente;
ObjectProperty<Partido> partido;
BooleanProperty cobrado, ganado, vigente;
	
	public Apuesta(){
		idApuesta = new SimpleIntegerProperty();
		idUsuario = new SimpleIntegerProperty();
		idPartido = new SimpleIntegerProperty();
		tipoApuesta = new SimpleIntegerProperty();
		premio = new SimpleDoubleProperty();
		apostado = new SimpleDoubleProperty();
		coeficiente = new SimpleDoubleProperty();
		partido = new SimpleObjectProperty<>();
		cobrado = new SimpleBooleanProperty();
		ganado = new SimpleBooleanProperty();
		vigente = new SimpleBooleanProperty();
	}
	public int getTipoApuesta() {
		return tipoApuesta.get();
	}
	
	public void setTipoApuesta(int tipoApuesta) {
		this.tipoApuesta.set(tipoApuesta);
	}
	public Partido getPartido() {
		return partido.get();
	}
	public ObjectProperty<Partido> getPartidoProperty(){
		return this.partido;
	}
	public void setPartido(Partido partido) {
		this.partido.set(partido);
	}
	
	public void setIdApuesta(int idApuesta) {
		this.idApuesta.set(idApuesta);
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario.set(idUsuario);
	}
	
	public void setIdPartido(int idPartido) {
		this.idPartido.set(idPartido);
	}
	
	public Double getPremio() {
		return premio.get();
	}
	public DoubleProperty getPremioProperty(){
		return premio;
	}
	public void setPremio(Double premio) {
		this.premio.set(premio);
	}

	public Double getApostado() {
		return apostado.get();
	}
	public DoubleProperty getApostadoProperty(){
		return apostado;
	}
	public void setApostado(Double apostado) {
		this.apostado.set(apostado);
	}

	public Double getCoeficiente() {
		return coeficiente.get();
	}
	public DoubleProperty getCoeficienteProperty(){
		return coeficiente;
	}
	public void setCoeficiente(Double coeficiente) {
		this.coeficiente.set(coeficiente);
	}
	
	public boolean isCobrado() {
		return cobrado.get();
	}
	public BooleanProperty getCobradoProperty(){
		return cobrado;
	}
	public void setCobrado(boolean cobrado) {
		this.cobrado.set(cobrado);
	}
	public boolean isVigente(){
		return vigente.get();
	}
	public BooleanProperty isVigenteProperty(){
		return vigente;
	}
	public void setVigente(boolean vigente){
		this.vigente.set(vigente);
	}
	public boolean isGanado(){
		return ganado.get();
	}
	public BooleanProperty isGanadoProperty(){
		return ganado;
	}
	public void setGanado(boolean ganado){
		this.ganado.set(ganado);
	}
	public int getIdApuesta() {
		return idApuesta.get();
	}

	public int getIdUsuario() {
		return idUsuario.get();
	}

	public int getIdPartido() {
		return idPartido.get();
	}
	public String getTipoString(){
		switch(tipoApuesta.get()){
		case 0:
			return "X";
		case 1:
			return "1";
		case 2:
			return "2";
		}
		return "N/D";
	}
}
