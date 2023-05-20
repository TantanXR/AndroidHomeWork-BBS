package com.example.bbs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bbs.databinding.ActivityMainBinding;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView user_icon;
    private TextView user_content;
    private TextView user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        init();

        binding.appBarMain.addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }
    void init(){
        user_content=binding.navView.findViewById(R.id.user_content);
        user_icon=binding.navView.findViewById(R.id.user_icon);
        user_name=binding.navView.findViewById(R.id.user_name);
        Intent intent = getIntent();
        user_name.setText(intent.getStringExtra("userName"));
        SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("select * from users", null);
            while (cursor.moveToNext()) {
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                String _content = cursor.getString(cursor.getColumnIndex("_content"));
                Integer _image = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_image")));
                if (Objects.equals(intent.getStringExtra("userName"),_user)) {
                    user_icon.setImageResource(_image);
                    user_content.setText(_content);
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}