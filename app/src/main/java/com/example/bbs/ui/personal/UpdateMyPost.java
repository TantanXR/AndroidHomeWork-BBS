package com.example.bbs.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.home.Post;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class UpdateMyPost extends AppCompatActivity {
    private Button cancel,updatePost;
    private EditText title,content;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_my_post);
        init();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        updatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost(v);
            }
        });

    }

    void init(){
        cancel=findViewById(R.id.cancel);
        updatePost=findViewById(R.id.updatePost);
        title = findViewById(R.id.post_title);
        content =findViewById(R.id.post_content);
        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");
        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()){
            title.setText(post.getTitle());
            content.setText(post.getContent());
        }
        database.close();
    }

    public void updatePost(View view){

        String got_title = title.getText().toString();
        String got_content = content.getText().toString();

        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()){
            if (got_title.equals("")|| got_content.equals("")){
                if (got_title.equals("")){
                    Toast.makeText(this,"请输入修改后的文章标题",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"请输入修改后的帖子正文",Toast.LENGTH_SHORT).show();
            }
            else{
                boolean flag = false;
                Cursor cursor = database.rawQuery("select * from posts",null);
                while (cursor.moveToNext()) {
                    String _title = cursor.getString(cursor.getColumnIndex("_title"));
                    if (_title.equals(got_title)){
                        Toast.makeText(this,"标题重复，请重新想一个标题",Toast.LENGTH_SHORT).show();
                        title.setText("");
                        flag=true;
                    }
                }
                if (flag==false){
                    String sql = "update posts set _title=?,_content=?,_recentUpdateTime=? where _title=?";
                    String got_updateTime = getNowTime();
                    database.execSQL(sql,new Object[]{got_title,got_content,getNowTime(),post.getTitle()});
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateMyPost.this,MyPostShow.class);
                    startActivity(intent);
                }
            }
        }
        database.close();
    }

    public static String getNowTime() {
        long getNowTimeLong = System.currentTimeMillis();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String result = time.format(getNowTimeLong);
        return result;
    }
}



