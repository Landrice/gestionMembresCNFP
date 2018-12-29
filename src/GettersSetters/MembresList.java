/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

/**
 *
 * @author MultiMedia
 */
public class MembresList {
    public String code_cli;
    public String titre_cli;
    public String sexe_cli;
    public String nom_cli;
    public String prenom_cli;
    public String departement_cli;
    public String cycle_anne_etude_cli;
    public String adresse_cli;
    public String telephone_cli;
    public String etablissement_cli;
    public String couriel_cli;
    public Long jourrest_cli;
    public String imgpath;
    
    //Constructeurs
    public MembresList(String code_cli, String titre_cli, String sexe_cli, String nom_cli, String prenom_cli, String departement_cli, String cycle_anne_etude_cli, String adresse_cli, String telephone_cli, String etablissement_cli, String couriel_cli, Long jourrest_cli) {
        this.code_cli = code_cli;
        this.titre_cli = titre_cli;
        this.sexe_cli = sexe_cli;
        this.nom_cli = nom_cli;
        this.prenom_cli = prenom_cli;
        this.departement_cli = departement_cli;
        this.cycle_anne_etude_cli = cycle_anne_etude_cli;
        this.adresse_cli = adresse_cli;
        this.telephone_cli = telephone_cli;
        this.etablissement_cli = etablissement_cli;
        this.couriel_cli = couriel_cli;
        this.jourrest_cli = jourrest_cli;
    }

   
    
    //Getters

    public String getCode_cli() {
        return code_cli;
    }

    public String getTitre_cli() {
        return titre_cli;
    }

    public String getSexe_cli() {
        return sexe_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public String getPrenom_cli() {
        return prenom_cli;
    }

    public String getDepartement_cli() {
        return departement_cli;
    }

    public String getCycle_anne_etude_cli() {
        return cycle_anne_etude_cli;
    }

    public String getAdresse_cli() {
        return adresse_cli;
    }

    public String getTelephone_cli() {
        return telephone_cli;
    }

    public String getEtablissement_cli() {
        return etablissement_cli;
    }

    public String getCouriel_cli() {
        return couriel_cli;
    }

    public Long getJourrest_cli() {
        return jourrest_cli;
    }
    
    //Setters

    public void setCode_cli(String code_cli) {
        this.code_cli = code_cli;
    }

    public void setTitre_cli(String titre_cli) {
        this.titre_cli = titre_cli;
    }

    public void setSexe_cli(String sexe_cli) {
        this.sexe_cli = sexe_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public void setPrenom_cli(String prenom_cli) {
        this.prenom_cli = prenom_cli;
    }

    public void setDepartement_cli(String departement_cli) {
        this.departement_cli = departement_cli;
    }

    public void setCycle_anne_etude_cli(String cycle_anne_etude_cli) {
        this.cycle_anne_etude_cli = cycle_anne_etude_cli;
    }

    public void setAdresse_cli(String adresse_cli) {
        this.adresse_cli = adresse_cli;
    }

    public void setTelephone_cli(String telephone_cli) {
        this.telephone_cli = telephone_cli;
    }

    public void setEtablissement_cli(String etablissement_cli) {
        this.etablissement_cli = etablissement_cli;
    }

    public void setCouriel_cli(String couriel_cli) {
        this.couriel_cli = couriel_cli;
    }

    public void setJourrest_cli(Long jourrest_cli) {
        this.jourrest_cli = jourrest_cli;
    }
    
}