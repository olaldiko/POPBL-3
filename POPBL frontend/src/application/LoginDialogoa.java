package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
	@FXML
	Label loginlabel = new Label();
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
		passfield.setEditable(true);
		erabiltzailefield.setEditable(true);
	}
	private void commitLogin() {
		//Hay que ver como mirar si va a entrar a sus apuestas o a hacer una nueva, por ahora probamos con que va a sus apuestas;
		int idUser = -1;
		user = erabiltzailefield.getText();
		pass = passfield.getText();
		System.out.println("Entra");
		try{
			if(((user.compareTo("")!= 0) && (pass.compareTo("") != 0))){
			idUser = modelo.loginuser(user, pass);
			}
			if(idUser != -1){
				System.out.println("Entra loginok");
				erabiltzailefield.setText("");
				passfield.setText("");
				loginlabel.setText("Mesedez, sartu zure erabiltzaile eta pasahitza");
				if(modelo.getDestLogin() == modelo.DEST_NIREAPOSTUAK){
					modelo.initApuestasUser();
					myController.loadScreen(ScreensFramework.Mapostuak, ScreensFramework.Mapostuak_FXML);
					myController.removeScreenOverlay("login");
					myController.setScreen("apostuak");
				}else{
					
					myController.removeScreenOverlay("login");
				}
				
				//myController.unloadScreen("login");
			}else{
				loginlabel.setText("Pasahitza gaizki sartu duzu, saiatu berriz");
				loginlabel.setTextFill(Color.RED);
				erabiltzailefield.setText("");
				passfield.setText("");
			}
		}catch(ManteniException e){
			
		}
		
	}
	private void goToBack() {
		loginlabel.setText("Mesedez, sartu zure erabiltzaile eta pasahitza");
		loginlabel.setTextFill(Color.BLACK);
		myController.removeScreenOverlay("login");
		myController.unloadScreen("login");
	}

	
}
