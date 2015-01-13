package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import datos.Apuesta;
import datos.ModeloApuestas;
import datos.Partido;

public class ControladorApostuak implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	ObservableList<Apuesta> lista;
	ObservableList<PieChart.Data> estadisUser;
	int idUser;
	@FXML
	TableColumn<Apuesta, Partido> partiduaCol = new TableColumn<>();
	@FXML
	TableColumn<Apuesta, Partido> emaitzaCol = new TableColumn<>();
	@FXML
	TableColumn<Apuesta, String> apostuaCol = new TableColumn<>();
	@FXML
	TableColumn<Apuesta, Double> sariaCol = new TableColumn<>();
	@FXML
	TableColumn<Apuesta, Boolean> jokatuaCol = new TableColumn<>();
	@FXML
	TableColumn<Apuesta, Boolean> irabaziaCol = new TableColumn<>();
	
	
	@FXML
	TableView<Apuesta> tablaApostuak = new TableView<>();
	@FXML
	PieChart estadisChart = new PieChart();

	@FXML
	Button btnatzera = new Button();
	@FXML
	Button btnkobratu = new Button();
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
			modelo.initApuestasUser();
			modelo.loadEstadisticas();
			lista = modelo.getApuestasuser();
			estadisUser = modelo.getEstadisticasUser();
			btnatzera.setOnAction(event -> goToPrincipal());
			btnkobratu.setOnAction(event -> goToCobrar());
			partiduaCol.setCellValueFactory(cellData -> cellData.getValue().getPartidoProperty());
			partiduaCol.setCellFactory(column -> {
				return new TableCell<Apuesta, Partido>(){
					protected void updateItem(Partido item, boolean empty){
						if(item == null || empty){
							setText("");
						}else{
							setText(item.getLocal().getNombre()+" - "+item.getVisitante().getNombre());
							//Hay que ver como se puede hacer para meter los escudos, tal vez haya que usar dos columnas para poder utilizar setGraphic
						}
					}
					};
				});
			emaitzaCol.setCellValueFactory(cellData -> cellData.getValue().getPartidoProperty());
			emaitzaCol.setCellFactory(column -> {
				return new TableCell<Apuesta, Partido>(){
					protected void updateItem(Partido item, boolean empty){
						if(item == null || empty){
							setText("");
						}else{
							if((item.getGolesLocal() == -1) || (item.getGolesVisitante() == -1)){
								setText("");
							}else{
								String golesLocal = String.valueOf(item.getGolesLocal());
								String golesVisit = String.valueOf(item.getGolesVisitante());
								setText(golesLocal+" - "+golesVisit);
							}
						}
					}
					};
				});
			apostuaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoString()));
			sariaCol.setCellValueFactory(cellData -> cellData.getValue().getPremioProperty().asObject());
			sariaCol.setCellFactory(column -> {
				return new TableCell<Apuesta, Double>(){
					protected void updateItem(Double item, boolean empty){
						if(item == null || empty){
							setText("");
						}else{
							setText(String.format("%.2f", item));
						}
					}
					};
				});
			jokatuaCol.setCellValueFactory(cellData -> cellData.getValue().isVigenteProperty());
			jokatuaCol.setCellFactory(column -> {
				return new TableCell<Apuesta, Boolean>(){
					protected void updateItem(Boolean item, boolean empty){
						if(item == null || empty){
							setText("");
						}else{
							if(item){
								setText("Ez");
							}else{
								setText("Bai");
							}
						}
					}
					};
				});
			irabaziaCol.setCellValueFactory(cellData -> cellData.getValue().isGanadoProperty());
			irabaziaCol.setCellFactory(column -> {
				return new TableCell<Apuesta, Boolean>(){
					protected void updateItem(Boolean item, boolean empty){
						if(item == null || empty){
							setText("");
						}else{
							if(item){
								setText("Bai");
								setStyle("--fx-backgroud-color: green");
							}else{
									setText("Ez");
								}
							}
						}
					};
				});
			estadisChart.setData(estadisUser);
			tablaApostuak.setItems(modelo.getApuestasuser());
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public void goToPrincipal(){
		myController.setScreen("principal");
		myController.unloadScreen("apuestas");
	}
	public void goToCobrar(){
		if(myController.isScreenLoaded("cobrarApuestas")){
			myController.unloadScreen("cobrarApuestas");
		}
		myController.loadScreen(ScreensFramework.CobrarApuestas, ScreensFramework.CobrarApuestas_FXML);
		myController.setScreen("cobrarApuestas");
	}

}
