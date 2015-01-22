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
import datos.ModeloApuestas;
/**
 * Controlador para la pagina de registro de usuario
 * @author gorkaolalde
 *
 */
public class ControladorErregistroa implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	
	String user, pass, nombre, apellido, email;
	int idal = 0;
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
    @FXML
    TextField izenaField = new TextField();

    @FXML
    PasswordField passField = new PasswordField();

    @FXML
    Button btnatzera = new Button();

    @FXML
    Button btntxartela = new Button();

    @FXML
    TextField userField = new TextField();

    @FXML
    Button btnsortu = new Button();

    @FXML
    TextField abizenaField = new TextField();

    @FXML
    TextField emailField = new TextField();
    @FXML
    Label textPrincipal = new Label();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnatzera.setOnAction(event -> goToPrincipal());
		btnsortu.setOnAction(event -> addUser());
	}
	public void goToPrincipal(){
		textPrincipal.setText("Mesedez, sartu zure datuak kontu bat sortzeko:");
		textPrincipal.setTextFill(Color.WHITE);
		userField.setText("");
		passField.setText("");
		izenaField.setText("");
		abizenaField.setText("");
		emailField.setText("");
		myController.setScreen("principal");
	}
	public void showlogin(){
		myController.setScreenOverlay("login");
	}
	/**
	 * Manda anadir el usuario si este ha anadido todos los datos, sino cambia las letras a rojo y modifica el mensaje
	 */
	public void addUser(){
		user = userField.getText();
		pass = passField.getText();
		nombre = izenaField.getText();
		apellido = abizenaField.getText();
		email = emailField.getText();
		if(user.isEmpty() || pass.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()){
			textPrincipal.setText("Mesedez, bete itzazu datu guztiak");
			textPrincipal.setTextFill(Color.RED);
		}else{
			try{
				modelo.addUser(user, pass, nombre, apellido, email, idal);
				textPrincipal.setText("Zure kontua ondo sortu da.");
				textPrincipal.setTextFill(Color.GREEN);
			}catch(ManteniException e){
				e.printStackTrace();
			}
		}
	}
}