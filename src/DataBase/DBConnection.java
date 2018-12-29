/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MultiMedia
 */
public class DBConnection {
    
     Properties properties = new Properties();
    InputStream inputStream;

    public Connection con;

    String url;
    String user;
    String pass;


    public void loadPropertiesFile() {
        try {
            inputStream = new FileInputStream("database.cnfp");
            properties.load(inputStream);
            url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/bdd_membres";
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println("Erreur sur la recuperation du propriété du fic: "+e);
        }
    }

    public Connection mkDataBase() throws SQLException {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection à la base de données établie");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return con;
    }
    
}
