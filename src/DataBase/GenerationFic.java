/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author MultiMedia
 */


//Generation du fichier de sauvegarde et de raglage de la base de données
public class GenerationFic {
    Properties property=new Properties();
    InputStream input;
    OutputStream out;
    
    public void MkDBProperty(){
        try {
            out=new FileOutputStream("database.cnfp");
            property.setProperty("host", "localhost");
            property.setProperty("port", "3306");
            property.setProperty("db", "bdd_membres");
            property.setProperty("user", "root");
            property.setProperty("password", "");
          
            try {
                property.store(out, null);
            } catch (IOException ex) {
                Logger.getLogger(GenerationFic.class.getName()).log(Level.SEVERE, null, ex);
                Alert a1=new Alert(Alert.AlertType.ERROR);
            a1.setTitle("Erreur");
            a1.setHeaderText("Impossible de creer le fichier database.cnfp");
            a1.setContentText(ex.getMessage() + "\n penser à executer l'application en tant que Administrateur ");
            a1.showAndWait();
            }
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(GenerationFic.class.getName()).log(Level.SEVERE, null, ex);
                Alert a1=new Alert(Alert.AlertType.ERROR);
            a1.setTitle("Erreur");
            a1.setHeaderText("Impossible de creer le fichier database.cnfp");
            a1.setContentText(ex.getMessage() + "\n penser à executer l'application en tant que Administrateur ");
            a1.showAndWait();
            }
        
        
    }   catch (FileNotFoundException ex) {
            Logger.getLogger(GenerationFic.class.getName()).log(Level.SEVERE, null, ex);
            Alert a1=new Alert(Alert.AlertType.ERROR);
            a1.setTitle("Erreur");
            a1.setHeaderText("Impossible de creer le fichier database.cnfp");
            a1.setContentText(ex.getMessage() + "\n penser à executer l'application en tant que Administrateur ");
            a1.showAndWait();
        }
    
}

    
    public String loadpropertyfile(){
        try {
            input=new FileInputStream("database.cnfp");
            property.load(input);
            return property.getProperty("db"); //On test si on à reccueil le fichier
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
