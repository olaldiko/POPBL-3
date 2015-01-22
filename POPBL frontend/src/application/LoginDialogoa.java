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

/**
 * Controlador para el dialogo de login de la aplicacion
 * @author gorkaolalde
 *
 */
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
	/**
	 * Comprueba el usuario en la base de datos, y segun el destino elegido, va a una pantalla o otra. Si el login esta mal, cambia las letras a rojo
	 */
	private void commitLogin() {
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
				if(modelo.getDestLogin() == ModeloApuestas.DEST_NIREAPOSTUAK){
					modelo.initApuestasUser();
					myController.loadScreen(ScreensFramework.Mapostuak, ScreensFramework.Mapostuak_FXML);
					myController.removeScreenOverlay("login");
					myController.setScreen("apostuak");
				}else{
					if(modelo.getDestLogin() == ModeloApuestas.DEST_APOSTUBERRI){
						modelo.getApuestaInProgress().setIdUsuario(idUser);
						myController.loadScreen(ScreensFramework.InsertDiru, ScreensFramework.InsertDiru_FXML);
						myController.removeScreenOverlay("login");
						myController.setScreen("insertDiru");
					}
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
