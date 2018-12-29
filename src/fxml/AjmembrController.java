/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import DataBase.DBConnection;

import GettersSetters.MembrImg;
import GettersSetters.RecuImg;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import gestionsmembrecnfp.GestionsMembreCNFP;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class AjmembrController implements Initializable {

    @FXML
    public JFXDatePicker dateinsc;
    @FXML
    public JFXDatePicker dateexp;
    @FXML
    public JFXTextField codecli;
    @FXML
    public JFXTextField nom;
    @FXML
    public JFXComboBox<String> titre;
    @FXML
    public JFXTextField prenom;
    @FXML
    public ToggleGroup sexe;
    @FXML
    public JFXTextField depart;
    @FXML
    public JFXTextField cycle;
    @FXML
    public JFXTextField adress;
    @FXML
    public JFXTextField tel;
    @FXML
    public JFXTextField etab;
    @FXML
    public JFXTextField mail;
    @FXML
    public JFXTextField jourrest;
    @FXML
    public JFXButton vider;
    @FXML
    public JFXButton ajoutbtn;
    @FXML
    public JFXButton selectimg;
    public final String pattern = "yyyy-MM-dd";
    @FXML
    public ImageView photo;
    @FXML
    private StackPane rootpane;
    DBConnection dconn;
    Connection con;
    PreparedStatement stmnt;
    ResultSet rs;
    @FXML
    public JFXRadioButton masc;
    @FXML
    public JFXRadioButton fem;
    @FXML
    public JFXTextField numrecu;
    @FXML
    public JFXTextField montanrecu;
    public FileInputStream fis;
    MembrImg modimembr = new MembrImg();
    RecuImg modirecu = new RecuImg();
    public String codeMembr;
    public String codeMembrRc;
    @FXML
    public Text membreVar;

    private String mbrid;
    @FXML
    public JFXButton modifbtn;
    private Image image;
    public Image imgbdd;
    private String imgpath;
    @FXML
    private Label labelpath;
    public File file;
    public MembrImg mbi;
    Blob imgblob;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titre.getItems().addAll("Etudiant(e)", "Doctorant(e)", "Enseignant/Chercheur", "PAT", "Autres");
        modifbtn.setVisible(false);
        modifbtn.setDisable(true);

        dateinsc.getValue();

        //Converir le format du date en aaaa-mm-jj
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dateinsc.setConverter(converter);
        dateexp.setConverter(converter);
        dateinsc.setValue(LocalDate.now());
        dateexp.setValue(dateinsc.getValue().plusYears(1));
        
        css(vider);
        css(ajoutbtn);
        css(modifbtn);
    }

     public void css(JFXButton btns){
        btns.getStylesheets().add(GestionsMembreCNFP.class.getResource("cssbtn.css").toExternalForm());
    }
    
    @FXML
    public void jourrestant() {
        //recuperation des jours
        LocalDate d1 = dateexp.getValue();
        LocalDate d2 = dateinsc.getValue();

        //Difference de deux dates
        if (d1.isBefore(d2)) {
            Long d3 = ChronoUnit.DAYS.between(d1, d2);

            if (dateexp != null) {
                System.out.println("nmbr jours= " + d3);
                jourrest.setText(String.valueOf(d3) + " Jours Expiré");
                jourrest.setFocusColor(Color.GREEN);
                jourrest.setEditable(false);
            }
        } else {
            Long d3 = ChronoUnit.DAYS.between(d2, d1);
            jourrest.setText(String.valueOf(d3) + " Jours Restant");
            jourrest.setFocusColor(Color.RED);
            jourrest.setEditable(false);
        }

    }

    public void vider() {

        codecli.setText(null);
        nom.setText(null);
        sexe.selectToggle(null);
        prenom.setText(null);
        titre.getSelectionModel().select(-1);
        depart.setText(null);
        cycle.setText(null);
        adress.setText(null);
        tel.setText(null);
        etab.setText(null);
        mail.setText(null);
        jourrest.setText(null);
        numrecu.setText(null);

        Image img = new Image("Images/customer.png");
        photo.setImage(img);
        
    }

    @FXML
    private void vider_act(ActionEvent event) {

        vider();

    }

    @FXML
    private void ajoutbtn_act(ActionEvent event) throws FileNotFoundException {

        dconn = new DBConnection();

        if (chmpnvide() || cmprecunvide()) {

            String sexe1;
            if (masc.isSelected()) {
                sexe1 = "Masculin";
            } else if (fem.isSelected()) {
                sexe1 = "Feminin";
                System.out.println(sexe1);
            } else {
                sexe1 = null;
            }

            try {

                String reqrecu = "INSERT INTO bdd_membres.recu(num_recu,date_recu,date_recu_expire,code_cli,montant_lettres) VALUES ('" + numrecu.getText() + "','" + dateinsc.getValue() + "','" + dateexp.getValue() + "','" + codecli.getText() + "','" + montanrecu.getText() + "')";

                String reqmembr = "INSERT INTO bdd_membres.membres(code_cli,titre_cli,sexe_cli,nom_cli,prenom_cli,departement_cli,cycle_anne_etude_cli,adresse_cli,telephone_cli,etablissement_cli,couriel_cli,photo_cli) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

                con = dconn.mkDataBase();
                stmnt = con.prepareStatement(reqmembr);
                stmnt.setString(1, codecli.getText());
                stmnt.setString(2, titre.getSelectionModel().getSelectedItem());
                stmnt.setString(3, sexe1);
                stmnt.setString(4, nom.getText());
                stmnt.setString(5, prenom.getText());
                stmnt.setString(6, depart.getText());
                stmnt.setString(7, cycle.getText());
                stmnt.setString(8, adress.getText());
                stmnt.setString(9, tel.getText());
                stmnt.setString(10, etab.getText());
                stmnt.setString(11, mail.getText());
                if (imgpath != null) {
                    InputStream is;
                    is = new FileInputStream(new File(imgpath));
                    stmnt.setBlob(12, is);
                } else {

                     stmnt.setBlob(12, (Blob) null);
                }

                stmnt.executeUpdate();
                try {
                    stmnt = con.prepareStatement(reqrecu);
                    stmnt.executeUpdate(reqrecu);

                    stmnt.close();
                    con.close();
                    
                } catch (SQLException e) {
                    System.out.println(e);
                }

                        TrayNotification tn = new TrayNotification();
                        tn.setTitle("Ajout avec succes");
                        tn.setMessage("Le membre numero "+codecli.getText()+" a été ajouté");
                        tn.setAnimationType(AnimationType.POPUP);
                        tn.showAndDismiss(Duration.seconds(4));
                        vider();
            } catch (SQLException e) {
                Alert alerr = new Alert(Alert.AlertType.ERROR);
                alerr.setTitle("Erreur");
                alerr.setHeaderText("Impossible d'ajouter les contenus, veuillez bien verifier s'il vous plait");
                alerr.setContentText(e.getMessage());
                alerr.showAndWait();
                System.out.println("Impossible d'ajouter le membre");
                System.out.println(e);
            }
        } else {
            System.out.println("champ vide");
        }

    }

    @FXML
    private void selectimg_act(ActionEvent event) {
        FileChooser fileDialog = new FileChooser();
        fileDialog.setTitle("choisir une image de profil");
        FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("Fichiers JPG(*.jpg)", "*.JPG", "JPEG files", "*.jpeg", "PNG files", "*.png");
        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("Fichiers PNG(*.png)", "*.PNG");
        FileChooser.ExtensionFilter filterAll = new FileChooser.ExtensionFilter("Touts les fichiers images", "*.jpg", "*.jpeg", "*.png");
        FileChooser.ExtensionFilter filterJPEG = new FileChooser.ExtensionFilter("Fichiers JPEG", "*.jpeg");
        fileDialog.getExtensionFilters().addAll(filterAll, filterJPEG, filterJPG, filterPNG);

        //Ouvrir le Dialogue
        file = fileDialog.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage buff = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(buff, null);
                photo.setImage(image);

                imgpath = file.getAbsolutePath();
                System.out.println(imgpath);

            } catch (IOException e) {

                Logger.getLogger(GestionsMembreCNFP.class.getName()).log(Level.SEVERE, null, e);
                JFXSnackbar fXSnackbar = new JFXSnackbar(rootpane);
                fXSnackbar.show("Impossible d'ajouter une image, verifier le format ou la taille", 5000);
            }

        }
    }

    public boolean chmpnvide() {
        boolean cmpnvide = false;
        if (codecli.getText().isEmpty() || titre.getSelectionModel().isEmpty() || nom.getText().isEmpty()
                || sexe.getSelectedToggle() == null) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("certains champs sont vides");
            al.setHeaderText("Certains champs ne peur pas être vide");
            al.setContentText("Veuillez bien verifier: \n le code client \n le titre du client \n le nom et le sexe du client ");
            al.showAndWait();
            return cmpnvide;
        } else {
            cmpnvide = true;
        }

        return cmpnvide;
    }

    public boolean cmprecunvide() {
        boolean nvide = false;
        if (numrecu.getText().isEmpty() || montanrecu.getText().isEmpty()) {
            Alert a2 = new Alert(Alert.AlertType.WARNING);
            a2.setTitle("Certains champs sont vide");
            a2.setHeaderText("Ceratins champ ne peut pas être vide");
            a2.setContentText("Veuillez bien verfifier: \n le numero de recu \n Le montant de recu");
            a2.showAndWait();
            return nvide;
        } else {
            nvide = true;
        }
        return nvide;
    }

    public void selectModif(MembrImg mbrimg) {

        System.out.println("name :" + mbrimg.code_cli);
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareCall("SELECT * FROM bdd_membres.membres WHERE code_cli='" + mbrimg.code_cli + "'");
            rs = stmnt.executeQuery();

            while (rs.next()) {
                mbrimg.code_cli = rs.getString(1);
                mbrimg.titre_cli = rs.getString(2);
                mbrimg.sexe_cli = rs.getString(3);
                mbrimg.nom_cli = rs.getString(4);
                mbrimg.prenom_cli = rs.getString(5);
                mbrimg.depatement_cli = rs.getString(6);
                mbrimg.cycle_anne_etude_cli = rs.getString(7);
                mbrimg.adresse_cli = rs.getString(8);
                mbrimg.telephone_cli = rs.getString(9);
                mbrimg.etablissement_cli = rs.getString(10);
                mbrimg.couriel_cli = rs.getString(11);
                mbrimg.photo_cli = rs.getBlob(12);
            }

            rs.close();
            stmnt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AjmembrController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectmodifrecu(RecuImg recimg) {
        dconn = new DBConnection();
        try {
            con = dconn.mkDataBase();
            stmnt = con.prepareCall("SELECT * FROM bdd_membres.recu WHERE code_cli='" + recimg.code_cli + "'");
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

        } catch (SQLException ex) {
            Logger.getLogger(AjmembrController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifrecu() {
        modirecu.code_cli = codeMembrRc;
        selectmodifrecu(modirecu);
        numrecu.setText(modirecu.numrecu);

        //Concertion du date en localdate
        Date dater = modirecu.daterecu;
        LocalDate ldtr = dater.toLocalDate();
        Date datex = modirecu.daterecuExp;
        LocalDate ldtx = datex.toLocalDate();

        dateinsc.setValue(ldtr);
        dateexp.setValue(ldtx);
        montanrecu.setText(modirecu.montant);
    }

    public void modif() throws IOException, SQLException {
        modimembr.code_cli = codeMembr;
        selectModif(modimembr);
        codecli.setText(modimembr.code_cli);

        //Ajout des selections sur le combobox
        switch (modimembr.titre_cli) {
            case "Etudiant(e)":
                titre.getSelectionModel().select(0);
                break;
            case "Doctorant(e)":
                titre.getSelectionModel().select(1);
                break;
            case "Enseignant/Chercheur":
                titre.getSelectionModel().select(2);
                break;
            case "PAT":
                titre.getSelectionModel().select(3);
                break;
            case "Autres":
                titre.getSelectionModel().select(4);
            default:
                titre.getSelectionModel().select(-1);
                break;
        }

        //Ajout des selections sur le sexe
        switch (modimembr.sexe_cli) {
            case "Masculin":
                sexe.selectToggle(masc);
                break;
            case "Feminin":
                sexe.selectToggle(fem);
                break;
            default:
                sexe.selectToggle(null);
                break;
        }

        nom.setText(modimembr.nom_cli);
        prenom.setText(modimembr.prenom_cli);
        depart.setText(modimembr.depatement_cli);
        cycle.setText(modimembr.cycle_anne_etude_cli);
        adress.setText(modimembr.adresse_cli);
        tel.setText(modimembr.telephone_cli);
        etab.setText(modimembr.etablissement_cli);
        mail.setText(modimembr.couriel_cli);
        
        if(modimembr.photo_cli!=null){       
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(modimembr.photo_cli.getBytes(1, (int) modimembr.photo_cli.length()));
        modimembr.image = new Image(byteArrayInputStream);
        imgbdd=modimembr.image;
        photo.setImage(imgbdd);
        }else{
            Image imgm = new Image("Images/customer.png");
            photo.setImage(imgm);
        }
        
    }

    @FXML
    private void modifbtnact(ActionEvent event) throws FileNotFoundException {

        dconn = new DBConnection();

        String sexe2;
        if (masc.isSelected()) {
            sexe2 = "Masculin";
        } else if (fem.isSelected()) {
            sexe2 = "Feminin";
            System.out.println(sexe2);
        } else {
            sexe2 = null;
        }

        try {
            con = dconn.mkDataBase();

            String requeteMDrecu = "UPDATE bdd_membres.recu SET num_recu='" + numrecu.getText() + "',date_recu='" + dateinsc.getValue() + "',date_recu_expire='" + dateexp.getValue() + "',"
                    + "code_cli='" + codecli.getText() + "',montant_lettres='" + montanrecu.getText() + "' WHERE code_cli='" + codecli.getText() + "'";

            String requeteMDmembre = "UPDATE bdd_membres.membres SET code_cli=?, titre_cli=?, sexe_cli=?, nom_cli=?,prenom_cli=?,departement_cli=?,cycle_anne_etude_cli=?,adresse_cli=?,telephone_cli=?,etablissement_cli=?,couriel_cli=?,photo_cli=? WHERE code_cli='" + codecli.getText() + "'";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez vous vraiment modifier le membre numero" + codecli.getText() + "?");
            alert.setContentText("Cliquer sur Oui pour continuer");

            ButtonType yesButton = new ButtonType("Oui");
            ButtonType noButton = new ButtonType("Non");
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                stmnt = con.prepareStatement(requeteMDrecu);
  
                stmnt.executeUpdate();
                try {
                    stmnt = con.prepareStatement(requeteMDmembre);
                    
                    stmnt.setString(1, codecli.getText());
                stmnt.setString(2, titre.getSelectionModel().getSelectedItem());
                stmnt.setString(3, sexe2);
                stmnt.setString(4, nom.getText());
                stmnt.setString(5, prenom.getText());
                stmnt.setString(6, depart.getText());
                stmnt.setString(7, cycle.getText());
                stmnt.setString(8, adress.getText());
                stmnt.setString(9, tel.getText());
                stmnt.setString(10, etab.getText());
                stmnt.setString(11, mail.getText());
                if (imgpath != null) {
                    InputStream is;
                    is = new FileInputStream(new File(imgpath));
                    stmnt.setBlob(12, is);
                } else {
                     stmnt.setBlob(12, (Blob) null);
                }
                    
                    stmnt.executeUpdate();

                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur");
                    a.setHeaderText("Une Erreur s'est produite!");
                    a.setContentText("Veuillez verifier les champs, ou votre connectivité à la base de données.");
                    a.showAndWait();
                }

                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("succes");
                b.setHeaderText("Modification avec succes!");
                b.setContentText("Cliquer sur OK pour continuer");
                b.showAndWait();
                vider();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AjmembrController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
