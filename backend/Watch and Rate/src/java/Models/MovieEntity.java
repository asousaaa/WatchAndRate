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

    boolean insertmovie(JSONObject json) throws SQLException {
        try {
          
            sql.open();

            if (!getmovie(Integer.valueOf(json.get("movieid").toString())).equals("")) {
                return true;
            }

            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("INSERT INTO movie (MOVIENAME,TYPE,YEAR,DESCRIPTION,MOVIEIMAGE,RATESYSTEM,API_ID,Story,"
                    + "Direction,Acting,Motion,Music,VOTE_COUNT)VALUES ('"
                    + json.get("mov_name") + "','" + json.get("type") + "','" + json.get("year") + "','" + json.get("desc") + "','"
                    + json.get("mov_img") + "','" + json.get("mov_rate") + "','" + json.get("movieid") + "','" + json.get("story")
                    + "','" + json.get("direction") + "','" + json.get("acting")
                    + "','" + json.get("motion") + "','" + json.get("music") + "'," + json.get("mov_vote") + ")");

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
                ret.put("id", sql.ResStetmnt.getInt("API_ID"));
                ret.put("vote_average", sql.ResStetmnt.getDouble("RATESYSTEM"));
                ret.put("vote_count", sql.ResStetmnt.getInt("VOTE_COUNT"));
                ret.put("story", sql.ResStetmnt.getString("Story"));
                ret.put("direction", sql.ResStetmnt.getString("Direction"));
                ret.put("acting", sql.ResStetmnt.getString("Acting"));
                ret.put("motion", sql.ResStetmnt.getString("Motion"));
                ret.put("music", sql.ResStetmnt.getString("Music"));
                ret.put("mov_id", sql.ResStetmnt.getInt("MOVIE_ID"));

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
