package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import datos.ModeloApuestas;
/**
 * Controlador para la pagina de nueva apuesta de la aplicacion
 * @author gorkaolalde
 *
 */

public class ControladorNewApuesta implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	DoubleProperty apostado = new SimpleDoubleProperty(0.0f);
	DoubleProperty premio = new SimpleDoubleProperty(0.0f);
	int tipo = -1;
	Double coef = 0.0;
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
	Label labelLocal = new Label();
	@FXML
	Label labelVisitante = new Label();
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
			btnAtzera.setOnAction(event -> goToPrincipal());
			btnJarraitu.setOnAction(event -> commitApuesta());
			btnMas.setOnAction(event -> sumaApostado());
			btnMenos.setOnAction(event -> restaApostado());
			logoLocal.setImage(new Image(modelo.getPartidoApuesta().getLocal().getEscudo().toString()));
			logoVisitante.setImage(new Image(modelo.getPartidoApuesta().getVisitante().getEscudo().toString()));
			labelLocal.setText(modelo.getPartidoApuesta().getLocal().getNombre());
			labelVisitante.setText(modelo.getPartidoApuesta().getVisitante().getNombre());
			botonAp1.setText(String.format("%.2f", modelo.getPartidoApuesta().getCoefLocal()));
			botonApX.setText(String.format("%.2f", modelo.getPartidoApuesta().getCoefEmpate()));
			botonAp2.setText(String.format("%.2f", modelo.getPartidoApuesta().getCoefVisitante()));
			botonAp1.setOnAction(event -> actualizaPremio());
			botonApX.setOnAction(event -> actualizaPremio());
			botonAp2.setOnAction(event -> actualizaPremio());
		}catch(ManteniException e){
			e.printStackTrace();
		}
	}
	/**
	 * Llama al login, selecciona su destino y crea la apuestaInProgress de ModeloApuestas
	 */
	private void commitApuesta() {
		if(tipo != -1){
			modelo.setDestLogin(ModeloApuestas.DEST_APOSTUBERRI);
			modelo.setApuestaInProgress(apostado.get(), tipo);
			if(!myController.isScreenLoaded("login")){
				myController.loadScreen(ScreensFramework.Login, ScreensFramework.Login_FXML);
			}
			myController.setScreenOverlay("login");
		}
		
		
	}
	/**
	 * Resta dinero de la apuesta
	 */
	private void restaApostado() {
		if(apostado.get() >= 1.0) apostado.set(apostado.get() -1.0);
			diruField.setText(String.format("%.2f", apostado.get()));
			actualizaPremio();
			
	}
	/**
	 * Suma dinero a la apuesta
	 */
	private void sumaApostado() {
		apostado.set(apostado.get() + 1.0);
		diruField.setText(String.format("%.2f", apostado.get()));
		actualizaPremio();
	}
	private void goToPrincipal() {
		myController.setScreen("principal");
	}
	/**
	 * Actualiza el premio segun la apuesta y cantidad seleccionadas
	 */
	private void actualizaPremio(){
		if(botonAp1.isSelected()){
			tipo = 1;
			coef = modelo.getPartidoApuesta().getCoefLocal();
			premio.set(apostado.get()*coef);
			premioLabel.setText(String.format("%.2f", premio.get()));
		}else{
			if(botonApX.isSelected()){
				tipo = 0;
				coef = modelo.getPartidoApuesta().getCoefEmpate();
				premio.set(apostado.get()*coef);
				premioLabel.setText(String.format("%.2f", premio.get()));
			}else{
				if(botonAp2.isSelected()){
					tipo = 2;
					coef = modelo.getPartidoApuesta().getCoefVisitante();
					premio.set(apostado.get()*coef);
					premioLabel.setText(String.format("%.2f", premio.get()));
				}else{
					tipo = -1;
					coef = 0.0;
					premio.set(0.0);
					premioLabel.setText(String.format("%.2f", premio.get()));
				}
			}
		}
	}
	
}
