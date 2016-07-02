package Models;

import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hosam azzam
 */
public class MovieEntity {

    private int MOVIE_ID;
    private int API_ID;
    private String MOVIENAME;
    private String YEAR;
    private String TYPE;
    private String DESCRIPTION;
    private String MOVIEIMAGE;
    private double RATESYSTEM;
    private int VOTE_COUNT;
    private String Story;
    private String Direction;
    private String Acting;
    private String Motion;
    private String Music;
    private Sql sql;

    public MovieEntity() {
        sql = new Sql();

    }

    public int getMOVIE_ID() {
        return MOVIE_ID;
    }

    public void setMOVIE_ID(int MOVIE_ID) {
        this.MOVIE_ID = MOVIE_ID;
    }

    public String getMOVIENAME() {
        return MOVIENAME;
    }

    public void setMOVIENAME(String MOVIENAME) {
        this.MOVIENAME = MOVIENAME;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public double getRATESYSTEM() {
        return RATESYSTEM;
    }

    public void setRATESYSTEM(double RATESYSTEM) {
        this.RATESYSTEM = RATESYSTEM;
    }

    public String getmovie(int mov_id) {

        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from movie where API_ID=" + mov_id + " ");

            if (sql.ResStetmnt.next()) {
                MOVIENAME = sql.ResStetmnt.getString("MOVIENAME");
            } else {
                MOVIENAME = "";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return MOVIENAME;
    }

    public JSONObject calculateRate(JSONObject obj) throws ParseException {
        JSONObject result = new JSONObject();
        ReviewEntity re = new ReviewEntity();
        JSONObject revList = re.getReviewList(obj);
        
        String data = revList.get("results").toString();
        JSONParser parser = new JSONParser();
        Object object =  parser.parse(data);
        JSONArray arr = (JSONArray) object;
        float summation = 0.0f;
        for (int i = 0; i < arr.size(); i++) {
            JSONObject rateObj = (JSONObject) parser.parse(arr.get(i).toString());
            float rate = Float.parseFloat(rateObj.get("starrate").toString());
            summation += rate;
        }
        summation = summation/(arr.size());
       result.put("rate", summation);
        MOVIE_ID = Integer.parseInt( obj.get("movieid").toString());
        result.put("movieid", MOVIE_ID);
        result.put("status", "rated");
       
        return result;
    }

    boolean insertmovie(JSONObject json) throws SQLException {
        try {

            sql.open();

            if (!getmovie(Integer.valueOf(json.get("movieid").toString())).equals("")) {
                return true;
            }

            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("INSERT INTO movie (MOVIENAME,TYPE,YEAR,DESCRIPTION,MOVIEIMAGE,RATESYSTEM,API_ID,Story,"
                    + "Direction,VOTE_COUNT)VALUES ('"
                    + json.get("mov_name") + "','" + json.get("type") + "','" + json.get("year") + "','" + json.get("desc") + "','"
                    + json.get("mov_img") + "','" + json.get("mov_rate") + "','" + json.get("movieid") + "','" + json.get("story")
                    + "','" + json.get("direction") + "','" + json.get("mov_vote") + ")");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public JSONObject getmovie(JSONObject json) throws SQLException {

        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from movie where API_ID=" + json.get("movieid") + " ");

            if (sql.ResStetmnt.next()) {
                ret.put("original_title", sql.ResStetmnt.getString("MOVIENAME"));
                ret.put("mov_type", sql.ResStetmnt.getString("TYPE"));
                ret.put("overview", sql.ResStetmnt.getString("DESCRIPTION"));
                ret.put("release_date", sql.ResStetmnt.getString("YEAR"));
                ret.put("poster_path", sql.ResStetmnt.getString("MOVIEIMAGE"));
                ret.put("movieid", sql.ResStetmnt.getInt("API_ID"));
                ret.put("vote_average", sql.ResStetmnt.getDouble("RATESYSTEM"));
                ret.put("vote_count", sql.ResStetmnt.getInt("VOTE_COUNT"));
                ret.put("mov_id", sql.ResStetmnt.getInt("MOVIE_ID"));
                ReviewEntity re = new ReviewEntity();
                JSONObject obj = new JSONObject();
                obj = re.getReviewList(ret);

                ret.put("story", obj.get("story"));
                ret.put("direction", obj.get("direction"));

                ret.put("status", "movie");
            } else {
                ret.put("status", "something wrong ,Try again..");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }
        return ret;

    }

    

}
