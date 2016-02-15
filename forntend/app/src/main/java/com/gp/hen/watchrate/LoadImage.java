package com.gp.hen.watchrate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by hosam azzam on 15/02/2016.
 */
public class LoadImage extends AsyncTask<String, String, Bitmap> {
    Bitmap bitmap;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    ImageView img;

    public void Image(ImageView image) {
        img = image;
    }

    protected Bitmap doInBackground(String... args) {
        try {
            if (!args[0].equals("http://image.tmdb.org/t/p/w300null")) {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap image) {


        if (image != null) {
            img.setImageBitmap(image);

        }
    }


}

