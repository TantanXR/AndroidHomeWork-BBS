package com.example.bbs.ui.setting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MusicSqliteOpenHelper extends SQLiteOpenHelper{
    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getMInstance(Context context){
        if (mInstance==null){
            mInstance = new MusicSqliteOpenHelper(context,"music.db",null,1);
        }
        return mInstance;
    }
    private MusicSqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table musics(_musicName text, _musicAdult text, _musicPath text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="drop table if exists musics";
        db.execSQL(sql);
    }


}
