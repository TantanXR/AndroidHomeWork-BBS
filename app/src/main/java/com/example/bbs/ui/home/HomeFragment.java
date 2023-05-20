package com.example.bbs.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bbs.R;
import com.example.bbs.databinding.FragmentHomeBinding;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.example.bbs.ui.personal.UpdatePassword;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Post> posts = new ArrayList<Post>();
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_home, null);
        }
        init();
        PostAdapter postAdapter = new PostAdapter(root.getContext(), R.layout.post_item_list, posts);
        //创建 MyAdapter 对象
        ListView listview = root.findViewById(R.id.listPosts);
        //设置 Adapter
        listview.setAdapter(postAdapter);
        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {
                Post post = posts.get(position);
                Toast.makeText(root.getContext(), post.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    void init(){
        SQLiteOpenHelper helper = PostSqliteOpenHelper.getMInstance(root.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from posts",null);
        while (cursor.moveToNext()){
            String _title = cursor.getString(cursor.getColumnIndex("_title"));
            String _write = cursor.getString(cursor.getColumnIndex("_writer"));
            String _content = cursor.getString(cursor.getColumnIndex("_content"));
            Post post = new Post(_title,_write,"now",_content);
            posts.add(post);
        }
    }


}