/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GettersSetters;

import DataBase.DBConnection;
import gestionsmembrecnfp.AnchorHomeController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MultiMedia
 */
public class ExcelCNFP {

    public DBConnection dconn;
    public Connection con;
    public PreparedStatement stmnt;
    ResultSet rs;

    public void XLPrepare(File file) throws IOException {

        try {
            dconn = new DBConnection();
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(file);

            String requeteJointures = " SELECT * FROM bdd_membres.recu INNER JOIN bdd_membres.membres ON bdd_membres.recu.code_cli = bdd_membres.membres.code_cli";

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet feuille = wb.createSheet("Membres CNFP");

            XSSFRow tete = feuille.createRow(0);
            tete.createCell(0).setCellValue("Date");
            tete.createCell(1).setCellValue("Code client");
            tete.createCell(2).setCellValue("Titre");
            tete.createCell(3).setCellValue("Sexe");
            tete.createCell(4).setCellValue("Nom");
            tete.createCell(5).setCellValue("Prenoms");
            tete.createCell(6).setCellValue("Département /Filière");
            tete.createCell(7).setCellValue("Cycle");
            tete.createCell(8).setCellValue("Adresse");
            tete.createCell(9).setCellValue("Téléphone");
            tete.createCell(10).setCellValue("Etablissement");
            tete.createCell(11).setCellValue("Courriel");

            int index = 1;
            try {
                con = dconn.mkDataBase();

                stmnt = con.prepareStatement(requeteJointures);
                rs = stmnt.executeQuery();
                while (rs.next()) {
                    XSSFRow lignes = feuille.createRow(index);

                    //lignes.createCell(0).setCellValue(rs.getDate(2));
                    Date dt = rs.getDate(2);
                    LocalDate ldt = dt.toLocalDate();
                    String dateSring = ldt.toString();
                    lignes.createCell(0).setCellValue(dateSring);
                    lignes.createCell(1).setCellValue(rs.getString(6));
                    lignes.createCell(2).setCellValue(rs.getString(7));
                    lignes.createCell(3).setCellValue(rs.getString(8));
                    lignes.createCell(4).setCellValue(rs.getString(9));
                    lignes.createCell(5).setCellValue(rs.getString(10));
                    lignes.createCell(6).setCellValue(rs.getString(11));
                    lignes.createCell(7).setCellValue(rs.getString(12));
                    lignes.createCell(8).setCellValue(rs.getString(13));
                    lignes.createCell(9).setCellValue(rs.getString(14));
                    lignes.createCell(10).setCellValue(rs.getString(15));
                    lignes.createCell(11).setCellValue(rs.getString(16));
                    index++;
                }

                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                rs.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnchorHomeController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

    }

    public void PrepareImportFileXL(File fileXl) throws FileNotFoundException, IOException {
        FileInputStream fileIn;
        fileIn = new FileInputStream(fileXl);
        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        
        int indexdheet=0;
       XSSFSheet feuille = wb.getSheetAt(indexdheet);
       
        XSSFRow tete = feuille.getRow(0);
        String date=tete.getCell(0).getStringCellValue();
        String code_client=tete.getCell(1).getStringCellValue();
        String titre=tete.getCell(2).getStringCellValue();
        String sexe=tete.getCell(3).getStringCellValue();
        String nom=tete.getCell(4).getStringCellValue();
        String prenom=tete.getCell(5).getStringCellValue();
        String depart=tete.getCell(6).getStringCellValue();
        String cycle=tete.getCell(7).getStringCellValue();
        String adress=tete.getCell(8).getStringCellValue();
        String tel=tete.getCell(9).getStringCellValue();
        String etab=tete.getCell(10).getStringCellValue();
        String couriel=tete.getCell(11).getStringCellValue();
        indexdheet++;
        
        System.out.print("Date= "+date+"\t");
        System.out.print("Code client= "+code_client+"\t");
        System.out.print("Titre= "+titre+"\t");
        System.out.print("Sexe= "+sexe+"\t");
        System.out.print("Nom= "+nom+"\t");
        System.out.print("Prénom= "+prenom+"\t");
        System.out.print("Département= "+depart+"\t");
        System.out.print("Cycle= "+cycle+"\t");
        System.out.print("Adresse= "+adress+"\t");
        System.out.print("Télephone= "+tel+"\t");
        System.out.print("Etablissement= "+etab+"\t");
        System.out.print("Couriel= "+couriel+"\t");
        
        int i=1;
         XSSFRow lignes = feuille.getRow(i);
         String dti=lignes.getCell(0).getStringCellValue();
         String cd=lignes.getCell(1).getStringCellValue();
         String tt=lignes.getCell(2).getStringCellValue();
         String sx=lignes.getCell(3).getStringCellValue();
         String nm=lignes.getCell(4).getStringCellValue();
         String pr=lignes.getCell(5).getStringCellValue();
         String dp=lignes.getCell(6).getStringCellValue();
         String cl=lignes.getCell(7).getStringCellValue();
         String ad=lignes.getCell(8).getStringCellValue();
         String tl=lignes.getCell(9).getStringCellValue();
         String etb=lignes.getCell(10).getStringCellValue();
         String cr=lignes.getCell(11).getStringCellValue();
         
         
        System.out.println(); 
    //    System.out.print(dti+"\t");
        System.out.print(cd+"\t");
        System.out.print(tt+"\t");
        System.out.print(sx+"\t");
        System.out.print(nm+"\t");
        System.out.print(pr+"\t");
        System.out.print(dp+"\t");
        System.out.print(cl+"\t");
        System.out.print(ad+"\t");
        System.out.print(tl+"\t");
        System.out.print(etb+"\t");
        System.out.print(cr+"\t");
        System.out.println();
        
    }
}
