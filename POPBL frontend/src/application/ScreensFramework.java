/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */ 

package application;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import datos.ModeloApuestas;

/**
 *
 * @author Angie
 */
public class ScreensFramework extends Application {
    File css;
	public static final String MPrincipal = "principal";
	public static final String MPrincipal_FXML = "../vistas/Principal.fxml";
	public static final String Mapostuak = "apostuak";
	public static final String Mapostuak_FXML = "../vistas/NireApostuak.fxml";
	public static final String MErregistroa = "erregistroa";
	public static final String MErregistroa_FXML = "../vistas/Erregistroa.fxml";
    public static final String MEmaitzak = "emaitzak";
    public static final String MEmaitzak_FXML = "../vistas/Emaitzak.fxml";
    public static final String NewApuesta = "newApuesta";
    public static final String NewApuesta_FXML = "../vistas/NewApuesta.fxml";
    public static final String Login = "login";
    public static final String Login_FXML = "../vistas/Login.fxml";
    public static final String Manteni = "manteni";
    public static final String Manteni_FXML = "../vistas/ManteniScreen.fxml";
    public static final String InsertDiru = "insertDiru";
    public static final String InsertDiru_FXML = "../vistas/InsertDiru.fxml";
    public static final String ConfirmApuesta = "confirmApuesta";
    public static final String ConfirmApuesta_FXML = "../vistas/ConfirmApuesta.fxml";
    @Override
    public void start(Stage primaryStage) {
    	
        ScreensController mainContainer = new ScreensController();
        try{
        	ModeloApuestas.getInstance();
        }catch(ManteniException e){
        	
        }
        mainContainer.loadScreen(ScreensFramework.MPrincipal, ScreensFramework.MPrincipal_FXML);
        mainContainer.loadScreen(ScreensFramework.MErregistroa, ScreensFramework.MErregistroa_FXML);
        mainContainer.loadScreen(ScreensFramework.MEmaitzak, ScreensFramework.MEmaitzak_FXML);
        mainContainer.setScreen(ScreensFramework.MPrincipal);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        //File css = new File("bin/vistas/dark.css");
        //scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///dark.css");
        primaryStage.setWidth(768f);
        primaryStage.setHeight(1024f);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
