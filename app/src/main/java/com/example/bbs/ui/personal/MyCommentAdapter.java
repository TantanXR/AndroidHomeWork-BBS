package com.example.bbs.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbs.R;
import com.example.bbs.ui.home.Comment;
import com.example.bbs.ui.home.CommentSqliteOpenHelper;
import com.example.bbs.ui.login.User;
import com.example.bbs.ui.post.PostSqliteOpenHelper;

import java.util.List;

public class MyCommentAdapter extends BaseAdapter {

    private int resource; //列表项布局
    private List<Comment> data; //数据集合
    private Context context;
    private User user;
    //构造方法：上下文对象，布局文件，数据
    public MyCommentAdapter(Context context, int resource, List<Comment> data, User user) {
        this.resource = resource;
        this.data = data;
        this.context = context;
        this.user = user;
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
        Comment comment = data.get(position);
        //加载 item_list.xml 布局文件 _postTitle text, _commentContent text, _createTime text, _username text
        View view = View.inflate(context,resource,null);
        TextView commentContent = view.findViewById(R.id.comment_content);
        TextView createTime=view.findViewById(R.id.comment_createTime);
        Button delete = view.findViewById(R.id.delete_my_comment);

        //将数据设置到控件上
        commentContent.setText(comment.getCommentContent());
        createTime.setText(comment.getCreateTime());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper helper = CommentSqliteOpenHelper.getMInstance(view.getContext());
                SQLiteDatabase db = helper.getReadableDatabase();
                if (user.getUserName().equals(comment.getUsername())){
                    String sql = "delete from comments where _commentContent = ? and _createTime = ? ";
                    db.execSQL(sql,new Object[]{comment.getCommentContent(),comment.getCreateTime()});
                    Toast.makeText(view.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    data.remove(position);
                    MyCommentDetail.instance.setCommentsDate(data);
                }
            }
        });
        return view;
    }

}
