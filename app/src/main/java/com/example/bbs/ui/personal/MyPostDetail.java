package com.example.bbs.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.start;
import com.example.bbs.ui.home.CommentDetail;
import com.example.bbs.ui.home.Post;
import com.example.bbs.ui.login.Register;
import com.example.bbs.ui.login.User;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

public class MyPostDetail extends AppCompatActivity {

    private Button cancel,update_myPost,delete_myPost;
    private TextView post_title,post_createTime,post_content,recent_update_time;
    private ImageButton show_comment;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_post_detail);
        init();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostDetail.this, CommentDetail.class);
                intent.putExtra("title",post_title.getText());
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        update_myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostDetail.this,UpdateMyPost.class);
                Intent intent1 = getIntent();
                Post post = (Post) intent1.getSerializableExtra("post");
                intent.putExtra("post",post);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        delete_myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMyPost();
            }
        });

    }
    void init(){
        cancel = findViewById(R.id.cancel);
        post_content = findViewById(R.id.post_content);
        post_createTime = findViewById(R.id.post_createTime);
        post_title = findViewById(R.id.post_title);
        recent_update_time = findViewById(R.id.recent_update_time);
        show_comment = findViewById(R.id.show_comment);
        update_myPost=findViewById(R.id.update_myPost);
        delete_myPost=findViewById(R.id.delete_myPost);
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        post_title.setText(post.getTitle());
        post_createTime.setText("发布时间:"+post.getCreateTime());
        post_content.setText(post.getContent());
        recent_update_time.setText("最近更新:"+post.getRecentUpdateTime());
        user = (User)intent.getSerializableExtra("user");
        show_comment.setImageResource(R.drawable.comment_green);
    }

    public void deleteMyPost(){
        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from posts",null);
        while (cursor.moveToNext()){
            String _title = cursor.getString(cursor.getColumnIndex("_title"));
            if (_title.equals(post_title.getText())){
                String sql = "delete from posts where _title = ? ";
                db.execSQL(sql,new Object[]{_title});
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyPostDetail.this, MyPostShow.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        }
    }

}
