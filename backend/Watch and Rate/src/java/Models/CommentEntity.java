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

    private int commentID;
    private String Body;
    private int UserID;
    private int reviewerID;
    private Sql sql;

    public CommentEntity() {
        sql = new Sql();
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(int reviewerID) {
        this.reviewerID = reviewerID;
    }

    public JSONObject getcommentList(JSONObject json) {
        JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from comment where  REVIEW_ID= " + json.get("rev_id") + " ");

            boolean flage = false;
            while (sql.ResStetmnt.next()) {
                flage = true;
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
            System.out.println("entered");
            sql.Stetmnt = sql.Conection.createStatement();
            sql.Stetmnt.executeUpdate("INSERT INTO comment(COMMENT_CONTENT,HASURL,USER_ID,REVIEW_ID) VALUES ('"
                    + json.get("content") + "'," + json.get("hasurl") + "," + json.get("userid") + "," + json.get("revid")
                    + ")");

            ret.put("status", "inserted");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        return ret;

    }

}
