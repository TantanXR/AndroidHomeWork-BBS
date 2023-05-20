package com.example.bbs.ui.personal;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bbs.R;

public class PersonalFragment extends Fragment {

    private View root;
    private Button update;


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
    }
}