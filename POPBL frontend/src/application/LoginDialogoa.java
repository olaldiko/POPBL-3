package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import datos.ModeloApuestas;


public class LoginDialogoa implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	String user, pass;
	@FXML
	PasswordField passfield = new PasswordField();
	@FXML
	TextField erabiltzailefield = new TextField();
	@FXML
	Button btnatzeralog = new Button();
	@FXML
	Button btnsartulog = new Button();
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
		} catch (ManteniException e) {
			// TODO: handle exception
		}
		btnatzeralog.setOnAction(event -> goToBack());
		btnsartulog.setOnAction(event -> commitLogin());
	}
	private void commitLogin() {
		//Hay que ver como mirar si va a entrar a sus apuestas o a hacer una nueva, por ahora probamos con que va a sus apuestas;
		
		user = erabiltzailefield.getText();
		pass = passfield.getText();
		try{
			if(modelo.loginuser(user, pass)){
				myController.setScreenNoTrans("apostuak");
				myController.removeScreenOverlay("login");
			}
		}catch(ManteniException e){
			
		}
		
	}
	private void goToBack() {
		myController.removeScreenOverlay("login");
	}

	
}
