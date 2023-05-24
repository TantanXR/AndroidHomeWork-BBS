package com.example.bbs.ui.personal;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bbs.Main2Activity;
import com.example.bbs.R;
import com.example.bbs.ui.login.User;

public class PersonalFragment extends Fragment {

    private View root;
    private Button update,my_comment,my_post,UpdatePassword;
    private TextView user_name_show,user_content_show;
    private User user ;
    private ImageView user_icon;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user = ((Main2Activity)getActivity()).getUser();
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_personal, null);
        }
        init();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePersonDetail.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        my_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCommentDetail.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        my_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPostShow.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePersonPassword.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        return root;
    }

    void init(){
        update = root.findViewById(R.id.Update);
        user_name_show = root.findViewById(R.id.user_name_show);
        user_content_show = root.findViewById(R.id.user_content_show);
        my_comment = root.findViewById(R.id.my_comment);
        my_post = root.findViewById(R.id.my_post);
        UpdatePassword = root.findViewById(R.id.UpdatePassword);
        user_icon = root.findViewById(R.id.user_icon);
        user_name_show.setText(user.getUserName());
        user_content_show.setText(user.getContent());
        user_icon.setImageResource(user.getImage());
    }
}