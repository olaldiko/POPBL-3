package datos;

import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partido {
IntegerProperty idPartido, idJornada, golesLocal, golesVisitante, idLocal, idVisitante;
StringProperty local, visitante;
Date fecha;
BooleanProperty jugado;
DoubleProperty coefLocal, coefVisitante, coefEmpate;


public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}

public int getIdPartido() {
	return idPartido.get();
}
public void setIdPartido(int idPartido) {
	this.idPartido = new SimpleIntegerProperty(idPartido);
}
public int getIdJornada() {
	return idJornada.get();
}
public void setIdJornada(int idJornada) {
	this.idJornada = new SimpleIntegerProperty(idJornada);
}
public int getGolesLocal() {
	return golesLocal.get();
}
public void setGolesLocal(int golesLocal) {
	this.golesLocal = new SimpleIntegerProperty(golesLocal);
}
public int getGolesVisitante() {
	return golesVisitante.get();
}
public void setGolesVisitante(int golesVisitante) {
	this.golesVisitante = new SimpleIntegerProperty(golesVisitante);
}
public int getIdLocal() {
	return idLocal.get();
}
public void setIdLocal(int idLocal) {
	this.idLocal = new SimpleIntegerProperty(idLocal);
}
public int getIdVisitante() {
	return idVisitante.get();
}
public void setIdVisitante(int idVisitante) {
	this.idVisitante = new SimpleIntegerProperty(idVisitante);
}
public String getLocal() {
	return local.get();
}
public void setLocal(String local) {
	this.local = new SimpleStringProperty(local);
}
public String getVisitante() {
	return visitante.get();
}
public void setVisitante(String visitante) {
	this.visitante = new SimpleStringProperty(visitante);
}
public boolean isJugado() {
	return jugado.get();
}
public void setJugado(boolean jugado) {
	this.jugado = new SimpleBooleanProperty(jugado);
}
public Double getCoefLocal() {
	return coefLocal.get();
}
public void setCoefLocal(Double coefLocal) {
	this.coefLocal = new SimpleDoubleProperty(coefLocal);
}
public Double getCoefVisitante() {
	return coefVisitante.get();
}
public void setCoefVisitante(Double coefVisitante) {
	this.coefVisitante = new SimpleDoubleProperty(coefVisitante);
}
public Double getCoefEmpate() {
	return coefEmpate.get();
}
public void setCoefEmpate(Double coefEmpate) {
	this.coefEmpate = new SimpleDoubleProperty(coefEmpate);
}

}
