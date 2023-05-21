package com.example.bbs.ui.post;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PostSqliteOpenHelper extends SQLiteOpenHelper{
    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getMInstance(Context context){
        if (mInstance==null){
            mInstance = new PostSqliteOpenHelper(context,"post.db",null,1);
        }
        return mInstance;
    }
    private PostSqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table posts(_title text, _content text, _writer text, _createTime text, _username text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="drop table if exists posts";
        db.execSQL(sql);
    }


}
