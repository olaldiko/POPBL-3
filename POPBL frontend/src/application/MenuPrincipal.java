package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class MenuPrincipal implements Initializable, ControlledScreen{
	ScreensController controlador;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		controlador = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
