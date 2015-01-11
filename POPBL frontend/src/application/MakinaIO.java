package application;

import javafx.beans.Observable;
import jssc.SerialPortException;

public interface MakinaIO extends Observable{

	public Double getDirua();
	public void setArgiak() throws SerialPortException;
}
