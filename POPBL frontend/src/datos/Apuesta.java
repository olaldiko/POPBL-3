package datos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Apuesta {
IntegerProperty idApuesta, idUsuario, idPartido;
DoubleProperty premio, apostado, coeficiente;
Partido partido;
BooleanProperty cobrado;

	public Partido getPartido() {
		return partido;
	}
	
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	
	public void setIdApuesta(int idApuesta) {
		this.idApuesta = new SimpleIntegerProperty(idApuesta);
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = new SimpleIntegerProperty(idUsuario);
	}
	
	public void setIdPartido(int idPartido) {
		this.idPartido = new SimpleIntegerProperty(idPartido);
	}
	
	public Double getPremio() {
		return premio.get();
	}

	public void setPremio(Double premio) {
		this.premio = new SimpleDoubleProperty(premio);
	}

	public Double getApostado() {
		return apostado.get();
	}

	public void setApostado(Double apostado) {
		this.apostado = new SimpleDoubleProperty(apostado);
	}

	public Double getCoeficiente() {
		return coeficiente.get();
	}

	public void setCoeficiente(Double coeficiente) {
		this.coeficiente = new SimpleDoubleProperty(coeficiente);
	}

	public boolean isCobrado() {
		return cobrado.get();
	}

	public void setCobrado(boolean cobrado) {
		this.cobrado = new SimpleBooleanProperty(cobrado);
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
	
}