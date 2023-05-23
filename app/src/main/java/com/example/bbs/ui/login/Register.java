package com.example.bbs.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.start;

import java.util.Objects;

public class Register extends AppCompatActivity {
    private Button button,choose,cancel;
    private ImageView icon;
    Integer image=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, ShowIcon.class);
                startActivityForResult(intent,12);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, start.class);
                startActivity(intent);
            }
        });

    }
    void init(){
        button = findViewById(R.id.SureRegister);
        choose = findViewById(R.id.btn_choose);
        icon = findViewById(R.id.imageView2);
        cancel = findViewById(R.id.cancel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12||requestCode==21){
            image = data.getIntExtra("image",image);
            icon.setImageResource(data.getIntExtra("image",R.drawable.login));
        }
    }

    public void register(View view){
        EditText user = findViewById(R.id.editTextTextPersonName);
        EditText content = findViewById(R.id.editTextTextName);
        EditText password = findViewById(R.id.editTextTextPassword);
        String got_user = user.getText().toString();
        String got_content = content.getText().toString();
        String got_password = password.getText().toString();
        SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        if (database.isOpen()){
            boolean flag = false;
            Cursor cursor = database.rawQuery("select * from users",null);
            while (cursor.moveToNext()){
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                if (Objects.equals(_user,got_user)) {
                    flag = true;
                    Toast.makeText(this,"用户名已注册"+got_user,Toast.LENGTH_SHORT).show();
                }
            }
            if (got_password=="" || got_user==""||got_content==""||image==-1){
                if (image==-1){
                    Toast.makeText(this,"请选择照片"+got_user,Toast.LENGTH_SHORT).show();
                }else if (got_password==""){
                    Toast.makeText(this,"请输入密码"+got_user,Toast.LENGTH_SHORT).show();
                }else if (got_content==""){
                    Toast.makeText(this,"请输入用户简介"+got_user,Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"请输入用户名"+got_user,Toast.LENGTH_SHORT).show();
            }
            else if (flag==false){
                String sql = "insert into users(_account,_password,_content,_image) values(?,?,?,?)";
                User user1 = new User(got_user,got_password,got_content,image);
                database.execSQL(sql,new Object[]{got_user,got_password,got_content,image});
                Toast.makeText(this,"register success"+got_user,Toast.LENGTH_SHORT).show();
                Log.e("register success",got_user);
                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this,start.class);
                intent.putExtra("User",user1);
                startActivity(intent);
            }

        }
        database.close();
    }

}
