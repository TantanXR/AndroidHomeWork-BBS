package com.example.bbs.ui.personal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bbs.ui.login.MySqliteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;


public class UpdatePersonDetail extends AppCompatActivity {
    private Button SureUpdatePassword,choose,cancel;
    private String UserName;
    private TextView username,content,password,password1,password2;
    private Integer image=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_personal);
        init();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SureUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updatePassword();
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
        choose=findViewById(R.id.btn_choose);
        SureUpdatePassword=findViewById(R.id.SureUpdate);
        username=findViewById(R.id.user_name_Update);
        content=findViewById(R.id.user_content_Update);
        password=findViewById(R.id.user_password_Update);
        password1=findViewById(R.id.user_password1_Update);
        password2=findViewById(R.id.user_password2_Update);
//        Intent intent = getIntent();
//        UserName = intent.getStringExtra("userName");
    }

    public void updatePassword(){
        String content = this.content.getText().toString();
        String Password = password.getText().toString();
        String Password1 = password1.getText().toString();
        String Password2 = password2.getText().toString();
        if (username.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if (content.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"用户简介不能为空",Toast.LENGTH_SHORT).show();
        }else if (Password.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"原密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (Password1.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (Password2.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"二次密码不能为空",Toast.LENGTH_SHORT).show();
        }else{
            SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(UpdatePersonDetail.this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users",null);
            boolean flag = false;
            while (cursor.moveToNext()){
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                String _password = cursor.getString(cursor.getColumnIndex("_password"));
                if (UserName.equals(_user) && Password.equals(_password)) {
                    if (Password.equals(Password1)){
                        Toast.makeText(UpdatePersonDetail.this,"密码与原密码相同",Toast.LENGTH_SHORT).show();
                        password1.setText("");
                        password2.setText("");
                    }
                    else if (Password1.equals(Password2)){
                        String sql = ("update users set _password=? where _account = ?");
                        String sql1 = ("update users set _image=? where _account = ?");
                        String sql2 = ("update users set _content=? where _account = ?");
                        db.execSQL(sql,new Object[]{Password1,UserName});
                        db.execSQL(sql1,new Object[]{content,UserName});
                        db.execSQL(sql2,new Object[]{image,UserName});
                        flag = true;
                        Toast.makeText(UpdatePersonDetail.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                        Intent ma2 = new Intent(UpdatePersonDetail.this, PersonalFragment.class);
                        startActivity(ma2);
                    }else{
                        Toast.makeText(UpdatePersonDetail.this,"二次密码输入错误",Toast.LENGTH_SHORT).show();
                        password1.setText("");
                        password2.setText("");
                    }
                }
            }
            if (flag==false){
                Toast.makeText(UpdatePersonDetail.this,"原密码输入错误",Toast.LENGTH_SHORT).show();
                password.setText("");
            }
        }
    }
}
