package com.example.bbs.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bbs.R;

import java.util.ArrayList;
import java.util.List;

public class CommentDetail extends AppCompatActivity {

    private List<Comment> comments = new ArrayList<Comment>();
    private TextView post_title;
    private Button cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_comment_list);
        init();
        CommentAdapter commentAdapter = new CommentAdapter(this,R.layout.comment_item_list,comments);
        //创建 MyAdapter 对象
        ListView listview = findViewById(R.id.listComments);
        //设置 Adapter
        listview.setAdapter(commentAdapter);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {
                Comment comment = comments.get(position);
                Toast.makeText(CommentDetail.this,comment.getUsername()+"评论:",Toast.LENGTH_SHORT).show();
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
        Intent intent = getIntent();
        String got_title = intent.getStringExtra("title");
        post_title = findViewById(R.id.post_title);
        post_title.setText(got_title);
        cancel=findViewById(R.id.cancel);
        SQLiteOpenHelper helper = CommentSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from comments",null);
        while (cursor.moveToNext()){
            String _postTitle = cursor.getString(cursor.getColumnIndex("_postTitle"));
            String _username = cursor.getString(cursor.getColumnIndex("_username"));
            String _commentContent = cursor.getString(cursor.getColumnIndex("_commentContent"));
            String _createTime = cursor.getString(cursor.getColumnIndex("_createTime"));
            if (got_title.equals(_postTitle)){
                Comment comment = new Comment(_postTitle, _commentContent, _createTime, _username);
                comments.add(comment);
            }
        }
    }
}
