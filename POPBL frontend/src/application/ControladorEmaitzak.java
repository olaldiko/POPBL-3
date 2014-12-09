package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ControladorEmaitzak implements Initializable, ControlledScreen {
	ScreensController myController;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	
	@FXML
	Button btnatzera = new Button();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnatzera.setOnAction(event -> goToPrincipal());
		
	}
	public void goToPrincipal(){
		myController.setScreen("principal");
	}
}
