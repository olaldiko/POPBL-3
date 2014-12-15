package datos;

import java.util.Date;

public class Partido {
int idPartido, idJornada, GolesLocal, GolesVisitante, idLocal, idVisitante;
String local, visitante;
Date fecha;
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
boolean jugado;
Double coefLocal, coefVisitante, coefEmpate;
public int getIdPartido() {
	return idPartido;
}
public void setIdPartido(int idPartido) {
	this.idPartido = idPartido;
}
public int getIdJornada() {
	return idJornada;
}
public void setIdJornada(int idJornada) {
	this.idJornada = idJornada;
}
public int getGolesLocal() {
	return GolesLocal;
}
public void setGolesLocal(int golesLocal) {
	GolesLocal = golesLocal;
}
public int getGolesVisitante() {
	return GolesVisitante;
}
public void setGolesVisitante(int golesVisitante) {
	GolesVisitante = golesVisitante;
}
public int getIdLocal() {
	return idLocal;
}
public void setIdLocal(int idLocal) {
	this.idLocal = idLocal;
}
public int getIdVisitante() {
	return idVisitante;
}
public void setIdVisitante(int idVisitante) {
	this.idVisitante = idVisitante;
}
public String getLocal() {
	return local;
}
public void setLocal(String local) {
	this.local = local;
}
public String getVisitante() {
	return visitante;
}
public void setVisitante(String visitante) {
	this.visitante = visitante;
}
public boolean isJugado() {
	return jugado;
}
public void setJugado(boolean jugado) {
	this.jugado = jugado;
}
public Double getCoefLocal() {
	return coefLocal;
}
public void setCoefLocal(Double coefLocal) {
	this.coefLocal = coefLocal;
}
public Double getCoefVisitante() {
	return coefVisitante;
}
public void setCoefVisitante(Double coefVisitante) {
	this.coefVisitante = coefVisitante;
}
public Double getCoefEmpate() {
	return coefEmpate;
}
public void setCoefEmpate(Double coefEmpate) {
	this.coefEmpate = coefEmpate;
}

}
