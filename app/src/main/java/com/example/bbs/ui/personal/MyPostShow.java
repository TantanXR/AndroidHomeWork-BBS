package com.example.bbs.ui.personal;

import android.content.Intent;
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
import com.example.bbs.ui.home.Post;
import com.example.bbs.ui.login.User;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyPostShow extends AppCompatActivity {

    private List<Post> posts = new ArrayList<Post>();
    private Button cancel;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_my_post_list);
        init();
        MyPostAdapter myCommentAdapter = new MyPostAdapter(this,R.layout.post_item_list,posts);
        //创建 MyAdapter 对象
        ListView listview = findViewById(R.id.listMyPosts);
        //设置 Adapter
        listview.setAdapter(myCommentAdapter);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //跳转到文章详情页面
                Intent intent = new Intent(MyPostShow.this,MyPostDetail.class);
                intent.putExtra("post",posts.get(position));
                intent.putExtra("user",user);
                startActivity(intent);
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

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from posts",null);
        while (cursor.moveToNext()){
            String _username = cursor.getString(cursor.getColumnIndex("_username"));
            String _title = cursor.getString(cursor.getColumnIndex("_title"));
            String _write = cursor.getString(cursor.getColumnIndex("_writer"));
            String _content = cursor.getString(cursor.getColumnIndex("_content"));
            String _createTime = cursor.getString(cursor.getColumnIndex("_createTime"));
            String _recentUpdateTime = cursor.getString(cursor.getColumnIndex("_recentUpdateTime"));
            if (_username.equals(user.getUserName())){
                Post post = new Post(_title,_write,_createTime,_content,_recentUpdateTime);
                posts.add(post);
            }
        }
    }
}
