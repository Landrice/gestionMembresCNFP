/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import DataBase.DBConnection;
import GettersSetters.UtilImg;
import GettersSetters.Utilisateurs;
import com.jfoenix.controls.JFXButton;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class UtilisateurController implements Initializable {

    @FXML
    private JFXButton nouvutil;
    @FXML
    private JFXButton supprutil;
    @FXML
    private TableView<Utilisateurs> tablview;
    @FXML
    private TableColumn<Utilisateurs, String> codeutil_col;
    @FXML
    private TableColumn<Utilisateurs, String> nomutil_col;
    @FXML
    private TableColumn<Utilisateurs, String> prenomutil_col;
    @FXML
    private TableColumn<Utilisateurs, String> identifiutil_col;
    @FXML
    private JFXButton actubtn;
    DBConnection dconn;
    Connection con;
    PreparedStatement stmnt;
    ResultSet rs;
    private ObservableList<Utilisateurs>data;
    private String pass;
    Utilisateurs util;
    public String code_util;
    @FXML
    private TableColumn<?, ?> typeutil_col;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableutil();
        css(actubtn);
        css(nouvutil);
        css(supprutil);
    }  
    
     public void css(JFXButton btns){
        btns.getStylesheets().add(gestionsmembrecnfp.GestionsMembreCNFP.class.getResource("cssbtn.css").toExternalForm());
    }

    @FXML
    private void nouvutil_act(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("NewUserHome.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void supprutil_act(ActionEvent event) {
        Utilisateurs utilsuppr=tablview.getSelectionModel().getSelectedItem();
        UtilImg utilimg=new UtilImg();
        if(utilsuppr != null){
            utilimg.code_util=utilsuppr.getCode_util();
            code_util=utilimg.code_util;
            System.out.println(code_util);
            String requtil = "DELETE FROM bdd_membres.utilisateurs WHERE code_util='" + code_util + "'";
            dconn=new DBConnection();
            try {
                con=dconn.mkDataBase();
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez vous vraiment supprimmer l'Utilisateur numero " + code_util + "?");
                alert.setContentText("Cliquer sur Oui pour continuer");

                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non");
                ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == yesButton) {
                    stmnt = con.prepareStatement(requtil);
                    stmnt.executeUpdate();
                     Alert al10 = new Alert(Alert.AlertType.INFORMATION);
                        al10.setTitle("Suppression avec succes");
                        al10.setHeaderText("L'Utilisateur numero " + code_util + " a été bien supprimé");
                        al10.showAndWait();
                        tableutil();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
                 Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Suppression impossible");
                        a.setHeaderText("L'utilisateur numero " + code_util + " n'a pas pu être supprimé, \n veuilez ressayer ou verfier votre connectivité a ala base de données");
                        a.showAndWait();
            }
        }
    }



    @FXML
    private void actubtn_act(ActionEvent event) {
        tableutil();
    }
    
    public void tableutil(){
    
        dconn=new DBConnection();
        data=FXCollections.observableArrayList();
        String requete="SELECT * FROM bdd_membres.utilisateurs";
        try {
            con=dconn.mkDataBase();
            stmnt=con.prepareStatement(requete);
            rs=stmnt.executeQuery();
            
            while(rs.next()){
                data.add(new Utilisateurs(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)));
            }
            rs.close();
            stmnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        codeutil_col.setCellValueFactory(new PropertyValueFactory<>("code_util"));
        nomutil_col.setCellValueFactory(new PropertyValueFactory<>("nom_util"));
        prenomutil_col.setCellValueFactory(new PropertyValueFactory<>("prenom_util"));
        identifiutil_col.setCellValueFactory(new PropertyValueFactory<>("identifiant_util"));
        typeutil_col.setCellValueFactory(new PropertyValueFactory<>("type_util"));
        
        tablview.setItems(null);
        tablview.setItems(data);
        tablview.setOnMouseClicked((MouseEvent e)->{

        });
        
        
    }
}
