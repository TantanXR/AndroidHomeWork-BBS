package com.example.bbs.ui.home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CommentSqliteOpenHelper extends SQLiteOpenHelper{
    private static SQLiteOpenHelper mInstance;

    public static synchronized SQLiteOpenHelper getMInstance(Context context){
        if (mInstance==null){
            mInstance = new CommentSqliteOpenHelper(context,"comment.db",null,1);
        }
        return mInstance;
    }
    private CommentSqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table comments(_postTitle text, _commentContent text, _createTime text, _username text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="drop table if exists comments";
        db.execSQL(sql);
    }


}
