package application;

import javafx.beans.Observable;

public interface MakinaIO extends Observable{

	public Double getDirua();
	public void setArgiak() throws ManteniException;
}
