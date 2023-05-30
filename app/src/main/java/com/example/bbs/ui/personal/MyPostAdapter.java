package com.example.bbs.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbs.R;
import com.example.bbs.start;
import com.example.bbs.ui.home.Post;
import com.example.bbs.ui.login.Register;
import com.example.bbs.ui.login.User;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

import java.util.List;

public class MyPostAdapter extends BaseAdapter {

    private int resource; //列表项布局
    private List<Post> data; //数据集合
    private Context context;
    //构造方法：上下文对象，布局文件，数据
    public MyPostAdapter(Context context, int resource, List<Post> data) {
        this.resource = resource;
        this.data = data;
        this.context = context;
    }
    //获取列表项的个数   个数
    @Override
    public int getCount() {
        return data.size();
    }
    //获取 position 位置上的列表项对象     列表项条目
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    //获取 position 位置上的列表项的 id   第几个列表项
    @Override
    public long getItemId(int position) {
        return position;
    }
    //获取 position 位置上的列表项视图
    //自动调取getView方法，每条数据调用一次，把position位置上的数据放到列表项中
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取 position 位置上的数据对象
        Post post = data.get(position);
        //加载 item_list.xml 布局文件
        View view = View.inflate(context,resource,null);
        TextView post_title = view.findViewById(R.id.post_title);
        TextView post_content=view.findViewById(R.id.post_content);
        TextView post_createTime = view.findViewById(R.id.post_createTime);
        TextView post_write = view.findViewById(R.id.post_write);
        //将数据设置到控件上
        post_title.setText(post.getTitle());
        post_content.setText(post.getContent());
        post_createTime.setText(post.getCreateTime());
        post_write.setText(post.getWrite());
        return view;
    }
}
