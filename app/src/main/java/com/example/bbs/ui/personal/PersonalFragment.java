package com.example.bbs.ui.personal;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bbs.R;

public class PersonalFragment extends Fragment {

    private View root;
    private Button update;
    private TextView user_name_show;
    private TextView user_password_show;
    private TextView user_content_show;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(root == null) {
            root = inflater.inflate(R.layout.fragment_personal, null);
        }
        init();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePassword.class);
                startActivity(intent);
            }
        });
        return root;
    }

    void init(){
        update = root.findViewById(R.id.Update);
        user_name_show = root.findViewById(R.id.user_name_show);
        user_password_show = root.findViewById(R.id.user_password_show);
        user_content_show = root.findViewById(R.id.user_content_show);

    }
}