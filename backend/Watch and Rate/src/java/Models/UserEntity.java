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

    private int USER_ID;
    private String USERNAME;
    private String EMAIL;
    private String PASS;
    private double SCORE;
    private String USERIMAGE;
    private Sql sql;

    public UserEntity() {
        sql = new Sql();
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASS() {
        return PASS;
    }

    public double getSCORE() {
        return SCORE;
    }

    public String getUSERIMAGE() {
        return USERIMAGE;
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
                USER_ID = sql.ResStetmnt.getInt("USER_ID");
                EMAIL = sql.ResStetmnt.getString("EMAIL");
                PASS = sql.ResStetmnt.getString("PASS");
                USERIMAGE = sql.ResStetmnt.getString("USERIMAGE");
                USERNAME = sql.ResStetmnt.getString("USERNAME");
                SCORE = sql.ResStetmnt.getDouble("SCORE");
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
                USER_ID = sql.ResStetmnt.getInt("USER_ID");
                EMAIL = sql.ResStetmnt.getString("EMAIL");
                PASS = sql.ResStetmnt.getString("PASS");
                USERIMAGE = sql.ResStetmnt.getString("USERIMAGE");
                USERNAME = sql.ResStetmnt.getString("USERNAME");
                SCORE = sql.ResStetmnt.getDouble("SCORE");
               
            }
            else{
                USERNAME="";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return USERNAME;
    }
    String getUserimage(int userID) {
       
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from user where USER_ID=" +userID + " ");

            if (sql.ResStetmnt.next()) {
                USER_ID = sql.ResStetmnt.getInt("USER_ID");
                EMAIL = sql.ResStetmnt.getString("EMAIL");
                PASS = sql.ResStetmnt.getString("PASS");
                USERIMAGE = sql.ResStetmnt.getString("USERIMAGE");
                USERNAME = sql.ResStetmnt.getString("USERNAME");
                SCORE = sql.ResStetmnt.getDouble("SCORE");
               
            }
            else{
                USERIMAGE="none";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return USERIMAGE;
    }
    
    
    public JSONObject getHigherReviewers() {
     JSONObject res = new JSONObject();
       JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from user order by SCORE desc ");

            while (sql.ResStetmnt.next()) {
                
                JSONObject result = new JSONObject();
                USER_ID = sql.ResStetmnt.getInt("USER_ID");
                EMAIL = sql.ResStetmnt.getString("EMAIL");
                PASS = sql.ResStetmnt.getString("PASS");
                USERIMAGE = sql.ResStetmnt.getString("USERIMAGE");
                USERNAME = sql.ResStetmnt.getString("USERNAME");
                SCORE = sql.ResStetmnt.getDouble("SCORE");

                result.put("User_Id", USER_ID);
                result.put("Email", EMAIL);
                result.put("Image", USERIMAGE);
                result.put("Name", USERNAME);
                result.put("Score", SCORE);
                list.add(result);

            }
            res.put("status", "higherScore");

        } catch (SQLException ex) {
            ex.printStackTrace();
            res.put("status", "something wrong ,Try again..");
        }

        res.put("results", list);
        return res;
    }
    
    
     
     
}
