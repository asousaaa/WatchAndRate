package Models;

import java.util.ArrayList;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hosam azzam
 */
public class UserEntity {

    private int User_Id;
    private String Name;
    private String Email;
    private String Pass;
    private double Score;
    private String image;
    private ArrayList<ReviewEntity> review;
    private Sql sql;

    public UserEntity() {
        sql = new Sql();
    }

    public int getUser_Id() {
        return User_Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPass() {
        return Pass;
    }

    public double getScore() {
        return Score;
    }

    public String getImage() {
        return image;
    }

    public String saveUser(JSONObject json) {
        String result = "";
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("INSERT INTO user(PASS,USERNAME,EMAIL) VALUES('"
                    + json.get("pass") + "','"
                    + json.get("username") + "','"
                    + json.get("email") + "')");
            result = "done";
            sql.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = "fail";
        }
        return result;
    }

    public JSONObject getUser(JSONObject json) {
        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from user where EMAIL='" + json.get("email") + "' and PASS='" + json.get("pass") + "'");

            if (sql.ResStetmnt.next()) {
                User_Id = sql.ResStetmnt.getInt("USER_ID");
                Email = sql.ResStetmnt.getString("EMAIL");
                Pass = sql.ResStetmnt.getString("PASS");
                image = sql.ResStetmnt.getString("USERIMAGE");
                Name = sql.ResStetmnt.getString("USERNAME");
                Score = sql.ResStetmnt.getDouble("SCORE");
                ret.put("status", "login");
            } else {
                ret.put("status", "something wrong ,Try again..");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }
        return ret;
    }

    public JSONObject updateInfo(JSONObject json) {

        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("UPDATE  user set EMAIL='" + json.get("email") + "' , PASS='" + json.get("pass")
                    + "' , USERNAME ='" + json.get("username") + "' , USERIMAGE='" + json.get("userimage")
                    + "' where USER_ID= " + json.get("userid") + " ");
            ret.put("status", "updated");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "failed");
        }

        return ret;
    }

    public JSONObject deleteAccount(JSONObject json) {

        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("DELETE FROM  comment where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  comment_notification where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  notification where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  rate where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  rate_notification where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  review where USER_ID= " + json.get("userid") + " ");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("DELETE FROM  user where USER_ID= " + json.get("userid") + " ");

            ret.put("status", "deleted");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "failed");
        }

        return ret;
    }

    String getUsername(int userID) {
       
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from user where USER_ID=" +userID + " ");

            if (sql.ResStetmnt.next()) {
                User_Id = sql.ResStetmnt.getInt("USER_ID");
                Email = sql.ResStetmnt.getString("EMAIL");
                Pass = sql.ResStetmnt.getString("PASS");
                image = sql.ResStetmnt.getString("USERIMAGE");
                Name = sql.ResStetmnt.getString("USERNAME");
                Score = sql.ResStetmnt.getDouble("SCORE");
               
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return Name;
    }
    String getUserimage(int userID) {
       
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from user where USER_ID=" +userID + " ");

            if (sql.ResStetmnt.next()) {
                User_Id = sql.ResStetmnt.getInt("USER_ID");
                Email = sql.ResStetmnt.getString("EMAIL");
                Pass = sql.ResStetmnt.getString("PASS");
                image = sql.ResStetmnt.getString("USERIMAGE");
                Name = sql.ResStetmnt.getString("USERNAME");
                Score = sql.ResStetmnt.getDouble("SCORE");
               
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return image;
    }
    
    
    
     
     
}
