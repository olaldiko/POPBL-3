package datos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Clase que contendra todos los datos sobre los usuarios
 * @author gorkaolalde
 *
 */
public class Usuario {
	int idUsuario;
	String nombre;
	SimpleStringProperty password;
	SimpleStringProperty correo;
	SimpleIntegerProperty idal;
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public void setCorreo(String correo){
		this.correo.set(correo);
	}
	public String getCorreo(){
		return correo.get();
	}
	public void setIdal(int idal){
		this.idal.set(idal);
	}
	public int getIdal(){
		return idal.get();
	}
}
