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
public class DroitUtil {
    String code;
    String identifiant;
    String type;

    public DroitUtil() {
    }
    
    

    public DroitUtil(String code, String identifiant, String type) {
        this.code = code;
        this.identifiant = identifiant;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getType() {
        return type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
