package com.gp.hen.watchrate;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hosam azzam on 11/02/2016.
 */
public class Connection {
    private String result = "";


   public  String sendrequest(String urls , String data) {
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL(urls);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + " ");
            }
            result = sb.toString();
          //  System.out.println("gg "+result);
            // Append Server Response To Content String

        } catch (Exception ex) {
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }
        return result;
    }
}
