/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsmembrecnfp;

import DataBase.DBConnection;
import GettersSetters.DroitUtil;
import GettersSetters.ExcelCNFP;

import GettersSetters.MembrImg;

import GettersSetters.MembresList;
import GettersSetters.RecuImg;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import fxml.AjmembrController;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class AnchorHomeController implements Initializable {

    @FXML
    private TableView<MembresList> tableMembres;
    @FXML
    private TableColumn<MembresList, String> codecli_col;
    @FXML
    private TableColumn<MembresList, String> titre_col;
    @FXML
    private TableColumn<MembresList, String> sexe_col;
    @FXML
    private TableColumn<MembresList, String> nom_col;
    @FXML
    private TableColumn<MembresList, String> prenom_col;
    @FXML
    private TableColumn<MembresList, String> dep_col;
    @FXML
    private TableColumn<MembresList, String> adress_col;
    @FXML
    private TableColumn<MembresList, String> tel_col;
    @FXML
    private TableColumn<MembresList, String> etabl_col;
    @FXML
    private TableColumn<MembresList, String> couriel_col;
    @FXML
    private TableColumn<MembresList, String> cycle_clo;
    @FXML
    private TableColumn<MembresList, String> jourrest_col;

    @FXML
    private JFXButton actubtn;
    private ImageView photo;
    @FXML
    public JFXButton nouveauutil;
    @FXML
    public JFXButton supprmembres;

    DBConnection dconn;
    Connection con;
    PreparedStatement stmnt;
    ResultSet rs;
    @FXML
    private GridPane gridpane;
    MembrImg membres;
    RecuImg recu;
    private String code_cli;
    @FXML
    private Circle rectanglePhoto;
    private Image image;
    MembresList mbrlist;

    private FileInputStream fis;
    @FXML
    private JFXTextField searc;
    @FXML
    private JFXComboBox<String> cmbChoice;
    @FXML
    private JFXButton modifie;
    private String mbrid;
    public ExcelCNFP xl;
    private DroitUtil droit;
    

    @FXML
    private JFXButton exportXL;
    
    String code_;
    String identif_;
    String type_;
    
    public DroitUtil getDroitUtil(){
        return droit;
    }
    
    
      public void appliqueDrt(DroitUtil droit){
        code_=droit.getCode();
        identif_=droit.getIdentifiant();
        type_=droit.getType();
        System.out.println("L'utilisateur numero: "+code_+" est conécté en tant que "+type_);
        if(type_.equals("Ordinaire")){
            Tooltip tp=new Tooltip("Seul l'administrateur peuvent supprimer les membres");
            supprmembres.setTooltip(tp);
            supprmembres.setOnAction(event->{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de droit");
            alert.setHeaderText("Vous n'êtes pas autorisé a supprimer les membres");
            alert.setContentText("Vous devez Utiliser un compte administrateur pour supprimer les membres");
            alert.showAndWait();
            });
        }    
        this.droit=droit;
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setitems();
        cmbChoice.getItems().addAll("code client", "Titre", "Nom", "Prenom", "Departement", "Etablissement", "Adresse");
        cmbChoice.getSelectionModel().select(0);
        tableMembres.getStylesheets().add(GestionsMembreCNFP.class.getResource("cssbtn.css").toExternalForm());
        css(actubtn);
        css(exportXL);
        css(supprmembres);
        css(nouveauutil);
        css(modifie);
        
       
    }
    public void css(JFXButton btns){
        btns.getStylesheets().add(GestionsMembreCNFP.class.getResource("cssbtn.css").toExternalForm());
    }

    @FXML
    private void nouveauutil_act(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Ajmembr.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/Images/lgCNFPSmall.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void supprmembr_act(ActionEvent event) {

        MembresList membreList1 = tableMembres.getSelectionModel().getSelectedItem();
        MembrImg mbr1 = new MembrImg();
        if (membreList1 != null) {

            mbr1.code_cli = membreList1.getCode_cli();
            code_cli = mbr1.code_cli;
            String reqRecu = "DELETE FROM bdd_membres.recu WHERE code_cli='" + code_cli + "'";
            String reqMembr = "DELETE FROM bdd_membres.membres WHERE code_cli='" + code_cli + "'";
            dconn = new DBConnection();
            try {
                con = dconn.mkDataBase();

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Voulez vous vraiment supprimmer le membre numero " + code_cli + "?");
                alert.setContentText("Cliquer sur Oui pour continuer");

                ButtonType yesButton = new ButtonType("Oui");
                ButtonType noButton = new ButtonType("Non");
                ButtonType cancelButton = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == yesButton) {
                    stmnt = con.prepareStatement(reqRecu);
                    stmnt.executeUpdate();
                    try {
                        stmnt = con.prepareStatement(reqMembr);
                        stmnt.executeUpdate();
                        stmnt.close();

                        Alert al10 = new Alert(AlertType.INFORMATION);
                        al10.setTitle("Suppression avec succes");
                        al10.setHeaderText("Le membre numero " + code_cli + " a été bien supprimé");
                        al10.showAndWait();
                        setitems();
                    } catch (Exception e) {
                        System.out.println(e);
                        Alert a = new Alert(AlertType.WARNING);
                        a.setTitle("Suppression impossible");
                        a.setHeaderText("Le membre numero " + code_cli + " n'a pas pu être supprimé, \n veuilez ressayer ou verfier votre connectivité a ala base de données");
                        a.showAndWait();
                    }

                } else if (result.get() == noButton) {

                }

            } catch (SQLException ex) {
                Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    @FXML
    private void actubtn_act(ActionEvent event) {
        setitems();
       // setrecuj();
    }

    public void actualiser(MembrImg membres) {

        String requete = "SELECT * FROM bdd_membres.membres INNER JOIN bdd_membres.recu ON bdd_membres.membres.code_cli = bdd_membres.recu.code_cli";
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareStatement(requete);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                membres.code_cli = rs.getString(1);
                membres.titre_cli = rs.getString(2);
                membres.sexe_cli = rs.getString(3);
                membres.nom_cli = rs.getString(4);
                membres.prenom_cli = rs.getString(5);
                membres.depatement_cli = rs.getString(6);
                membres.cycle_anne_etude_cli = rs.getString(7);
                membres.adresse_cli = rs.getString(8);
                membres.telephone_cli = rs.getString(9);
                membres.etablissement_cli = rs.getString(10);
                membres.couriel_cli = rs.getString(11);

                Date dtinsc = rs.getDate(14);
                Date dtexp = rs.getDate(15);
                LocalDate ld1 = dtinsc.toLocalDate();
                LocalDate ld2 = dtexp.toLocalDate();
                LocalDate ld3 = LocalDate.now();

                // Compteur du jour rest sur la tableView
                if (ld3.isBefore(ld2)) {
                    membres.jourrest_cli = ChronoUnit.DAYS.between(ld3, ld2);
                } else {
                    membres.jourrest_cli = ChronoUnit.DAYS.between(ld2, ld3);
                }
                membres.MbrLists.addAll(new MembresList(membres.code_cli, membres.titre_cli, membres.sexe_cli, membres.nom_cli, membres.prenom_cli, membres.depatement_cli, membres.cycle_anne_etude_cli, membres.adresse_cli, membres.telephone_cli, membres.etablissement_cli, membres.couriel_cli, membres.jourrest_cli));
            }

            rs.close();
            stmnt.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void jourrest(RecuImg recimg) {
        String requete = "SELECT * FROM bdd_membres.recu";
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareStatement(requete);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                recimg.numrecu = rs.getString(1);
                recimg.daterecu = rs.getDate(2);
                recimg.daterecuExp = rs.getDate(3);
                recimg.code_cli = rs.getString(4);
                recimg.montant = rs.getString(5);
            }
            rs.close();
            stmnt.close();
            con.close();

            Date dt1 = recimg.daterecu;
            LocalDate ld1 = dt1.toLocalDate();
            Date dt2 = recimg.daterecuExp;
            LocalDate ld2 = dt2.toLocalDate();

            if (ld1.isBefore(ld2)) {
                recimg.jourrest = ChronoUnit.DAYS.between(ld1, ld2);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setrecuj() {
        recu = new RecuImg();
        jourrest_col.setCellValueFactory(new PropertyValueFactory<>("recimg.jourrest"));
        jourrest(recu);
    }

    public void setitems() {
        membres = new MembrImg();

        tableMembres.setItems(membres.MbrLists);
        // tableMembres.setItems(recu.RecuLists);

        //Ajout des valeurs au colones
        codecli_col.setCellValueFactory(new PropertyValueFactory<>("code_cli"));
        titre_col.setCellValueFactory(new PropertyValueFactory<>("titre_cli"));
        sexe_col.setCellValueFactory(new PropertyValueFactory<>("sexe_cli"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom_cli"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom_cli"));
        dep_col.setCellValueFactory(new PropertyValueFactory<>("departement_cli"));
        cycle_clo.setCellValueFactory(new PropertyValueFactory<>("cycle_anne_etude_cli"));
        adress_col.setCellValueFactory(new PropertyValueFactory<>("adresse_cli"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("telephone_cli"));
        etabl_col.setCellValueFactory(new PropertyValueFactory<>("etablissement_cli"));
        couriel_col.setCellValueFactory(new PropertyValueFactory<>("couriel_cli"));
        jourrest_col.setCellValueFactory(new PropertyValueFactory<>("jourrest_cli"));

        actualiser(membres);

    }

    public void setSelect(MembrImg mb) {

        String requeteImg = "SELECT bdd_membres.membres.photo_cli FROM bdd_membres.membres WHERE code_cli=?";
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareCall(requeteImg);
            stmnt.setString(1, mb.code_cli);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                mb.photo_cli = rs.getBlob(1);

                System.out.println("Notre image de la base de données: " + mb.photo_cli);

                if (mb.photo_cli != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mb.photo_cli.getBytes(1, (int) mb.photo_cli.length()));
                    mb.image = new Image(byteArrayInputStream);

                } else {
                    mb.image = new Image("/Images/customer.png");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MembrImg.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void afficheimg() {
        MembresList membreList = tableMembres.getSelectionModel().getSelectedItem();
        MembrImg mbr = new MembrImg();
        if (membreList != null) {

            mbr.code_cli = membreList.getCode_cli();
            setSelect(mbr);
            code_cli = mbr.code_cli;
            System.out.println(code_cli);
            image = mbr.image;
            System.out.println(image);
            rectanglePhoto.setFill(new ImagePattern(image));

        } else {
            System.out.println("La table est vide, veuillez cliquer sur actualiser");
        }

    }

    @FXML
    private void mouseclik(MouseEvent event) {

        afficheimg();
        int click = event.getClickCount();

        if (click == 2) {
            vuembr();
        }
    }

    @FXML
    private void keyreleased(KeyEvent event) {

        //Fleche haut
        if (event.getCode().equals(KeyCode.UP)) {
            afficheimg();
        } // fleche bas
        else if (event.getCode().equals(KeyCode.DOWN)) {
            afficheimg();
        }
    }

    @FXML
    private void searc(ActionEvent event) {
        membres = new MembrImg();
        tableMembres.setItems(membres.MbrLists);

        //Ajout des valeurs au colones
        codecli_col.setCellValueFactory(new PropertyValueFactory<>("code_cli"));
        titre_col.setCellValueFactory(new PropertyValueFactory<>("titre_cli"));
        sexe_col.setCellValueFactory(new PropertyValueFactory<>("sexe_cli"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom_cli"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom_cli"));
        dep_col.setCellValueFactory(new PropertyValueFactory<>("departement_cli"));
        cycle_clo.setCellValueFactory(new PropertyValueFactory<>("cycle_anne_etude_cli"));
        adress_col.setCellValueFactory(new PropertyValueFactory<>("adresse_cli"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("telephone_cli"));
        etabl_col.setCellValueFactory(new PropertyValueFactory<>("etablissement_cli"));
        couriel_col.setCellValueFactory(new PropertyValueFactory<>("couriel_cli"));

        searchvoid(membres);
    }

    @FXML
    private void modifiebtn(ActionEvent event) throws IOException {
        if (tableMembres.getSelectionModel().getSelectedItem() != null) {
            vuemodif();
        } else {
            System.out.println("Selection vide");
        }

    }

    public void searchvoid(MembrImg mbrsrh) {
        String choice = "";

        switch (cmbChoice.getSelectionModel().getSelectedItem()) {
            case "code client":
                choice = "code_cli";
                break;
            case "Titre":
                choice = "titre_cli";
                break;
            case "Nom":
                choice = "nom_cli";
                break;
            case "Prenom":
                choice = "prenom_cli";
                break;
            case "Departement":
                choice = "departement_cli";
                break;
            case "Etablissement":
                choice = "etablissement_cli";
                break;
            case "Adresse":
                choice = "adresse_cli";
                break;
            default:
                break;
        }
        System.out.println(choice);

        String requete = "SELECT * FROM bdd_membres.membres WHERE " + choice + " LIKE '%" + searc.getText() + "%'";
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareStatement(requete);
            rs = stmnt.executeQuery();

            while (rs.next()) {
                mbrsrh.code_cli = rs.getString(1);
                mbrsrh.titre_cli = rs.getString(2);
                mbrsrh.sexe_cli = rs.getString(3);
                mbrsrh.nom_cli = rs.getString(4);
                mbrsrh.prenom_cli = rs.getString(5);
                mbrsrh.depatement_cli = rs.getString(6);
                mbrsrh.cycle_anne_etude_cli = rs.getString(7);
                mbrsrh.adresse_cli = rs.getString(8);
                mbrsrh.telephone_cli = rs.getString(9);
                mbrsrh.etablissement_cli = rs.getString(10);
                mbrsrh.couriel_cli = rs.getString(11);
                //mbrsrh.jourrest_cli = rs.getString(12);
                mbrsrh.MbrLists.addAll(new MembresList(mbrsrh.code_cli, mbrsrh.titre_cli, mbrsrh.sexe_cli, mbrsrh.nom_cli, mbrsrh.prenom_cli, mbrsrh.depatement_cli, mbrsrh.cycle_anne_etude_cli, mbrsrh.adresse_cli, mbrsrh.telephone_cli, mbrsrh.etablissement_cli, mbrsrh.couriel_cli, mbrsrh.jourrest_cli));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void vuemodif() {
        if (!tableMembres.getSelectionModel().isEmpty()) {
            MembresList mbrlst = tableMembres.getSelectionModel().getSelectedItem();
            String items = mbrlst.getCode_cli();
            System.out.println("Code cli est: " + items);
            if (!items.equals(null)) {
                AjmembrController aj = new AjmembrController();
                // MbrNameId mbrids=new MbrNameId();

                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println("L'erreur est iici 1");
                fxmlLoader.setLocation(getClass().getResource("/fxml/Ajmembr.fxml"));
                System.out.println("L'erreur est iici 2");

                try {
                    fxmlLoader.load();
                    Parent parent = fxmlLoader.getRoot();
                    Scene scene = new Scene(parent);
                    scene.setFill(new Color(0, 0, 0, 0));
                    AjmembrController mbraj = fxmlLoader.getController();
                    mbraj.codeMembr = mbrlst.getCode_cli();
                    mbraj.codeMembrRc = mbrlst.getCode_cli();

                    mbraj.modif();
                    mbraj.modifrecu();
                    mbraj.membreVar.setText("Mise à jour ou Modification du membres");
                    mbraj.ajoutbtn.setVisible(false);
                    mbraj.modifbtn.setVisible(true);
                    mbraj.modifbtn.setDisable(false);
                    mbraj.codecli.setEditable(false);
                    mbraj.vider.setVisible(false);
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.show();
                } catch (IOException | SQLException e) {
                    System.out.println(e);
                    
                }
            }
        } else {
            System.out.println("tablView vide");
            Alert b = new Alert(AlertType.WARNING);
            b.setTitle("Selection vide");
            b.setHeaderText("Selection vide!");
            b.setContentText("Veuillez choisir un membre avant de modifier");
            b.showAndWait();
        }

    }

    public void vuembr() {

        if (!tableMembres.getSelectionModel().isEmpty()) {
            MembresList mbrlst = tableMembres.getSelectionModel().getSelectedItem();
            String items = mbrlst.getCode_cli();
            System.out.println("Code cli est: " + items);
            if (!items.equals(null)) {
                AjmembrController aj = new AjmembrController();
                // MbrNameId mbrids=new MbrNameId();

                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/fxml/Ajmembr.fxml"));

                try {

                    fxmlLoader.load();
                    Parent parent = fxmlLoader.getRoot();
                    Scene scene = new Scene(parent);
                    scene.setFill(new Color(0, 0, 0, 0));
                    AjmembrController mbraj = fxmlLoader.getController();
                    mbraj.codeMembr = mbrlst.getCode_cli();
                    mbraj.codeMembrRc = mbrlst.getCode_cli();

                    mbraj.modif();
                    mbraj.modifrecu();
                    mbraj.membreVar.setText("Vue des membres");
                    mbraj.ajoutbtn.setVisible(false);
                    mbraj.modifbtn.setVisible(false);
                    mbraj.modifbtn.setDisable(false);
                    mbraj.codecli.setEditable(false);
                    mbraj.adress.setEditable(false);
                    mbraj.cycle.setEditable(false);
                    mbraj.depart.setEditable(false);
                    mbraj.etab.setEditable(false);
                    mbraj.mail.setEditable(false);
                    mbraj.dateexp.setEditable(false);
                    mbraj.dateinsc.setEditable(false);
                    mbraj.vider.setVisible(false);
                    mbraj.selectimg.setVisible(false);
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.show();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else {
            System.out.println("tablView vide");
            Alert b = new Alert(AlertType.WARNING);
            b.setTitle("Selection vide");
            b.setHeaderText("Selection vide!");
            b.setContentText("Veuillez choisir un membre avant de modifier");
            b.showAndWait();
        }

    }

    @FXML
    private void XLExport(ActionEvent event) {
        FileChooser chooser = new FileChooser();

        // Extension du fichier  , Remarque: Seul les fichiers .xls mais pas des fichiers xlsx
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(filter);

        // Afficher le dialogue d'exportation
        File file = chooser.showSaveDialog(exportXL.getScene().getWindow());

        //Importation du class depuis le package Getters Setters
        xl = new ExcelCNFP();
        if (file != null) {
            try {
                xl.XLPrepare(file);

                Alert a = new Alert(AlertType.INFORMATION);
                a.setTitle("Succes");
                a.setHeaderText("Exportation avec succes");
                a.setContentText("Veuillez verifier l'emplacement de l'exportation pour verifier");
                a.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur de l'exportation");
                al.setContentText("Verfier votre connectivité à la base de données");
                al.showAndWait();
            }
        }
    }
    
    

}
