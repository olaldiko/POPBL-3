package datos;

import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partido {
IntegerProperty idPartido, idJornada, golesLocal, golesVisitante;
ObjectProperty<Equipo> local, visitante;
ObjectProperty<Date> fecha;
BooleanProperty jugado;
DoubleProperty coefLocal, coefVisitante, coefEmpate;

public Partido(){
	idPartido = new SimpleIntegerProperty();
	idJornada = new SimpleIntegerProperty();
	golesLocal = new SimpleIntegerProperty();
	golesVisitante = new SimpleIntegerProperty();
	local = new SimpleObjectProperty<Equipo>();
	visitante = new SimpleObjectProperty<Equipo>();
	fecha = new SimpleObjectProperty<Date>();
	jugado = new SimpleBooleanProperty();
	coefLocal = new SimpleDoubleProperty();
	coefVisitante = new SimpleDoubleProperty();
	coefEmpate = new SimpleDoubleProperty();
	local.set(new Equipo());
	visitante.set(new Equipo());
}


public Date getFecha() {
	return fecha.get();
}
public ObjectProperty<Date> getFechaProperty(){
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha.set(fecha);
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
public IntegerProperty getGolesLocalProperty(){
	return golesLocal;
}
public void setGolesLocal(int golesLocal) {
	this.golesLocal = new SimpleIntegerProperty(golesLocal);
}


public int getGolesVisitante() {
	return golesVisitante.get();
}
public IntegerProperty getGolesVisitanteProperty() {
	return golesVisitante;
}
public void setGolesVisitante(int golesVisitante) {
	this.golesVisitante = new SimpleIntegerProperty(golesVisitante);
}


public Equipo getLocal(){
	return local.get();
}
public ObjectProperty<Equipo> getLocalProperty(){
	return local;
}
public void setLocal(Equipo local){
	this.local.set(local);
}


public Equipo getVisitante(){
	return visitante.get();
}
public ObjectProperty<Equipo> getVisitanteProperty(){
	return visitante;
}
public void setVisitante(Equipo visitante){
	this.visitante.set(visitante);
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
public DoubleProperty getCoefLocalProperty(){
	return coefLocal;
}
public void setCoefLocal(Double coefLocal) {
	this.coefLocal = new SimpleDoubleProperty(coefLocal);
}


public Double getCoefVisitante() {
	return coefVisitante.get();
}
public DoubleProperty getCoefVisitanteProperty(){
	return coefVisitante;
}
public void setCoefVisitante(Double coefVisitante) {
	this.coefVisitante = new SimpleDoubleProperty(coefVisitante);
}


public Double getCoefEmpate() {
	return coefEmpate.get();
}
public DoubleProperty getCoefEmpateProperty(){
	return coefEmpate;
}
public void setCoefEmpate(Double coefEmpate) {
	this.coefEmpate = new SimpleDoubleProperty(coefEmpate);
}

}
