package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

public class ControladorManteni implements Initializable, ControlledScreen{
	ScreensController myController;
	static ManteniException excepcion;
	
	@FXML
	Button btnDatos = new Button();
	@FXML
	Label errorLabel = new Label();
	@FXML 
	TextFlow errorFlow = new TextFlow();
	@FXML
	TextArea errorText = new TextArea();
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnDatos.setOnAction(event -> mostrarDatos());
		errorLabel.setText(Integer.toString(excepcion.tipo));
		errorText.setText(excepcion.getStackTrace().toString());
	}
	private void mostrarDatos() {
		errorFlow.setOpacity(1.0);
		errorText.setOpacity(1.0);
	}

	public static void setException(ManteniException e){
		excepcion = e;
	}
}
