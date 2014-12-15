package datos;


public class Apuesta {
int idApuesta, idUsuario, idPartido;
Double premio, apostado, coeficiente;
Partido partido;
boolean cobrado;

	public Partido getPartido() {
		return partido;
	}
	
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	
	public void setIdApuesta(int idApuesta) {
		this.idApuesta = idApuesta;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void setIdPartido(int idPartido) {
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
