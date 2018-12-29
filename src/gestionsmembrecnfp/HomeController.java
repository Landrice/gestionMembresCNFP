/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionsmembrecnfp;

import GettersSetters.DroitUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author MultiMedia
 */
public class HomeController implements Initializable {

    @FXML
    private JFXButton btnaccueil;
    @FXML
    private JFXButton btnmembres;
    @FXML
    public JFXButton btnutilisateur;
    @FXML
    private JFXButton btnlougout;


    
    @FXML
    private BorderPane ancprincipal;
    @FXML
    private VBox vbox;
    private BorderPane bdpn;
    @FXML
    private AnchorPane acDashBord;
    @FXML
    private ScrollPane leftSideBarScroolPan;
    @FXML
    private BorderPane appcontent;
    @FXML
    private AnchorPane head;
    @FXML
    private StackPane acContent;
    @FXML
    private JFXHamburger hamburger;
    HamburgerSlideCloseTransition hamb;
    @FXML
    private JFXButton btnaEtatmembr;
    public Label lbl;
    private DroitUtil drt;
    String code_;
    String identif_;
    String type_;

    
    public DroitUtil getDroitUtil(){
        return drt;
    }
    
    public void appliqueDroit(DroitUtil drt){
        code_=drt.getCode();
        identif_=drt.getIdentifiant();
        type_=drt.getType();
        System.out.println("L'utilisateur numero: "+code_+" est conécté en tant que "+type_);
        if(type_.equals("Ordinaire")){
            Tooltip tp=new Tooltip("Seul l'administrateur peuvent consulter les utilisateurs");
            btnutilisateur.setTooltip(tp);
            btnutilisateur.setOnAction(event->{
            Alert z=new Alert(Alert.AlertType.ERROR);
            z.setTitle("Erreur de droit");
            z.setHeaderText("Vous n'êtes pas autorisé a consulter les utilisateurs");
            z.setContentText("Utiliser un compte administrateur pour consulter les utilisateurs");
            z.showAndWait();
            });
        }
        
        this.drt=drt;
 
    }
    
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       hamb=new HamburgerSlideCloseTransition(hamburger);
       
       hamb.setRate(-1);
       
       TranslateTransition sideMenu = new TranslateTransition(Duration.millis(600.0), acDashBord);
            sideMenu.setByX(-200);
            sideMenu.play();
            acDashBord.getChildren().clear();
       
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/fxml/HomePrincipale.fxml").openStream());
        } catch (IOException e) {
        }
        AnchorPane ar = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(ar);
     }
    
    
     private void setNode(Node node) {
        acContent.getChildren().clear();
        acContent.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void btnaccueil_act(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/fxml/HomePrincipale.fxml").openStream());
        } catch (IOException e) {
        }
        AnchorPane ar = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(ar);
    }

    @FXML
    private void btnmembres_act(ActionEvent event) {
        //setNode(membres);
        DroitUtil dtutil=new DroitUtil();
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("anchorHome.fxml").openStream());
        } catch (IOException e) {
        }
        dtutil.setType(type_);
        AnchorHomeController achmctrl=fxmlLoader.getController();
        achmctrl.appliqueDrt(drt);
        
        
        AnchorPane er = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(er);

    }


    @FXML
    private void btnutilisateur_act(ActionEvent event) {
         FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/fxml/Utilisateur.fxml").openStream());
        } catch (IOException e) {
        }
        AnchorPane br = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(br);
    }

    @FXML
    private void btnlougout_act(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parentuser = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, parentuser, false, false, true);
        decorator.setCustomMaximize(false);
        decorator.setBorder(Border.EMPTY);
        decorator.setCustomMaximize(false);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().add(GestionsMembreCNFP.class.getResource("/autrecss/scenecss.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("Se connecter");
        stage.setResizable(false);
        stage.show();
    }

    
    
    @FXML
    private void hamburger_act(MouseEvent event) {
       
      hamb.setRate(hamb.getRate()*-1);
      TranslateTransition sideMenu = new TranslateTransition(Duration.millis(600.0), acDashBord);
      hamb.play();
      if(hamb.getRate()==-1){
            sideMenu.setByX(-200);
            sideMenu.play();
            acDashBord.getChildren().clear();

      }else{    
            sideMenu.setByX(200);
            sideMenu.play();
            acDashBord.getChildren().add(leftSideBarScroolPan);
      }
       
    }

    @FXML
    private void btnEtatmembr_act(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/fxml/ExportImportImpress.fxml").openStream());
        } catch (IOException e) {
        }
        StackPane exprt = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(exprt);
    }
    

}
