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


public class ControladorNewApuesta implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	DoubleProperty apostado = new SimpleDoubleProperty(0.0f);
	DoubleProperty premio = new SimpleDoubleProperty(0.0f);
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
			btnMas.setOnAction(event -> sumaApostado());
			btnMenos.setOnAction(event -> restaApostado());
			logoLocal.setImage(new Image(modelo.getPartidoApuesta().getLocal().getEscudo().toString()));
			logoVisitante.setImage(new Image(modelo.getPartidoApuesta().getVisitante().getEscudo().toString()));
			labelLocal.setText(modelo.getPartidoApuesta().getLocal().getNombre());
			labelVisitante.setText(modelo.getPartidoApuesta().getVisitante().getNombre());
			botonAp1.setText(modelo.getPartidoApuesta().getCoefLocal().toString());
			botonApX.setText(modelo.getPartidoApuesta().getCoefEmpate().toString());
			botonAp2.setText(modelo.getPartidoApuesta().getCoefVisitante().toString());
			botonAp1.setOnAction(event -> actualizaPremio());
			botonApX.setOnAction(event -> actualizaPremio());
			botonAp2.setOnAction(event -> actualizaPremio());
		}catch(ManteniException e){
			
		}
	}
	private void restaApostado() {
		if(apostado.get() >= 1.0) apostado.set(apostado.get() -1.0);
			diruField.setText(Double.toString(apostado.get()));
			actualizaPremio();
			
	}
	private void sumaApostado() {
		apostado.set(apostado.get() + 1.0);
		diruField.setText(Double.toString(apostado.get()));
		actualizaPremio();
	}
	private void goToPrincipal() {
		myController.setScreen("principal");
	}
	private void actualizaPremio(){
		if(botonAp1.isSelected()){
			premio.set(apostado.get()*modelo.getPartidoApuesta().getCoefLocal());
			premioLabel.setText(Double.toString(premio.get()));
		}else{
			if(botonApX.isSelected()){
				premio.set(apostado.get()*modelo.getPartidoApuesta().getCoefEmpate());
				premioLabel.setText(Double.toString(premio.get()));
			}else{
				if(botonAp2.isSelected()){
					premio.set(apostado.get()*modelo.getPartidoApuesta().getCoefVisitante());
					premioLabel.setText(Double.toString(premio.get()));
				}else{
					premio.set(0.0);
					premioLabel.setText(Double.toString(premio.get()));
				}
			}
		}
	}
	
}
