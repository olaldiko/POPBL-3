package datos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Liga {
	IntegerProperty idLiga;
	StringProperty nombre;
	
	public Liga(){
		idLiga = new SimpleIntegerProperty();
		nombre = new SimpleStringProperty();
	}
	
	public int getIdLiga() {
		return idLiga.get();
	}
	public IntegerProperty getIdLigaProperty(){
		return idLiga;
	}
	public void setIdLiga(int idLiga) {
		this.idLiga.set(idLiga);
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
	public String toString(){
		return nombre.get();
	}
}
