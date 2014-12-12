package application;

public class Apuesta {
int idApuesta, idUsuario, idPartido;
Double premio, apostado, coeficiente;
boolean cobrado;

	public Apuesta(int idApuesta, int idUsuario, int idPartido){
		this.idApuesta = idApuesta;
		this.idUsuario = idUsuario;
		this.idPartido = idPartido;
	}

	public Double getPremio() {
		return premio;
	}

	public void setPremio(Double premio) {
		this.premio = premio;
	}

	public Double getApostado() {
		return apostado;
	}

	public void setApostado(Double apostado) {
		this.apostado = apostado;
	}

	public Double getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(Double coeficiente) {
		this.coeficiente = coeficiente;
	}

	public boolean isCobrado() {
		return cobrado;
	}

	public void setCobrado(boolean cobrado) {
		this.cobrado = cobrado;
	}

	public int getIdApuesta() {
		return idApuesta;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public int getIdPartido() {
		return idPartido;
	}
	
}
