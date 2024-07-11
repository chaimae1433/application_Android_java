package com.kaadiri.examtp2022;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Personnnns.db";
    private static final String TABLE_NAME = "Personnes";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FIRSTNAME";
    private static final String COL_3 = "LASTNAME";
    private static final String COL_4 = "BIRTHDAY";
    private static final String COL_5 = "IMAGE";
    private static final String COL_6 = "EMAIL";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table Personnes ( ID Integer PRIMARY KEY AUTOINCREMENT , FIRSTNAME text not null , LASTNAME text not null , BIRTHDAY text not null, IMAGE BLOB, EMAIL text not null);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insert (String firstname , String lastname, String birthday, byte[] image, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues personne = new ContentValues();
        personne.put(COL_2 , firstname);
        personne.put(COL_3 , lastname);
        personne.put(COL_4 , birthday);
        //personne.put(COL_5 , image);
        personne.put(COL_6 , email);
        long result = db.insert(TABLE_NAME , null, personne);
        db.close();
        if (result == -1){
            Log.d("insert","************************ L'insertion est échoué **************************");
            return false;
        }
        else {
            Log.d("insert","************************ L'insertion est réussié **************************");
            return true;
        }
    }

    public Cursor select (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return res;
    }
    public boolean update (int id , String firstname , String lastname , String birthday, byte[] image, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newPersonne = new ContentValues();
        newPersonne.put(COL_1, id);
        newPersonne.put(COL_2 , firstname);
        newPersonne.put(COL_3 , lastname);
        newPersonne.put(COL_4 , birthday);
        newPersonne.put(COL_5 , image);
        newPersonne.put(COL_6 , email);
        int res = db.update(TABLE_NAME , newPersonne , "ID = ?" , new String [] {id+""});
        if (res >0){
            Log.d("insert","************************ L'update est réussie **************************");
            return true;
        }
        else{
            Log.d("insert","************************ L'update est échoué **************************");
            return false;
        }

    }

    public Integer delete (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME , "ID = ?" , new String[] {id+""});
    }
}
