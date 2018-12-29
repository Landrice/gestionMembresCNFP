/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import DataBase.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class HomePrincipaleController implements Initializable {

    @FXML
    private Label totalmembr;
    @FXML
    private Label totutil;
    @FXML
    private Label membretud;
    @FXML
    private Label autremembr;
    DBConnection dconn;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        total();
    }    
    
    public void total(){
    
        dconn=new DBConnection();
        try {
            con=dconn.mkDataBase();
            
            //Listes des nombres des membres
            pst=con.prepareStatement("select count(*) from bdd_membres.membres");
            rs=pst.executeQuery();
            while(rs.next()){totalmembr.setText(rs.getString(1));}
            
            //Liste des nombres des utilisateurs
            pst=con.prepareStatement("select count(*) from bdd_membres.utilisateurs");
            rs=pst.executeQuery();
            while(rs.next()){totutil.setText(rs.getString(1));}
            
        } catch (SQLException ex) {
            Logger.getLogger(HomePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
