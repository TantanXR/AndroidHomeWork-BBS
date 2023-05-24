package com.example.bbs.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.example.bbs.ui.login.ShowIcon;
import com.example.bbs.ui.login.User;


public class UpdatePersonPassword extends AppCompatActivity {
    private Button UpdatePassword,cancel;
    private TextView password,password1,password2;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);
        init();
        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void init(){
        cancel=findViewById(R.id.cancel);
        UpdatePassword=findViewById(R.id.UpdatePassword);
        password = findViewById(R.id.update_user_password);
        password1 = findViewById(R.id.update_user_password1);
        password2 = findViewById(R.id.update_user_password2);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }


    public void updatePassword(){
        String Password = password.getText().toString();
        String Password1= password1.getText().toString();
        String Password2= password2.getText().toString();
        if (Password.equals("")){
            Toast.makeText(UpdatePersonPassword.this,"原密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (Password1.equals("")){
            Toast.makeText(UpdatePersonPassword.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (Password2.equals("")){
            Toast.makeText(UpdatePersonPassword.this,"二次密码不能为空",Toast.LENGTH_SHORT).show();
        }else{
            SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(UpdatePersonPassword.this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users",null);
            boolean flag = false;
            while (cursor.moveToNext()){
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                String _password = cursor.getString(cursor.getColumnIndex("_password"));
                if (user.getUserName().equals(_user) && Password.equals(_password)) {
                    if (Password.equals(Password1)){
                        Toast.makeText(UpdatePersonPassword.this,"密码与原密码相同",Toast.LENGTH_SHORT).show();
                        password1.setText("");
                        password2.setText("");
                        flag = true;
                    }
                    else if (Password1.equals(Password2)){
                        String sql = ("update users set _password=? where _account = ?");
                        db.execSQL(sql,new Object[]{Password1,user.getUserName()});
                        flag = true;
                        Toast.makeText(UpdatePersonPassword.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(UpdatePersonPassword.this,"二次密码输入错误",Toast.LENGTH_SHORT).show();
                        password1.setText("");
                        password2.setText("");
                        flag = true;
                    }
                }
            }
            if (flag==false){
                Toast.makeText(UpdatePersonPassword.this,"原密码输入错误",Toast.LENGTH_SHORT).show();
                password.setText("");
            }
        }
    }
}
