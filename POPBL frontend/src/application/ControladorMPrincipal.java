package application;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import datos.Liga;
import datos.ModeloApuestas;
import datos.Partido;

public class ControladorMPrincipal implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	
	
	@FXML
	Button btnapostuak = new Button();
	@FXML
	Button btnerreg = new Button();
	@FXML
	Button btnemaitzak = new Button();
	@FXML
	ComboBox<Liga> ligaselect = new ComboBox<Liga>();
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
		try {
			modelo = ModeloApuestas.getInstance();
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnapostuak.setOnAction(event -> goToApostuak());
		btnerreg.setOnAction(event -> goToErregistroa());
		btnemaitzak.setOnAction(event -> goToEmaitzak());
		ligaselect.setOnAction(event -> cambioLigas());
		
		tablapartidos.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> nuevaApuesta(newValue));
		colFecha.setCellValueFactory(cellData -> cellData.getValue().getFechaProperty());
		colLocal.setCellValueFactory(cellData -> cellData.getValue().getLocal().getNombreProperty());
		colVisit.setCellValueFactory(cellData -> cellData.getValue().getVisitante().getNombreProperty());
		//Por alguna extrana razon no se puede pasar directamente doublepropertys, hay que usar.asobject para que tire
		col1.setCellValueFactory(cellData -> cellData.getValue().getCoefLocalProperty().asObject());
		colx.setCellValueFactory(cellData -> cellData.getValue().getCoefEmpateProperty().asObject());
		col2.setCellValueFactory(cellData -> cellData.getValue().getCoefVisitanteProperty().asObject());
		
		
		ligaselect.setItems(modelo.getLigas());
		tablapartidos.setItems(modelo.getPartidosprincipal());
		
	}
	public void cambioLigas(){
		try{
			modelo.updatePartidosPr(ligaselect.getSelectionModel().getSelectedItem().getIdLiga());
		}catch(ManteniException e){
			
		}
		
	}
	public void cargaDatos(){
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
	public void nuevaApuesta(Partido p){
		modelo.setPartidoApuesta(p);
		myController.setScreen("newApuesta");
	}
}

