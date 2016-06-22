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
    RoundImage roundimg;
    public int tag=0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    ImageView img;

    public void Image(ImageView image) {
        img = image;
        roundimg = new RoundImage();
    }

    protected Bitmap doInBackground(String... args) {
        try {
            if (!args[0].equals("http://image.tmdb.org/t/p/w300null")) {
                System.out.println(args[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                if (args[0].contains("http://watchandrateimage.comxa.com/User_image/")) {
                    if (tag == 1)
                        bitmap = roundimg.getCircleBitmap(bitmap);
                }
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

