package com.gp.hen.watchrate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by tech 1 on 7/6/2015.
 */
public class DB extends SQLiteOpenHelper {
    String DATABASE_TABLE;

    public DB(Context context) {
        super(context, "watchandrateimage.db", null, 1);
    }

    public String getDATABASE_TABLE() {
        return DATABASE_TABLE;
    }

    public void setDATABASE_TABLE(String DATABASE_TABLE) {
        this.DATABASE_TABLE = DATABASE_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE image_local (title TEXT,image BLOB)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

    public void insert_note(String table, String title,byte[] image)  throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("image", image);
        db.insert(table, null, contentValues);
        db.close();
    }


    public byte[] getimage(String table,String title)
    {
        byte[] image = null ;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + table+" where title='"+title+"' ", null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                    image = res.getBlob(1);
                res.moveToNext();
            }
            db.close();
        }
        catch (Exception e){
            System.out.println("table not found");
            return image;
        }
        return image;
    }
    public void createTables(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(
                "create table if not exists image_local (title TEXT,image BLOB)"
        );
        db.close();
    }
}
