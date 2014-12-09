package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ControladorMPrincipal implements Initializable, ControlledScreen {
	ScreensController myController;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	
	
	@FXML
	Button btnapostuak = new Button();
	@FXML
	Button btnerreg = new Button();
	@FXML
	Button btnemaitzak = new Button();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnapostuak.setOnAction(event -> goToApostuak());
		btnerreg.setOnAction(event -> goToErregistroa());
		btnemaitzak.setOnAction(event -> goToEmaitzak());
		
		
	}
	public void goToEmaitzak() {
		myController.setScreen("emaitzak");
	}
	public void goToErregistroa(){
		myController.setScreen("erregistroa");
	}
	public void goToApostuak(){
		myController.setScreen("apostuak");
	}

}

