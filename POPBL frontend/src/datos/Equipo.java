package datos;

import java.net.URL;


public class Equipo {
int idEquipo;
String nombre, abreviatura;
URL escudo;
public int getIdEquipo() {
	return idEquipo;
}
public void setIdEquipo(int idEquipo) {
	this.idEquipo = idEquipo;
}

public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getAbreviatura() {
	return abreviatura;
}
public void setAbreviatura(String abreviatura) {
	this.abreviatura = abreviatura;
}
public URL getEscudo() {
	return escudo;
}
public void setEscudo(URL escudo) {
	this.escudo = escudo;
}

}
