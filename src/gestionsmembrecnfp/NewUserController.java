/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsmembrecnfp;

import DataBase.DBConnectController;
import DataBase.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class NewUserController implements Initializable {

    @FXML
    private JFXTextField code_util;
    @FXML
    private JFXTextField nom_util;
    @FXML
    private JFXTextField prenom_util;
    @FXML
    private JFXTextField id_utilis;
    @FXML
    private JFXTextField newpass;

    @FXML
    private JFXButton connecterbtn;
    @FXML
    private JFXButton btncreer;
    @FXML
    private Text bddetat;
    @FXML
    private AnchorPane paneutil;

    //Importation des utilités au base
    DBConnection dconn;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    
    @FXML
    private Label lbletat;
    DBConnectController init;
    @FXML
    private JFXComboBox<String> typeUtil;
    TextInputDialog dialog;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        testcon();
        typeUtil.getItems().addAll("Ordinaire", "Administrateur");
        typeUtil.getSelectionModel().select(0);
        
    }
    
    public void typeutil(){
        
        
        String choice = "";

        switch (typeUtil.getSelectionModel().getSelectedItem()) {
            case "Ordinaire":
                choice = "Ordinaire";
                break;
            case "Administrateur":
                choice = "Administrateur";
                break;
            default:
                break;
        }
        System.out.println(choice);
        
        if(choice.equals("Administrateur")){
        dialog = new TextInputDialog();
 
        dialog.setTitle("Authentification");
        dialog.setHeaderText("Vous devez entrer le mot de passe pour creer un compte administrateur");
        dialog.setContentText("Mot de passe par defaut:");
 
        Optional<String> result = dialog.showAndWait();
        
         result.ifPresent(name -> {
             if(name.matches("cnfp")){
             typeUtil.getSelectionModel().select(1);
             System.out.println("mitovy");
             }else{
             typeUtil.getSelectionModel().select(0);
             System.out.println("Tsy mitovy");
             Alert alt=new Alert(Alert.AlertType.ERROR);
             alt.setTitle("Erreur");
             alt.setHeaderText("Mot de passe incorrecte");
             alt.setContentText("Vous ne pouvez pas selectionner le type Administrateur \n sans le vrai mot de passe");
             alt.showAndWait();
             }
        });
        }
        
   
    }

    public void testcon() {

        init = new DBConnectController();
        System.out.println("Database DBConnectController OK");
        init.SQLStatus(lbletat);

    }

    @FXML
    private void act_connecterbtn(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parentuser = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, parentuser, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);

        decorator.setMaxSize(800, 500);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(GestionsMembreCNFP.class.getResource("/autrecss/scenecss.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("Se connecter");
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    public void act_btncreer(ActionEvent event) {
        dconn = new DBConnection();//Initialisation du connection
        if (champplein()) {
            try {
                con = dconn.mkDataBase();
                String requete = "INSERT INTO bdd_membres.utilisateurs(code_util,nom_util,prenom_util,identifiant_util,mdp_util,type_util) VALUES('" + code_util.getText() + "',"
                        + "'" + nom_util.getText() + "','" + prenom_util.getText() + "','" + id_utilis.getText() + "','" + newpass.getText() + "','"+typeUtil.getSelectionModel().getSelectedItem()+"')";
                pst = con.prepareStatement(requete);
                pst.executeUpdate(requete);
                pst.close();
                con.close();
                

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Utilisateur ajouté");
                alert.setHeaderText("Bonjour " + id_utilis.getText() + ". Votre ajout a été un succès");
                alert.setContentText("Vous pouvez vous connecter maintenant");

                Optional<ButtonType> result = alert.showAndWait();

            } catch (SQLException ex) {
                Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("Ajout impossible");
                al.setHeaderText("desolé, impossible d'ajouter un utilisateur");
                al.setContentText("Verifier votre base de données \n votre connectivité, le nom d'utilisateur ou le mot de passe");
                al.show();
            }

        }
    }

    public boolean champplein() {
        boolean cmp = false;
        if (code_util.getText().isEmpty() || nom_util.getText().isEmpty() || id_utilis.getText().isEmpty() || newpass.getText().isEmpty()) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Champs vide");
            a.setHeaderText("Certains champs ne peut pas être vide");
            a.setContentText("seul le champ prenom est acceptable comme vide, veuillez remplir les autres champs");
            a.showAndWait();
            return false;
        } else {
            cmp = true;
        }
        return cmp;
    }


    public void vider() {
        code_util.setText(null);
        nom_util.setText(null);
        prenom_util.setText(null);
       
        newpass.setText(null);

    }

    @FXML
    private void act_type_util(ActionEvent event) {
        typeutil();
    }
}
