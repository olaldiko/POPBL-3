package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorErregistroa implements Initializable, ControlledScreen {
	ScreensController myController;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	@FXML
	Button btnatzera = new Button();
	@FXML
	Button btntxartela = new Button();
	@FXML
	Button btnsortu = new Button();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnatzera.setOnAction(event -> goToPrincipal());
		btnsortu.setOnAction(event -> showlogin());
	}
	public void goToPrincipal(){
		myController.setScreen("principal");
	}
	public void showlogin(){
		LoginDialogoa login = new LoginDialogoa();
		try {
			login.showLogin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}