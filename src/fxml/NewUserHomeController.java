/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import DataBase.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gestionsmembrecnfp.NewUserController;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class NewUserHomeController implements Initializable {

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
    private JFXButton btncreer;
 

    //Importation des utilités au base
    DBConnection dconn;
    Connection con;
    ResultSet rs;
    PreparedStatement pst;
    @FXML
    private JFXComboBox<String> typeUtil;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeUtil.getItems().addAll("Ordinaire", "Administrateur");
        typeUtil.getSelectionModel().select(0);
    }

    @FXML
    private void act_btncreer(ActionEvent event) {
        
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
               // vider();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Utilisateur ajouté");
                alert.setHeaderText("Bonjour " + id_utilis.getText() + ". Votre ajout a été une succes");
                //alert.setContentText("Voulez vous se connecter maintenant? \n cliquer sur se connecter");

                Optional<ButtonType> result = alert.showAndWait();

            } catch (SQLException ex) {
                Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("Ajout impossible");
                al.setHeaderText("desolé, impossible d'ajouter un utilisateur");
                al.setContentText("Verifier votre base de données");
                al.show();
            }

        } else{
        Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("Ajout impossible");
                al.setHeaderText("desolé, impossible d'ajouter un utilisateur");
                al.setContentText("Verifier votre base de données");
                al.show();
        
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
        id_utilis.setText(null);
        newpass.setText(null);
  
    }
}
