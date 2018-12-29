/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

import java.sql.Date;

/**
 *
 * @author MultiMedia
 */
public class Recu {
    public String numrecu;
    public Date daterecu;
    public Date daterecuExp;
    public String code_cli;
    public String montant;
    public Long jourrest;

    public Recu(String numrecu, Date daterecu, Date daterecuExp, String code_cli, String montant, Long jourrest) {
        this.numrecu = numrecu;
        this.daterecu = daterecu;
        this.daterecuExp = daterecuExp;
        this.code_cli = code_cli;
        this.montant = montant;
        this.jourrest=jourrest;
    }

    public String getNumrecu() {
        return numrecu;
    }

    public Date getDaterecu() {
        return daterecu;
    }

    public Date getDaterecuExp() {
        return daterecuExp;
    }

    public String getCode_cli() {
        return code_cli;
    }

    public String getMontant() {
        return montant;
    }

    public Long getJourrest() {
        return jourrest;
    }
    
    

    public void setNumrecu(String numrecu) {
        this.numrecu = numrecu;
    }

    public void setDaterecu(Date daterecu) {
        this.daterecu = daterecu;
    }

    public void setDaterecuExp(Date daterecuExp) {
        this.daterecuExp = daterecuExp;
    }

    public void setCode_cli(String code_cli) {
        this.code_cli = code_cli;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setJourrest(Long jourrest) {
        this.jourrest = jourrest;
    }
    
    
    
}
