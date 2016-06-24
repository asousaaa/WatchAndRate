/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author HEBA-PC
 */
public class CommentNotificationEntity {
    
    private Sql sql;

    public CommentNotificationEntity() {
        sql = new Sql();
    }
    
    
    
    
    public JSONObject getNewComments(JSONObject object) {
         JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            sql.open();

            sql.Stetmnt = sql.Conection.createStatement();
            sql.ResStetmnt = sql.Stetmnt.executeQuery("SELECT * from notification where  USER_ID_RECIEVER= " + object.get("userid") + " And "
                    + "	RECIEVED = 0 ");

            boolean flage = false;
            while (sql.ResStetmnt.next()) {
                flage = true;
                JSONObject comment = new JSONObject();

                UserEntity user = new UserEntity();
                String user_name = user.getUsername(sql.ResStetmnt.getInt("USER_ID"));
                String user_image = user.getUserimage(sql.ResStetmnt.getInt("USER_ID"));
                ReviewEntity re = new ReviewEntity();
                JSONObject obj = new JSONObject();
                obj.put("reviewid", sql.ResStetmnt.getInt("REVIEW_ID"));
                JSONObject res = new JSONObject();
                res = re.getReview(obj);
                String reviewTitle = res.get("title").toString();
                String movieTitle =  res.get("movie_name").toString();
                int movieID = Integer.parseInt( res.get("movie_id").toString());
                comment.put("notification_id", sql.ResStetmnt.getInt("NOTIFICATION_ID"));
                comment.put("rev_id", sql.ResStetmnt.getInt("REVIEW_ID"));
                comment.put("review_title",reviewTitle);
                comment.put("username", user_name);
                comment.put("sender_id", sql.ResStetmnt.getInt("USER_ID_SENDER"));
                comment.put("reciever_id", sql.ResStetmnt.getInt("USER_ID_RECIEVER"));
                comment.put("commnet_id", sql.ResStetmnt.getInt("COMMENT_ID"));
                comment.put("userimage", user_image);
                comment.put("movie_name",movieTitle);
                comment.put("movie_id",movieID);
                
                sql.Stetmnt = sql.Conection.createStatement();
                sql.Stetmnt.executeUpdate("UPDATE notification SET RECIEVED=1 WHERE COMMENT_ID= " + sql.ResStetmnt.getInt("COMMENT_ID") + " ");

                list.add(comment);

            }
            ret.put("status", "commentNotification");

        } catch (SQLException ex) {
            ex.printStackTrace();
            ret.put("status", "something wrong ,Try again..");
        }

        ret.put("results", list);
        return ret;
    }
    
}
