/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author MultiMedia
 */
public class Utilisateurs {
    private final StringProperty code_util;
    private final StringProperty nom_util;
    private final StringProperty prenom_util;
    private final StringProperty identifiant_util;
    private final StringProperty mdp_util;
    private final StringProperty type_util;

    public Utilisateurs(String code_util, String nom_util, String prenom_util, String identifiant_util, String mdp_util, String type_util) {
        this.code_util = new SimpleStringProperty(code_util);
        this.nom_util = new SimpleStringProperty(nom_util);
        this.prenom_util = new SimpleStringProperty(prenom_util);
        this.identifiant_util = new SimpleStringProperty(identifiant_util);
        this.mdp_util = new SimpleStringProperty(mdp_util);
        this.type_util=new SimpleStringProperty(type_util);
    }
    
   // Getters
    public String getCode_util(){
    return code_util.get();
    }
    
    public String getNom_util(){
    return nom_util.get();
    }
    
    public String getPrenom_util(){
    return prenom_util.get();
    }
    
    public String getIdentifiant_util(){
    return identifiant_util.get();
    }
    
    public String getMdp_util(){
    return mdp_util.get();
    }
    
    public String getType_util(){
    return type_util.get();
    }
    
    
    //Setters
    public void setCode_util(String valeurs){
    code_util.setValue(valeurs);
    }
    public void setNom_util(String valeurs){
    nom_util.setValue(valeurs);
    }
    public void setPrenom_util(String valeurs){
    prenom_util.setValue(valeurs);
    }
    public void setIdentifiant_util(String valeurs){
    identifiant_util.setValue(valeurs);
    }
    public void setMdp_util(String valeurs){
    mdp_util.setValue(valeurs);
    }
}
