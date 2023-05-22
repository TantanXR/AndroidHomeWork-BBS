package com.example.bbs.ui.personal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.home.Comment;
import com.example.bbs.ui.home.CommentSqliteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyCommentDetail extends AppCompatActivity {

    private List<Comment> comments = new ArrayList<Comment>();
    private Button cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_my_comment_list);
        init();
        MyCommentAdapter myCommentAdapter = new MyCommentAdapter(this,R.layout.mycomment_item_list,comments);
        //创建 MyAdapter 对象
        ListView listview = findViewById(R.id.listMyComments);
        //设置 Adapter
        listview.setAdapter(myCommentAdapter);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {
                //跳转到文章详情页面
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    void init(){
        cancel=findViewById(R.id.cancel);
        SQLiteOpenHelper helper = CommentSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from comments",null);
        while (cursor.moveToNext()){
            String _postTitle = cursor.getString(cursor.getColumnIndex("_postTitle"));
            String _username = cursor.getString(cursor.getColumnIndex("_username"));
            String _commentContent = cursor.getString(cursor.getColumnIndex("_commentContent"));
            String _createTime = cursor.getString(cursor.getColumnIndex("_createTime"));
            if (_username.equals("匿名用户提交评论")){
                Comment comment = new Comment(_postTitle, _commentContent, _createTime, _username);
                comments.add(comment);
            }
        }
    }
}
