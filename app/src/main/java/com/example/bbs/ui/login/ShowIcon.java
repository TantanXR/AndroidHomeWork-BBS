package com.example.bbs.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowIcon extends AppCompatActivity {
    GridView gridView;
    SimpleAdapter simpleAdapter;
    List<Map<String,Object>> list;
    int[] images={R.drawable.image02,R.drawable.iamge03,R.drawable.image05,R.drawable.image06,R.drawable.iamge04};

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = (GridView)findViewById(R.id.icon);
        list=new ArrayList<Map<String, Object>>();
        for (int i=0;i<images.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("image",images[i]);
            list.add(map);
        }
        simpleAdapter = new SimpleAdapter(this,list,R.layout.grid_item,new String[]{"image"},new int[]{R.id.iv_item});
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map=(Map<String, Object>)parent.getItemAtPosition(position);
                int choice = (int) map.get("image");
                Intent intent = new Intent();
                intent.putExtra("image",choice);
                System.out.println("ShowIcon："+choice);
                setResult(21,intent);
                finish();

            }
        });
    }
}
