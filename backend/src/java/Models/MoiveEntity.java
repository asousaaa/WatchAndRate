package Models;


import java.sql.SQLException;
import java.util.ArrayList;
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
public class MoiveEntity {
    private int Moive_ID;
    private String Name;
    private String Year;
    private String Description;
    private double calculatedRate;
    private ArrayList<ReviewEntity> review; 
    private Sql sql;
MoiveEntity(){
    sql = new Sql();
    
}
    
    public int getMoive_ID() {
        return Moive_ID;
    }

    public void setMoive_ID(int Moive_ID) {
        this.Moive_ID = Moive_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public double getCalculatedRate() {
        return calculatedRate;
    }

    public void setCalculatedRate(double calculatedRate) {
        this.calculatedRate = calculatedRate;
    }

    public ArrayList<ReviewEntity> getReview() {
        return review;
    }

    public void setReview(ArrayList<ReviewEntity> review) {
        this.review = review;
    }

   public String getmovie(int mov_id) {
        
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from movie where API_ID=" +mov_id + " ");

            if (sql.ResStetmnt.next()) {
           
                Name = sql.ResStetmnt.getString("MOVIENAME");
              
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
           
        }
        return Name;
    } 

    boolean insertmovie(JSONObject json) throws SQLException {
       try{
         System.out.println("entered ");
         sql.open();
         
           if(!getmovie(Integer.valueOf(json.get("movieid").toString())).equals("")){
               return true;
           }
         
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("INSERT INTO movie (MOVIENAME,TYPE,YEAR,DESCRIPTION,MOVIEIMAGE,RATESYSTEM,API_ID,Story,"
                    + "Direction,Acting,Motion,Music)VALUES ('"+
                 json.get("mov_name") + "','" + json.get("type")+"','"+  json.get("year") + "','" + json.get("desc")+"','"+ 
             json.get("mov_img") + "','" + json.get("mov_rate")+"','"+ json.get("movieid")+ "','" + json.get("story")
                    + "','" + json.get("direction")+"','"+ json.get("acting")
                    + "','" + json.get("motion")+"','"+ json.get("music")+"')" );
            
            
        }catch(SQLException ex) {
            ex.printStackTrace();
           return false;
        }
        
        return true;
    }
    
    
}
