package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginDialogoa extends AnchorPane implements Initializable{
	@FXML
	Button btnatzeralog = new Button();
	@FXML
	Button btnsartulog = new Button();
	@FXML
	TextField erabilfield = new TextField();
	@FXML
	PasswordField passfield = new PasswordField();
	StringProperty iduser;
	StringProperty pass;
	Stage stage = new Stage();
	public void showLogin(){
		Parent root = null;
		FXMLLoader fxload = new FXMLLoader(this.getClass().getResource("../vistas/login.fxml"));
		try {
			fxload.setRoot(this);
			root = fxload.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fxload.setController(this);
		fxload.setRoot(this);
		btnatzeralog.setOnAction(event ->stage.close());
		btnsartulog.setOnAction(event -> getDatos());
		Scene sce = new Scene(root);
		
		stage.setScene(sce);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setAlwaysOnTop(true);
		stage.setFocused(true);
		stage.centerOnScreen();
		System.out.println("Start");
		
		
		stage.show();
		
	}
	public void getDatos(){
		iduser.set(erabilfield.getText());
		pass.set(passfield.getText());
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
}
