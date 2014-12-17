package application;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import datos.ModeloApuestas;
import datos.Partido;

public class ControladorMPrincipal implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
		modelo = screenPage.getModelo();

	}
	
	
	@FXML
	Button btnapostuak = new Button();
	@FXML
	Button btnerreg = new Button();
	@FXML
	Button btnemaitzak = new Button();
	@FXML
	TableView<Partido> tablapartidos = new TableView<Partido>();
	@FXML
	TableColumn<Partido, Date> colFecha;
	@FXML
	TableColumn<Partido, String> colLocal;
	@FXML
	TableColumn<Partido, String> colVisit;
	@FXML
	TableColumn<Partido, Double> col1;
	@FXML
	TableColumn<Partido, Double> colx;
	@FXML
	TableColumn<Partido, Double> col2;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnapostuak.setOnAction(event -> goToApostuak());
		btnerreg.setOnAction(event -> goToErregistroa());
		btnemaitzak.setOnAction(event -> goToEmaitzak());
		colFecha.setCellValueFactory(cellData -> cellData.getValue().getFechaProperty());
		colLocal.setCellValueFactory(cellData -> cellData.getValue().getLocal().getNombreProperty());
		colVisit.setCellValueFactory(cellData -> cellData.getValue().getVisitante().getNombreProperty());
		//Por alguna extrana razon no se puede pasar directamente doublepropertys, hay que usar.asobject para que tire
		col1.setCellValueFactory(cellData -> cellData.getValue().getCoefLocalProperty().asObject());
		colx.setCellValueFactory(cellData -> cellData.getValue().getCoefEmpateProperty().asObject());
		col2.setCellValueFactory(cellData -> cellData.getValue().getCoefVisitanteProperty().asObject());
		tablapartidos.setItems(modelo.getPartidosprincipal());
	}
	public void goToEmaitzak() {
		myController.setScreen("emaitzak");
	}
	public void goToErregistroa(){
		myController.setScreen("erregistroa");
	}
	public void goToApostuak(){
		myController.setScreen("apostuak");
	}

}

