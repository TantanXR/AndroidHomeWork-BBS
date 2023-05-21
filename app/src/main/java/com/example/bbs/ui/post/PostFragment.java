package com.example.bbs.ui.post;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bbs.R;
import com.example.bbs.start;
import com.example.bbs.ui.home.HomeFragment;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.example.bbs.ui.login.Register;

import java.text.SimpleDateFormat;
import java.util.Objects;


public class PostFragment extends Fragment {

    private View root;
    private Button AddPost;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_post, null);
        }
        AddPost = root.findViewById(R.id.addPost);
        AddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost(root);
            }
        });
        return root;
    }
    public void addPost(View view){
        EditText title = root.findViewById(R.id.post_title);
        EditText content = root.findViewById(R.id.post_content);
        EditText write = root.findViewById(R.id.post_write);
        String got_title = title.getText().toString();
        String got_content = content.getText().toString();
        String got_write = write.getText().toString();

        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(root.getContext());
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()){
            if (got_title.equals("")|| got_content.equals("")||got_write.equals("")){
                if (got_title.equals("")){
                    Toast.makeText(root.getContext(),"请输入文章标题",Toast.LENGTH_SHORT).show();
                }else if (got_write.equals("")){
                    Toast.makeText(root.getContext(),"请输入匿名名称",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(root.getContext(),"请输入帖子正文",Toast.LENGTH_SHORT).show();
            }
            else{
                boolean flag = false;
                Cursor cursor = database.rawQuery("select * from posts",null);
                while (cursor.moveToNext()) {
                    String _title = cursor.getString(cursor.getColumnIndex("_title"));
                    if (_title.equals(got_title)){
                        Toast.makeText(root.getContext(),"标题重复，请重新想一个标题",Toast.LENGTH_SHORT).show();
                        title.setText("");
                        flag=true;
                    }
                }
                if (flag==false){
                    String sql = "insert into posts(_title,_content,_writer,_createTime,_username,_recentUpdateTime) values(?,?,?,?,?,?)";
                    String got_time = getNowTime();
                    database.execSQL(sql,new Object[]{got_title,got_content,got_write,got_time,"username",got_time});
                    Toast.makeText(root.getContext(),"发布成功",Toast.LENGTH_SHORT).show();
                    title.setText("");
                    content.setText("");
                    write.setText("");
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