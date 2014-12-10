package application;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginDialogoa extends Application{
	@FXML
	Button btnatzera = new Button();
	@FXML
	Button btnsartu = new Button();
	@FXML
	TextField erabilfield = new TextField();
	@FXML
	PasswordField passfield = new PasswordField();
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		FXMLLoader fxload = new FXMLLoader(this.getClass().getResource("login.fxml"));
		AnchorPane pane =(AnchorPane)fxload.load();
		Scene sce = new Scene(pane);
		arg0.setScene(sce);
		arg0.initStyle(StageStyle.UNDECORATED);
		arg0.setAlwaysOnTop(true);
		arg0.centerOnScreen();
		System.out.println("Start");
		arg0.show();
		
	}
	
}
