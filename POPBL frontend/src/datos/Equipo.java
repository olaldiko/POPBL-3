package datos;

import java.net.URL;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que contendra todos los datos de los equipos. Utiliza ObjectPropertys
 * @author gorkaolalde
 *
 */
public class Equipo {
IntegerProperty idEquipo;
StringProperty nombre, abreviatura;
ObjectProperty<URL> escudo;

public Equipo(){
	idEquipo = new SimpleIntegerProperty();
	nombre = new SimpleStringProperty();
	abreviatura = new SimpleStringProperty();
	escudo = new SimpleObjectProperty<URL>();
}

public int getIdEquipo() {
	return idEquipo.get();
}
public IntegerProperty getIdEquipoProperty(){
	return idEquipo;
}
public void setIdEquipo(int idEquipo) {
	this.idEquipo.set(idEquipo);
}

public String getNombre() {
	return nombre.get();
}
public StringProperty getNombreProperty(){
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre.set(nombre);
}
public String getAbreviatura() {
	return abreviatura.get();
}
public StringProperty getAbreviaturaProperty(){
	return abreviatura;
	
}
public void setAbreviatura(String abreviatura) {
	this.abreviatura.set(abreviatura);
}
public URL getEscudo() {
	return escudo.get();
}
public ObjectProperty<URL> getEscudoProperty(){
	return escudo;
}
public void setEscudo(URL escudo) {
	this.escudo.set(escudo);
}

}
