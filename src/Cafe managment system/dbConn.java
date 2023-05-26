/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxproject;

import java.sql.*;
import java.util.logging.*;



/**
 *
 * @author NorhanNasr
 */
public class dbConn {
     public static Connection DBConnection() {
        
         Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
             //cmd :
             //SQLPLUS / AS SYSDBA
             // ALTER USER HR ACCOUNT UNLOCK;
             //alter user hr identified by hr;
            conn = DriverManager.getConnection("jdbc:oracle:thin:hr/hr@localhost:1521/XE");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        return conn;
    
}
}
