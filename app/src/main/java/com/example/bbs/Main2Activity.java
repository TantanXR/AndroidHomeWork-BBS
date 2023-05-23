package com.example.bbs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bbs.databinding.ActivityMainBinding;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.example.bbs.ui.login.User;
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
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        init();
    }

    void init(){
        user_content = findViewById(R.id.user_content);
        user_icon = findViewById(R.id.user_icon);
        user_name =findViewById(R.id.user_name);
        User user = findUser();
//        user_content.setText(user.getContent());
//        user_icon.setImageResource(user.getImage());
//        user_name.setText(user.getUserName());
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

    public User findUser() {
        Intent intent = getIntent();
        User user = null;
        boolean flag = false;
        String userName = intent.getStringExtra("userName");
        SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()) {
            Cursor cursor = database.rawQuery("select * from users", null);
            while (cursor.moveToNext()) {
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                String _password = cursor.getString(cursor.getColumnIndex("_password"));
                String _content = cursor.getString(cursor.getColumnIndex("_content"));
                Integer _image = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_image")));
                if (Objects.equals(_user, userName)) {
                    user = new User(_user, _password, _content, _image);
                    flag = true;
                }
            }
        }
        if (flag){
            return user;
        }else{
            System.out.println("没有找到相应对象");
            return user;
        }

    }

}