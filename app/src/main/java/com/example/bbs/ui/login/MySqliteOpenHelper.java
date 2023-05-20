package com.example.bbs.ui.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteOpenHelper extends SQLiteOpenHelper{
    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getMInstance(Context context){
        if (mInstance==null){
            mInstance = new MySqliteOpenHelper(context,"derryDB.db",null,1);
        }
        return mInstance;
    }
    private MySqliteOpenHelper(@Nullable Context context, @Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table users(_account text,_password text,_content text,_image text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="drop table if exists users";
        db.execSQL(sql);
    }


}
