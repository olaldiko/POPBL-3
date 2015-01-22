package application;

import javafx.beans.Observable;
import jssc.SerialPortException;
/**
 * Interface que contiene las dos funciones minimas que deberia tener la clase para interactuar con la maquina
 * @author gorkaolalde
 *
 */
public interface MakinaIO extends Observable{

	public Double getDirua();
	public void setArgiak() throws SerialPortException;
}
