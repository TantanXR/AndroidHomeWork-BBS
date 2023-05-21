package com.example.bbs.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;

public class PostDetail  extends AppCompatActivity {

    private Button cancel;
    private TextView post_title,post_write,post_createTime,post_content;

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

    }
    void init(){
        cancel = findViewById(R.id.cancel);
        post_content = findViewById(R.id.post_content);
        post_createTime = findViewById(R.id.post_createTime);
        post_write = findViewById(R.id.post_write);
        post_title = findViewById(R.id.post_title);
        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra("post");
        post_title.setText(post.getTitle());
        post_write.setText("作者名称："+post.getWrite());
        post_createTime.setText("发布时间："+post.getCreateTime());
        post_content.setText(post.getContent());
    }


}
