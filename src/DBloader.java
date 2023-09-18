/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.*;


/**
 *
 * @author acer
 */
public class DBloader {
    
        public static ResultSet executeSQL(String sql){
         try {
            //Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Driver loading done");

            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/movie_db_2023", "root", "system");
            System.out.println("Connection done");

            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Statement done");

            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ResultSet Created");
           return rs;
        
          }
          catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
