/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author MultiMedia
 */
public class MembrImg {
   // public ObservableList<String> allMembres = FXCollections.observableArrayList();
    public String code_cli;
    public String titre_cli;
    public String sexe_cli;
    public String nom_cli;
    public String prenom_cli;
    public String depatement_cli;
    public String cycle_anne_etude_cli;
    public String adresse_cli;
    public String telephone_cli;
    public String etablissement_cli;
    public String couriel_cli;
    public Blob photo_cli;
    public Long jourrest_cli;
    public Image image;
    
    public ObservableList<MembresList> MbrLists = FXCollections.observableArrayList();
}