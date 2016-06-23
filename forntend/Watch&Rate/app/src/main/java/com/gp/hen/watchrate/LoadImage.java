package com.gp.hen.watchrate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by hosam azzam on 15/02/2016.
 */
public class LoadImage extends AsyncTask<String, String, Bitmap> {
    Bitmap bitmap;
    RoundImage roundimg;
    public Context context;
    public int tag = 0;

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
                DB database = new DB(context);
                database.setDATABASE_TABLE("image_local");
                database.createTables();
                String fileNameSegments[] = args[0].split("/");
                String fileName = fileNameSegments[fileNameSegments.length - 1];
                byte[] array = database.getimage("image_local", fileName);
                if (array != null) {
                    System.out.println("found local");
                    bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
                } else {
                    System.out.println("not found fetching");
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    database.insert_note("image_local", fileName, stream.toByteArray());
                    System.out.println("done inserting in db");
                }
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

