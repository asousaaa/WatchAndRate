package Models;

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
public class ReviewEntity {

    private int REVIEW_ID;
    private String TITLE;
    private String REVIEW_CONTENT;
    private float STAR_RATE;
    private int USER_ID;
    private int ENABLE_COMMENT;
    private String IMAGE_REVIEW;
    private int MOVIE_ID;
    private String DATE;
    private Sql sql;

    public ReviewEntity() {
        sql = new Sql();
    }

    public int getREVIEW_ID() {
        return REVIEW_ID;
    }

    public void setREVIEW_ID(int REVIEW_ID) {
        this.REVIEW_ID = REVIEW_ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getREVIEW_CONTENT() {
        return REVIEW_CONTENT;
    }

    public void setREVIEW_CONTENT(String REVIEW_CONTENT) {
        this.REVIEW_CONTENT = REVIEW_CONTENT;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDate() {
        return DATE;
    }

    public void setDate(String date) {
        this.DATE = date;
    }

    public JSONObject getUserReviews(JSONObject json) {
        JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from review where  USER_ID= " + json.get("userid") + " order by DATE desc ");

            while (sql.ResStetmnt.next()) {

                JSONObject review = new JSONObject();
                TITLE = sql.ResStetmnt.getString("TITLE");
                REVIEW_ID = sql.ResStetmnt.getInt("REVIEW_ID");
                DATE = sql.ResStetmnt.getString("DATE");
                REVIEW_CONTENT = sql.ResStetmnt.getString("REVIEW_CONTENT");
                MOVIE_ID = sql.ResStetmnt.getInt("MOVIE_ID");
                MovieEntity moiveEntity = new MovieEntity();

                String mov_name = moiveEntity.getmovie(MOVIE_ID);

                review.put("content", REVIEW_CONTENT);
                review.put("mov_name", mov_name);
                review.put("date", DATE);
                review.put("review_title", TITLE);
                review.put("mov_id", MOVIE_ID);
                review.put("rev_id", REVIEW_ID);
                list.add(review);

            }
            ret.put("status", "ownreview");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        ret.put("results", list);
        return ret;
    }

    public JSONObject writeReview(JSONObject json) throws SQLException {

        JSONObject ret = new JSONObject();

        MovieEntity movie = new MovieEntity();
        if (movie.insertmovie(json)) {

            try {

                sql.open();
                
                sql.Stetmnt = sql.Conection.createStatement();
                sql.Stetmnt.executeUpdate("INSERT INTO review(TITLE,REVIEW_CONTENT ,IMAGE_REVIEW,STAR_RATE,USER_ID,MOVIE_ID,DATE,ENABLE_COMMENT) VALUES ('"
                        + json.get("title") + "','" + json.get("content") + "','" + json.get("image") + "','" + json.get("rate") + "','"
                        + json.get("userid") + "','" + json.get("movieid") + "','" + json.get("date") + "'," + json.get("enable") + ")");

                ret.put("status", "inserted");

            } catch (SQLException ex) {
                ex.printStackTrace();
                ret.put("status", "something wrong ,Try again..");
            }

        } else {

            ret.put("status", "error add movie to database");
        }

        return ret;
    }
	
	public JSONObject getReview(JSONObject json) {
        
        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * FROM  review where  REVIEW_ID = " + json.get("reviewid") + " ");
            
            boolean flage = false;
            while (sql.ResStetmnt.next()) {
                flage = true;

                int mov_id = sql.ResStetmnt.getInt("MOVIE_ID");
                MovieEntity moiveEntity = new MovieEntity();

                String mov_name = moiveEntity.getmovie(mov_id);
                USER_ID = sql.ResStetmnt.getInt("USER_ID");
                TITLE = sql.ResStetmnt.getString("TITLE");
                ret.put("user_id", USER_ID);
                ret.put("title", TITLE);
                ret.put("movie_id", mov_id);
                ret.put("movie_name", mov_name);

            }
            sql.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        return ret;
    }

    public JSONObject deleteReview(JSONObject json) {

        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("DELETE FROM  review where USER_ID= " + json.get("userid") + " and REVIEW_ID = " + json.get("reviewid") + " ");

            ret.put("status", "deleted");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "failed");
        }

        return ret;
    }

    public JSONObject getReviewList(JSONObject json) {
        JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from review where  MOVIE_ID= " + json.get("movieid") + " order by DATE desc ");

            while (sql.ResStetmnt.next()) {

                JSONObject review = new JSONObject();
                TITLE = sql.ResStetmnt.getString("TITLE");
                REVIEW_ID = sql.ResStetmnt.getInt("REVIEW_ID");
                DATE = sql.ResStetmnt.getString("DATE");
                REVIEW_CONTENT = sql.ResStetmnt.getString("REVIEW_CONTENT");
                USER_ID = sql.ResStetmnt.getInt("USER_ID");

                UserEntity user = new UserEntity();
                String user_name = user.getUsername(USER_ID);

                int mov_id = sql.ResStetmnt.getInt("MOVIE_ID");
                MovieEntity moiveEntity = new MovieEntity();

                String mov_name = moiveEntity.getmovie(mov_id);

                review.put("content", REVIEW_CONTENT);
                review.put("mov_name", mov_name);
                review.put("date", DATE);
                review.put("userid", USER_ID);
                review.put("username", user_name);
                review.put("review_title", TITLE);
                review.put("mov_id", mov_id);
                review.put("rev_id", REVIEW_ID);
                list.add(review);

            }
            ret.put("status", "reviewlist");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        ret.put("results", list);
        return ret;

    }

    public JSONObject getreviewinfo(JSONObject json) {
        JSONObject ret = new JSONObject();

        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from review where  REVIEW_ID= " + json.get("rev_id") + " ");

            if (sql.ResStetmnt.next()) {
                TITLE = sql.ResStetmnt.getString("TITLE");
                REVIEW_ID = sql.ResStetmnt.getInt("REVIEW_ID");
                DATE = sql.ResStetmnt.getString("DATE");
                REVIEW_CONTENT = sql.ResStetmnt.getString("REVIEW_CONTENT");
                STAR_RATE = sql.ResStetmnt.getFloat("STAR_RATE");
                ENABLE_COMMENT =  sql.ResStetmnt.getInt("ENABLE_COMMENT");
                IMAGE_REVIEW = sql.ResStetmnt.getString("IMAGE_REVIEW");
                USER_ID = sql.ResStetmnt.getInt("USER_ID");

                UserEntity user = new UserEntity();
                String user_name = user.getUsername(USER_ID);

                MOVIE_ID = sql.ResStetmnt.getInt("MOVIE_ID");
                MovieEntity moiveEntity = new MovieEntity();

                String mov_name = moiveEntity.getmovie(MOVIE_ID);

                ret.put("content", REVIEW_CONTENT);
                ret.put("mov_name", mov_name);
                ret.put("date", DATE);
                ret.put("userid", USER_ID);
                ret.put("username", user_name);
                ret.put("review_title", TITLE);
                ret.put("review_rate", STAR_RATE);
                ret.put("mov_id", MOVIE_ID);
                ret.put("rev_id", REVIEW_ID);
                ret.put("rev_comment", ENABLE_COMMENT);
                ret.put("rev_img", IMAGE_REVIEW);
                ret.put("status", "reviewinfo");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        return ret;
    }

}
