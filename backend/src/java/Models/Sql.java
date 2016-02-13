/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.*;

/**
 *
 * @author hosam azzam
 */
public class Sql {
      String url = "jdbc:mysql://localhost:3306/watchandrate";
        String user = "root";
        String pass = "123456";
        String Line;
        Connection Conection = null;
        Statement Stetmnt = null;
        ResultSet ResStetmnt = null;
        
       public Sql() throws ClassNotFoundException{
           Class.forName("com.mysql.jdbc.Driver");
       } 
       public void open() throws SQLException{
              Conection = DriverManager.getConnection(url, user, pass);
       }
       public void close() throws SQLException{
           Conection.close();
       }
        
}
