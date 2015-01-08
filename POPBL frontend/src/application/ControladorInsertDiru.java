package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import datos.Apuesta;
import datos.ModeloApuestas;

public class ControladorInsertDiru implements ControlledScreen, Initializable, ChangeListener<Double>, InvalidationListener {
	ScreensController myController;
	ModeloApuestas modelo;
	Apuesta apostua;
	DoubleProperty dirua;
	Double eskatutakoDirua;
    @FXML
    private Button btnatzera;

    @FXML
    private Label sartuaLabel;

    @FXML
    private Button btnJarraitu;

    @FXML
    private ProgressIndicator diruProgress;

    @FXML
    private Label eskatuaLabel;
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
			eskatuaLabel.setText(String.format("%f.2", eskatutakoDirua));
			dirua.bind(modelo.getDiruaProperty());
			dirua.addListener(this);
			
		} catch (ManteniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changed(ObservableValue<? extends Double> observable,
			Double oldValue, Double newValue) {
			sartuaLabel.setText(String.format("%f.2", newValue));
			diruProgress.setProgress(newValue/eskatutakoDirua);
			if(newValue >= eskatutakoDirua){
				commitApuesta();
			}
	}

	private void commitApuesta() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidated(Observable observable) {
		// TODO Auto-generated method stub
		
	}

}
