package application;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginDialogoa{
	@FXML
	Button btnatzera = new Button();
	@FXML
	Button btnsartu = new Button();
	@FXML
	TextField erabilfield = new TextField();
	@FXML
	PasswordField passfield = new PasswordField();
	
	
	public void showLogin(){
		Parent root = null;
		FXMLLoader fxload = new FXMLLoader(this.getClass().getResource("../vistas/login.fxml"));
		try {
			root = fxload.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = new Stage();
		Scene sce = new Scene(root);
		
		stage.setScene(sce);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setAlwaysOnTop(true);
		stage.setFocused(true);
		stage.centerOnScreen();
		System.out.println("Start");
		btnatzera.setOnAction(event ->stage.close());
		stage.show();
		
	}
	
}
