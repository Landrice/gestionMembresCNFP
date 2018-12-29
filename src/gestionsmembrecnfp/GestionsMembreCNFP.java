/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsmembrecnfp;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author MultiMedia
 */
public class GestionsMembreCNFP extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);

        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(GestionsMembreCNFP.class.getResource("/autrecss/scenecss.css").toExternalForm());
        stage.setTitle("Se connecter");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/Images/logoCNFP.png"));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
