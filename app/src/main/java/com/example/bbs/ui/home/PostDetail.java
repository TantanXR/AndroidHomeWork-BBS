package com.example.bbs.ui.home;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.login.User;

import java.text.SimpleDateFormat;

public class PostDetail  extends AppCompatActivity {

    private Button cancel,comment;
    private TextView post_title,post_write,post_createTime,post_content,recent_update_time;
    private EditText comment_content;
    private ImageButton show_comment;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        init();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        show_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetail.this,CommentDetail.class);
                intent.putExtra("title",post_title.getText());
                startActivity(intent);
            }
        });

    }
    void init(){
        cancel = findViewById(R.id.cancel);
        post_content = findViewById(R.id.post_content);
        post_createTime = findViewById(R.id.post_createTime);
        post_write = findViewById(R.id.post_write);
        post_title = findViewById(R.id.post_title);
        recent_update_time = findViewById(R.id.recent_update_time);
        comment = findViewById(R.id.comment);
        comment_content = findViewById(R.id.comment_content);
        show_comment = findViewById(R.id.show_comment);
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        post_title.setText(post.getTitle());
        post_write.setText("作者名称:"+post.getWrite());
        post_createTime.setText("发布时间:"+post.getCreateTime());
        post_content.setText(post.getContent());
        recent_update_time.setText("最近更新:"+post.getRecentUpdateTime());
        user=(User)intent.getSerializableExtra("user");
    }

    void addComment(){
        String content = comment_content.getText().toString();
        if (content.equals("")){
            Toast.makeText(this,"请不要提交空评论",Toast.LENGTH_SHORT).show();
        }else{
            SQLiteOpenHelper helper = CommentSqliteOpenHelper.getMInstance(this);
            SQLiteDatabase database = helper.getReadableDatabase();
            String sql = "insert into comments(_postTitle, _commentContent, _createTime, _username) values(?,?,?,?)";
            database.execSQL(sql,new Object[]{post_title.getText(),content,getNowTime(),user.getUserName()});
            Toast.makeText(this,"评论发布成功",Toast.LENGTH_SHORT).show();
            comment_content.setText("");
        }
    }
    public static String getNowTime() {
        long getNowTimeLong = System.currentTimeMillis();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String result = time.format(getNowTimeLong);
        return result;
    }

}
