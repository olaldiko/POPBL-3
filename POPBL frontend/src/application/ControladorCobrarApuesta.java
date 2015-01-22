package application;

import java.net.URL;
import java.util.ResourceBundle;

import datos.ModeloApuestas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 * Controlador para la pagina que mustra el dinero cobrado por el usuario
 * @author gorkaolalde
 *
 */
public class ControladorCobrarApuesta implements ControlledScreen, Initializable{
	ScreensController myController;
	ModeloApuestas modelo;
	Double dinero = 0.0;
    @FXML
    private Button btnatzera = new Button();

    @FXML
    private Label labelDiru = new Label();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
			dinero = modelo.cobrarApuestas();
			labelDiru.setText(String.format("%.2f", dinero)+" €");
			modelo.initApuestasUser();
		} catch (ManteniException e) {

		}
		btnatzera.setOnAction(event -> goToMisApuestas());
	}

	private void goToMisApuestas() {
		myController.setScreen("apostuak");
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

}
