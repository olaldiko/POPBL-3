package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import datos.ModeloApuestas;


public class ControladorNewApuesta implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	
	@FXML
	Button btnAtzera = new Button();
	@FXML
	Button btnJarraitu = new Button();
	@FXML
	Button btnMas = new Button();
	@FXML
	Button btnMenos = new Button();
	@FXML
	Label premioLabel = new Label();
	@FXML
	ToggleButton botonAp1 = new ToggleButton();
	@FXML
	ToggleButton botonApX = new ToggleButton();
	@FXML
	ToggleButton botonAp2 = new ToggleButton();
	@FXML
	ImageView logoLocal = new ImageView();
	@FXML
	ImageView logoVisitante = new ImageView();
	@FXML
	TextField diruField = new TextField();
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			modelo = ModeloApuestas.getInstance();
			
		}catch(ManteniException e){
			
		}
	}
	
	
}
