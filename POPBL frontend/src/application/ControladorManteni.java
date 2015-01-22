package application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
/**
 * Controlador que mostrara la pantalla que aparece al ocurrir una excepcion
 * @author gorkaolalde
 *
 */
public class ControladorManteni implements Initializable, ControlledScreen{
	ScreensController myController;
	StringWriter sw = new StringWriter();
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
		
	}
	/**
	 * Copia el stacktrace a string y muestra los datos en el TextFlow
	 */
	private void mostrarDatos() {
		excepcion.printStackTrace(new PrintWriter(sw));
		errorText.setText(sw.toString());
		errorFlow.setOpacity(1.0);
		errorText.setOpacity(1.0);
	}

	public static void setException(ManteniException e){
		excepcion = e;
		
	}
}
