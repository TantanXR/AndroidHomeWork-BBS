package com.example.bbs.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bbs.Main2Activity;
import com.example.bbs.R;
import com.example.bbs.start;
import com.example.bbs.ui.login.User;


public class SettingFragment extends Fragment {

    private View root;
    private Button set_music,out_login;
    private TextView user_name;
    private User user;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = ((Main2Activity)getActivity()).getUser();
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_setting, null);
        }
        init();
        out_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), start.class);
                startActivity(intent);
            }
        });
        set_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MusicShow.class);
                startActivity(intent);
            }
        });
        return root;
    }

    void init(){
        user_name = root.findViewById(R.id.user_name);
        set_music = root.findViewById(R.id.set_music);
        out_login = root.findViewById(R.id.out_login);
        user_name.setText("你好，"+user.getUserName()+"!");
    }


}