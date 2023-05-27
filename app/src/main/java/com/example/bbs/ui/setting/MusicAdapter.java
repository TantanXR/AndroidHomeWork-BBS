package com.example.bbs.ui.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bbs.R;

import java.util.List;

public class MusicAdapter extends BaseAdapter {

    private int resource; //列表项布局
    private List<Music> data; //数据集合
    private Context context;

    //构造方法：上下文对象，布局文件，数据
    public MusicAdapter(Context context, int resource, List<Music> data) {
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
        Music music = data.get(position);
        //加载 item_list.xml 布局文件
        View view = View.inflate(context,resource,null);
        TextView music_name = view.findViewById(R.id.music_name);
        TextView music_adult = view.findViewById(R.id.music_adult);
        //将数据设置到控件上
        music_name.setText("歌曲名称："+music.getMusic_name());
        music_adult.setText("歌曲作者："+music.getMusic_adult());

        return view;
    }

}

