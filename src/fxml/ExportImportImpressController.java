/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import DataBase.DBConnection;
import GettersSetters.ExcelCNFP;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class ExportImportImpressController implements Initializable {

    @FXML
    private JFXButton btnImXL;
    @FXML
    private JFXButton btnExpXL;
    @FXML
    private JFXButton formulaireBtn;
    @FXML
    private JFXButton btnmbrCart;
    public ExcelCNFP xl;
    private Connection con;
    DBConnection dconn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        css(btnImXL);
        css(btnExpXL);
        css(btnmbrCart);
        css(formulaireBtn);
        btnImXL.setVisible(false);
    }    

     public void css(JFXButton bt){
         bt.getStylesheets().add(ExportImportImpressController.class.getResource("/fxml/cssfxml.css").toExternalForm());
        //btns.getStylesheets().add(GestionsMembreCNFP.class.getResource("/fxml/cssfxml.css").toExternalForm());
    }
    
    @FXML
    private void ImpXL(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        
        // Extension du fichier  , Remarque: Seul les fichiers .xls mais pas des fichiers xlsx
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(filter);
        File file=chooser.showOpenDialog(btnImXL.getScene().getWindow());
        xl=new ExcelCNFP();
        if(file!=null){
            try {
                xl.PrepareImportFileXL(file);
            } catch (IOException ex) {
                Logger.getLogger(ExportImportImpressController.class.getName()).log(Level.SEVERE, null, ex);
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setHeaderText("Erreur de l'importation");
                a.setContentText("Veuillez verifier si le document n'est pas vide ou les données sont cohérents.");
                a.showAndWait();
            }
        }
    }

    @FXML
    private void ExXL(ActionEvent event) {
         FileChooser chooser = new FileChooser();
        
        // Extension du fichier  , Remarque: Seul les fichiers .xls mais pas des fichiers xlsx
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(filter);
        
        // Afficher le dialogue d'exportation
        File file = chooser.showSaveDialog(btnExpXL.getScene().getWindow());
        
        //Importation du class depuis le package Getters Setters
        xl=new ExcelCNFP();
        if (file != null){
            try {
                xl.XLPrepare(file);
                
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Succes");
                a.setHeaderText("Exportation avec succes");
                a.setContentText("Veuillez verifier l'emplacement de l'exportation pour verifier");
                a.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(fxml.ExportImportImpressController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                Alert al=new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur de l'exportation");
                al.setContentText("Verfier votre connectivité à la base de données");
                al.showAndWait();
            }
        }
    }

    @FXML
    private void formulaireMbr(ActionEvent event) throws SQLException {
        String formulaire = "src\\etat\\report3.jrxml";
        dconn=new DBConnection();
        con = dconn.mkDataBase();
        JasperReport jform;
        try {
            System.out.println("TST");
            jform = JasperCompileManager.compileReport(formulaire);
            System.out.println("TST01");
            JasperPrint jpf = JasperFillManager.fillReport(jform, null, con);
            System.out.println("TST02");
        JasperViewer.viewReport(jpf, false);
        } catch (JRException ex) {
            Logger.getLogger(ExportImportImpressController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void cartEtat(ActionEvent event) throws JRException, SQLException {
        String reportRecto = "src\\etat\\report1.jrxml";
        String reportVerso = "src\\etat\\report2.jrxml";
        dconn=new DBConnection();
        con = dconn.mkDataBase();
        JasperReport jr = JasperCompileManager.compileReport(reportRecto);
        JasperReport js = JasperCompileManager.compileReport(reportVerso);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
        JasperPrint jps = JasperFillManager.fillReport(js, null, con);
        JasperViewer.viewReport(jp, false);
        JasperViewer.viewReport(jps, false);
    }
    
}
