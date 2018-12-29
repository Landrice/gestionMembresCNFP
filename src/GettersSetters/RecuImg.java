/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MultiMedia
 */
public class RecuImg {
    public String numrecu;
    public Date daterecu;
    public Date daterecuExp;
    public String code_cli;
    public String montant;
    public Long jourrest;
    
    public ObservableList<Recu> RecuLists = FXCollections.observableArrayList();
}
