/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.util.ServerController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class DBConnectController implements Initializable {

    @FXML
    private TextField localhostfield;
    @FXML
    private TextField fieldport;
    @FXML
    private TextField bddnamefield;
    @FXML
    private TextField utilfield;
    @FXML
    private JFXButton cntbtn;
    @FXML
    private PasswordField passfield;
    @FXML
    private JFXButton acueilbtn;
    @FXML
    private AnchorPane panepr;

    Properties properties = new Properties();
    InputStream inputStream;
    OutputStream output = null;

    Connection con;

    String url;
    String user;
    String pass;
    @FXML
    private Label serversta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SQLStatus(serversta);
        loadDataFromFile();
    }

    @FXML
    private void cntbtn_act(ActionEvent event) {
       ecritFic();
    }

    @FXML
    private void acueilbtn_act(ActionEvent event) throws IOException {
        AnchorPane acueil = FXMLLoader.load(getClass().getResource("/gestionsmembrecnfp/FXMLDocument.fxml"));
        panepr.getChildren().clear();
        panepr.getChildren().add((Node) acueil);
    }

    
    //Lecture des données dans le fichier database.cnfp
    public void loadDataFromFile() {
         try {
            inputStream = new FileInputStream("database.cnfp");
            
            properties.load(inputStream);
            System.err.println("Host : "+ properties.getProperty("host"));
            localhostfield.setText(properties.getProperty("host"));
            bddnamefield.setText(properties.getProperty("db"));
            utilfield.setText(properties.getProperty("user"));
            passfield.setText(properties.getProperty("password"));
            fieldport.setText(properties.getProperty("port"));
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Recuperation des données de la base
    public void loadPropertiesFile(){
                try {
            inputStream = new FileInputStream("database.cnfp");
            properties.load(inputStream);
            url = "jdbc:mysql://"+properties.getProperty("host")+":"+properties.getProperty("port")+"/";
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    //une classe du type booleen pour verifier si la connection est établie
    private boolean dbConnect() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url , user, pass);
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Verifier le condition boolean");
        }
        return false;
    }

    //Status du serveur SQL
    public void SQLStatus(Label labelstat) {
        try {
            inputStream = new FileInputStream("database.cnfp");
            String host = properties.getProperty("host");
            int port = 3306;
            Socket socket = new Socket(host, port);
            labelstat.setText("Serveur Connecté");
            labelstat.setTextFill(Color.GREEN);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //ecriture des données dans le fichier database.cnfp
    public void ecritFic(){
        try {
            output=new FileOutputStream("database.cnfp");
            properties.setProperty("host", localhostfield.getText().trim());
            properties.setProperty("port", fieldport.getText().trim());
            properties.setProperty("db", bddnamefield.getText().trim());
            properties.setProperty("user", utilfield.getText().trim());
            properties.setProperty("password", passfield.getText().trim());
            properties.store(output, null);
            output.close();
            
            if(dbConnect()){
                con.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connection au serveur établie");
                alert.setHeaderText("Se connecter maintenant?");
                alert.setContentText("Une connection au serveur établie \n pour es connecter, cliquer sur OK");
                alert.initStyle(StageStyle.UNDECORATED);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                      AnchorPane acueil = FXMLLoader.load(getClass().getResource("/gestionsmembrecnfp/FXMLDocument.fxml"));
                         panepr.getChildren().clear();
                         panepr.getChildren().add((Node) acueil);
                }else{
                Alert error_alert = new Alert(Alert.AlertType.ERROR);
                error_alert.setTitle("Impossible de se connecter au serveur MSQL");
                error_alert.setHeaderText("Impossible de se connecter au serveur MSQL");
                error_alert.setContentText("Verifier les champs ou le serveur lui même");
                error_alert.initStyle(StageStyle.UNDECORATED);
                error_alert.show();
            }
            }
        } catch (Exception e) {
            Alert a1=new Alert(Alert.AlertType.ERROR);
            a1.setTitle("Erreur");
            a1.setHeaderText("Impossible de creer le fichier database.cnfp");
            a1.setContentText(e.getMessage() + "\n penser à executer l'application en tant que Administrateur ");
            a1.showAndWait();
        }
    }
}
