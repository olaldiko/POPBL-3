package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import datos.Apuesta;
import datos.ModeloApuestas;
/**
 * Controlador para la pagina que muestra el dinero apostado
 * @author gorkaolalde
 *
 */
public class ControladorInsertDiru implements ControlledScreen, Initializable{
	ScreensController myController;
	ModeloApuestas modelo;
	Apuesta apostua;
	//DoubleProperty dirua = new SimpleDoubleProperty();
	Double eskatutakoDirua;
    @FXML
    Button btnatzera = new Button();

    @FXML
    Label sartuaLabel = new Label();

    @FXML
    private ProgressIndicator diruProgress = new ProgressIndicator();

    @FXML
    private Label eskatuaLabel = new Label();
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			modelo = ModeloApuestas.getInstance();
			apostua = modelo.getApuestaInProgress();
			eskatutakoDirua = apostua.getApostado();
			eskatuaLabel.setText(String.format("%.2f", eskatutakoDirua));
			//modelo.activaDesactivaPlaca();
			modelo.setDirua(0.0);
			btnatzera.setOnAction(event -> goToBack());
			/**
			 * Anade un listener que mira si el dinero metido ha cambiado, y en caso de haber llegado a lo solicitado ejecuta commitApuesta()
			 */
			modelo.getDiruaProperty().addListener(new ChangeListener<Number>(){
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
							System.out.println("Entra en changeListener: "+newValue);
							sartuaLabel.setText(String.format("%.2f", newValue));
							diruProgress.setProgress((Double)newValue/eskatutakoDirua);
							if((Double)newValue >= eskatutakoDirua){
								commitApuesta();
							}
					
						}
					}
			);
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void goToBack() {
		myController.setScreen("principal");
		modelo.setDirua(0.0);
	}
	/**
	 * Manda confirmar la apuesta y pasa a la pagina siguiente
	 */
	private void commitApuesta() {
		try {
			modelo.confirmApuesta();
			myController.loadScreen(ScreensFramework.ConfirmApuesta, ScreensFramework.ConfirmApuesta_FXML);
			myController.setScreen("confirmApuesta");
		//	modelo.activaDesactivaPlaca();
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
