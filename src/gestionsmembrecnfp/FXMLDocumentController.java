/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsmembrecnfp;

import DataBase.DBConnectController;
import DataBase.DBConnection;
import GettersSetters.DroitUtil;
import GettersSetters.UtilImg;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

/**
 *
 * @author MultiMedia
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField identif_util;
    @FXML
    private JFXPasswordField identif_pass;
    @FXML
    private JFXButton btnvalider;
    @FXML
    private Text etatbase;
    @FXML
    private JFXButton nouvutil;
    @FXML
    private AnchorPane panehomeconnecter;
    @FXML
    private Hyperlink bddparam;
    @FXML
    private Label lbletatsql;
    DBConnectController init;
    DBConnection dconn;
    Connection con;
    PreparedStatement pstmnt;
    Statement stmnt;
    ResultSet rs;
    @FXML
    private JFXSpinner validerProcess;
    @FXML
    private Label nmpd;
    public String VariableType;
    public String d="";

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        test();
        validerProcess.setVisible(false);
        nmpd.setVisible(false);
        btnvalider.disableProperty().bind(identif_util.textProperty().isEmpty() );
        btnvalider.disableProperty().bind(identif_pass.textProperty().isEmpty());
        
        String string =lbletatsql.getText();
        if(string.equals("Non connecté")){
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Verification");
            alert.setHeaderText("Il semble que vous êtes déconecté au serveur de la base de données ou le serveur n'est pas en marche");
            alert.setContentText("Verifier votre conectivité a la base de données, \n ou contacter votre adminstrateur de la base de données. \n Cliquer sur OK pour continuer ");
            
            alert.showAndWait();
            
        }

    }

    //a plus tard , on verifie si les champs sont ne sont pas vide
    public boolean textnvide() {
        boolean conditionvalide = false;
        if (identif_util.getText().trim().isEmpty()
                || identif_pass.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur :");
            alert.setHeaderText("Une Erreur s'est produit, Les champs ne peut pas être vide");
            alert.setContentText("Veuillez entrer votre identifiant et votre mot de passe");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            conditionvalide = false;
        } else {
            conditionvalide = true;
        }
        return conditionvalide;
    }

    public void test() {

        init = new DBConnectController();
        System.out.println("Database DBConnectController OK");
        init.SQLStatus(lbletatsql);

    }

    public void typeutil(UtilImg util){
        String code="";
        String identifi="";
        String type="";
    }
    
    @FXML
    private void act_btnvalider(ActionEvent event) throws IOException, SQLException {

        dconn = new DBConnection();
        con = dconn.mkDataBase();
        validerProcess.setVisible(true);
        nmpd.setVisible(false);
           PauseTransition pauseTransition = new PauseTransition();
                    pauseTransition.setDuration(Duration.seconds(2));
                    
                    pauseTransition.setOnFinished(ev -> {


                         if (con != null) {

            if (textnvide()) {
                System.out.println("ok1");
                try {
                    String requetesql = "SELECT * FROM bdd_membres.utilisateurs WHERE identifiant_util='" + identif_util.getText() + "' and mdp_util='" + identif_pass.getText() + "'";
                    pstmnt = con.prepareStatement(requetesql);
                    nmpd.setVisible(false);
                    rs = pstmnt.executeQuery();
                    
                         if (rs.next()) {
                        //Recuperation de la scene principale

                       /* UtilImg utl=new UtilImg();
                        utl.code_util=rs.getString(1);
                        utl.identfiant_util=rs.getString(4);
                        utl.type_util=rs.getString(6);*/
                       
                        FXMLLoader ld=new FXMLLoader();
                        DroitUtil drt=new DroitUtil(rs.getString(1),rs.getString(4),rs.getString(6)); 
                        d=rs.getString(6);
                        

                        
                        String tst=rs.getString(6);
                        System.out.println(tst);
                        VariableType=tst;
                        
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("Home.fxml"));
                        loader.load(); 
                        Parent parenthome = loader.getRoot();


                        Stage stage = new Stage();
                        stage.getIcons().add(new Image("/Images/lgCNFPSmall.png"));
                        Scene scene = new Scene(parenthome);
                        stage.setScene(scene);
                        stage.setTitle("Gestion des membres du CNFP");
                        stage.show();
                        TrayNotification tn = new TrayNotification();
                        tn.setTitle("Bienvenue "+rs.getString(4));
                        tn.setMessage("Outil de gestion des membres du CNFP.");
                        tn.setAnimationType(AnimationType.POPUP);
                        tn.showAndDismiss(Duration.seconds(5));
                        
                        // Droit pour les menu
                        HomeController hh=loader.getController();
                        hh.appliqueDroit(drt);


                    } else {
                        validerProcess.setVisible(false);
                        nmpd.setVisible(true);

                        identif_pass.setText(null);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("C'est ici l'erreur, else de txt non vide");
            }
        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Serveur de la base de données introuvable");
            al.setHeaderText("Impossible d'établir une connection à la base de données");
            al.setContentText("Nous vous renseignons de voir les paramètres de la base de données \n ou contacter l'administrateur de la base de données");
        }
                        
                });

       pauseTransition.play();
       
       
    }

    @FXML
    private void btn_nouvutil(ActionEvent event) throws IOException {
        //Recuperation du scene principale
        ((Node) (event.getSource())).getScene().getWindow().hide();
        //Recuperation de document fxml
        Parent parentuser = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, parentuser, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);

        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(GestionsMembreCNFP.class.getResource("/autrecss/scenecss.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.setTitle("Nouveau Utilisateur");

        stage.setResizable(false);
        stage.show();

    }

    @FXML
    private void bddparam_act(ActionEvent event) throws IOException {

        StackPane bdd = FXMLLoader.load(getClass().getResource("/DataBase/DBConnect.fxml"));
        panehomeconnecter.getChildren().clear();
        panehomeconnecter.getChildren().add((Node) bdd);
    }

}
