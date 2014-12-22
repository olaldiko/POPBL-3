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
		
		user = erabiltzailefield.getText();
		pass = passfield.getText();
		try{
			if(modelo.loginuser(user, pass)){
				System.out.println("Entra");
				erabiltzailefield.setText("");
				passfield.setText("");
				loginlabel.setText("Mesedez, sartu zure erabiltzaile eta pasahitza");
				myController.setScreenNoTrans("apostuak");
				myController.removeScreenOverlay("login");
			}else{
				loginlabel.setText("Pasahitza gaizki sartu duzu, saiatu berriz");
				loginlabel.setTextFill(Color.RED);
			}
		}catch(ManteniException e){
			
		}
		
	}
	private void goToBack() {
		loginlabel.setText("Mesedez, sartu zure erabiltzaile eta pasahitza");
		loginlabel.setTextFill(Color.BLACK);
		myController.removeScreenOverlay("login");
	}

	
}
