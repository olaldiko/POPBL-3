package application;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import datos.Liga;
import datos.ModeloApuestas;
import datos.Partido;

public class ControladorEmaitzak implements Initializable, ControlledScreen {
	ScreensController myController;
	ModeloApuestas modelo;
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}
	
	@FXML
	Button btnatzera = new Button();
	@FXML
	ComboBox<Liga> ligasel = new ComboBox<Liga>();
	@FXML
	TableView<Partido> emaitzataula = new TableView<Partido>();
	@FXML
	TableColumn<Partido, Date> fechacol;
	@FXML
	TableColumn<Partido, String> localacol;
	@FXML
	TableColumn<Partido, String> emaitzacol;
	@FXML
	TableColumn<Partido, String> bisitanteacol;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
			assert modelo != null;
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnatzera.setOnAction(event -> goToPrincipal());
		ligasel.setOnAction(event -> cambioLigas());
		fechacol.setCellValueFactory(cellData -> cellData.getValue().getFechaProperty());
		localacol.setCellValueFactory(cellData -> cellData.getValue().getLocal().getNombreProperty());
		emaitzacol.setCellValueFactory(cellData -> mostrarEmaitza(cellData.getValue()));
		bisitanteacol.setCellValueFactory(cellData -> cellData.getValue().getVisitante().getNombreProperty());
		
		if(modelo == null)System.out.println("El modelo es null en C/E");
		ligasel.setItems(modelo.getLigas());
		emaitzataula.setItems(modelo.getPartidosemaitzak());
	}
	private StringProperty mostrarEmaitza(Partido value) {
		
		StringProperty emaitza = new SimpleStringProperty();
		emaitza.set(value.getGolesLocal()+" - "+value.getGolesVisitante());
		return emaitza;
	}
	public void goToPrincipal(){
		myController.setScreen("principal");
	}
	
	public void cambioLigas(){
		try{
			modelo.updatePartidosEmaitzak(ligasel.getSelectionModel().getSelectedItem().getIdLiga());
		}catch(ManteniException e){
			
		}
		
	}
}
