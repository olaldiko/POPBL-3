package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import datos.ModeloApuestas;

public class ControladorConfirmApuesta implements ControlledScreen, Initializable {
	ScreensController myController;
	ModeloApuestas modelo;
	WebEngine engine;
	Printer prt;
	PageLayout layout;
    @FXML
    private WebView boletoWebView = new WebView();

    @FXML
    private Button btnPrint = new Button();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			modelo = ModeloApuestas.getInstance();
			engine = boletoWebView.getEngine();
			engine.load("http://olaldiko.mooo.com:81/boleto.php?id="+modelo.getApuestaInProgress().getIdApuesta());
			prt = Printer.getDefaultPrinter();
			layout = prt.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, MarginType.DEFAULT);
			btnPrint.setOnAction(event -> printApuesta());
			
		} catch (ManteniException e) {
			
		}

	}

	private void printApuesta() {
		PrinterJob trabajo = PrinterJob.createPrinterJob();
		if(trabajo != null){
			engine.print(trabajo);
				trabajo.endJob();
			}
		myController.setScreen("principal");
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage; 
	}

}
