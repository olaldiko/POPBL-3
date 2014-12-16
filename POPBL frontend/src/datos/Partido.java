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
IntegerProperty idPartido, idJornada, golesLocal, golesVisitante;
Equipo local, visitante;
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
public Equipo getLocal(){
	return local;
}
public void setLocal(Equipo local){
	this.local = local;
}
public Equipo getVisitante(){
	return visitante;
}
public void setVisitante(Equipo visitante){
	this.visitante = visitante;
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
