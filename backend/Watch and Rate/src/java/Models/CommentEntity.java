package Models;

import java.sql.SQLException;
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
public class CommentEntity {

    private int COMMENT_ID;
    private String COMMENT_CONTENT;
    private int USER_ID;
    private int REVIEW_ID;
    private int HASURL;
    private Sql sql;

    public CommentEntity() {
        sql = new Sql();
    }

    public int getCOMMENT_ID() {
        return COMMENT_ID;
    }

    public void setCOMMENT_ID(int COMMENT_ID) {
        this.COMMENT_ID = COMMENT_ID;
    }

    public String getCOMMENT_CONTENT() {
        return COMMENT_CONTENT;
    }

    public void setCOMMENT_CONTENT(String COMMENT_CONTENT) {
        this.COMMENT_CONTENT = COMMENT_CONTENT;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getREVIEW_ID() {
        return REVIEW_ID;
    }

    public void setREVIEW_ID(int REVIEW_ID) {
        this.REVIEW_ID = REVIEW_ID;
    }

    public JSONObject getcommentList(JSONObject json) {
        JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from comment where  REVIEW_ID= " + json.get("rev_id") + " ");

 
            while (sql.ResStetmnt.next()) {
          
                JSONObject review = new JSONObject();

                UserEntity user = new UserEntity();
                String user_name = user.getUsername(sql.ResStetmnt.getInt("USER_ID"));
                String user_image = user.getUserimage(sql.ResStetmnt.getInt("USER_ID"));

                review.put("comment_hasurl", sql.ResStetmnt.getInt("HASURL"));
                review.put("rev_id", sql.ResStetmnt.getInt("REVIEW_ID"));
                review.put("content", sql.ResStetmnt.getString("COMMENT_CONTENT"));
                review.put("username", user_name);
                review.put("userid", sql.ResStetmnt.getInt("USER_ID"));
                review.put("commnet_id", sql.ResStetmnt.getInt("COMMENT_ID"));
                review.put("userimage", user_image);

                list.add(review);

            }
            ret.put("status", "commentlist");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        ret.put("results", list);
        return ret;
    }

    public JSONObject write_comment(JSONObject json) {
        JSONObject ret = new JSONObject();

        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("INSERT INTO comment(COMMENT_CONTENT,HASURL,USER_ID,REVIEW_ID) VALUES ('"
                    + json.get("content") + "'," + json.get("hasurl") + "," + json.get("userid") + "," + json.get("revid")
                    + ")");

             sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT LAST_INSERT_ID() as last_id from comment");

            while (sql.ResStetmnt.next()) {
                COMMENT_ID = sql.ResStetmnt.getInt("last_id");

            }

            sql.close();

            ReviewEntity re = new ReviewEntity();
            JSONObject obj = new JSONObject();
            obj.put("reviewid", json.get("revid"));
            int recieverID = (int) re.getReview(obj).get("user_id");
            int senderID = Integer.parseInt(json.get("userid").toString());

            if (senderID != recieverID) {
                sql.open();
                sql.Stetmnt = sql.Conection.createStatement();
                sql.Stetmnt.executeUpdate("INSERT INTO notification (USER_ID_SENDER, USER_ID_RECIEVER, RECIEVED, REVIEW_ID, COMMENT_ID, USER_ID)VALUES  ("
                        + json.get("userid") + "," + recieverID + "," + 0 + "," + json.get("revid") + "," + COMMENT_ID + "," + json.get("userid") + ")");
                sql.close();
            }
            
            ret.put("status", "inserted");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        return ret;

    }
    
      public JSONObject deletecomment(JSONObject json) {

        JSONObject ret = new JSONObject();
        try {
            sql.open();
            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("DELETE FROM  notification where COMMENT_ID= " + json.get("comm_id") + " ");
          
            sql.Stetmnt = sql.Conection.createStatement();

            sql.Stetmnt.executeUpdate("DELETE FROM  comment where COMMENT_ID= " + json.get("comm_id") + " ");

            ret.put("status", "deleted");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "failed");
        }

        return ret;
    }

}
