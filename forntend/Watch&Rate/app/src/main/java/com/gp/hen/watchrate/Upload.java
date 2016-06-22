package com.gp.hen.watchrate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.gp.hen.watchrate.activity.add_review;
import com.gp.hen.watchrate.activity.user_profile;
import com.gp.hen.watchrate.model.UserEntity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;


/**
 * Created by tech 1 on 7/15/2015.
 */
public class Upload {
    UserEntity user;
    String encodedString;
    RequestParams params = new RequestParams();
    public String imgPath, fileName;
    Bitmap bitmap;
    public user_profile profile;
    public add_review review;
    public String uploadtime;
    public String type;

    // When Upload button is clicked
    public void uploadImage() {
        // When Image is selected from Gallery
        user = UserEntity.getCurrentUser();
        if (imgPath != null && !imgPath.isEmpty()) {

            // Convert image to String using Base64
            String fileNameSegments[] = imgPath.split("/");
            fileName = fileNameSegments[fileNameSegments.length - 1];
            fileName = fileName.replaceAll(" ", "_");
            // Put file name in Async Http Post Param which will used in Php web app
            if (type.equals("review")) {
                params.put("filename", user.getUser_Id() + uploadtime + fileName);
            } else {
                params.put("filename", user.getUser_Id() + fileName);
            }
            System.out.println("filename1 " + user.getUser_Id() + fileName);
            encodeImagetoString();


            // When Image is not selected from Gallery
        }
    }

    // AsyncTask - To convert Image to String
    public void encodeImagetoString() {

        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }


            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeFile(imgPath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy

                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                } catch (Exception e) {
                    System.out.println("error PNG");
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                } catch (Exception e) {
                    System.out.println("eroor jpeg");
                }


                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {

        //   prgDialog.setMessage("Finishing");
        makeHTTPCall();
    }

    // http://192.168.2.4:9000/imgupload/upload_image.php
    // http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(60000);
        // Don't forget to change the IP address to your LAN address. Port no as well.
        String URL = "http://watchandrateimage.comxa.com/";
        //     URL="http://10.0.3.2/Blog_db/";

        if (type == "user") {

            client.post(URL + "img_upload_user.php",

                    params, new AsyncHttpResponseHandler() {
                        // When the response returned by REST has Http
                        // response code '200'
                        @Override
                        public void onSuccess(String response) {
                            // Hide Progress Dialog
                            user.setImage(user.getUser_Id() + fileName);
                            profile.doneUploadImageState(1);
                            System.out.println(response);

                        }


                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            System.out.println(content);
                            profile.doneUploadImageState(0);

                        }
                    });
        } else {
            client.post(URL + "img_upload_post.php",

                    params, new AsyncHttpResponseHandler() {
                        // When the response returned by REST has Http
                        // response code '200'
                        @Override
                        public void onSuccess(String response) {
                            // Hide Progress Dialog
                                review.doneUploadImageState(1);
                            System.out.println("response "+ response);

                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            //   System.out.println(content);
                               review.doneUploadImageState(0);
                        }
                    });
        }

    }

}