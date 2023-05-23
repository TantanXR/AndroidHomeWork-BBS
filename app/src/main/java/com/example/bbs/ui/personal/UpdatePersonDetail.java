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
import com.example.bbs.ui.login.MySqliteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.login.Register;
import com.example.bbs.ui.login.ShowIcon;


public class UpdatePersonDetail extends AppCompatActivity {
    private Button SureUpdate,choose,cancel;
    private ImageView update_user_icon;
    private String UserName;
    private TextView username,content;
    private Integer image=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_personal);
        init();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdatePersonDetail.this, ShowIcon.class);
                startActivityForResult(intent,12);
            }
        });

        SureUpdate.setOnClickListener(new View.OnClickListener() {
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
        SureUpdate=findViewById(R.id.SureUpdate);
        username=findViewById(R.id.user_name_Update);
        content=findViewById(R.id.user_content_Update);
        update_user_icon=findViewById(R.id.update_user_icon);
//        Intent intent = getIntent();
//        UserName = intent.getStringExtra("userName");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12||requestCode==21){
            image = data.getIntExtra("image",image);
            update_user_icon.setImageResource(data.getIntExtra("image",R.drawable.login));
        }
    }

    public void updateUserIcon(){
//        if (username.equals("")){
//            Toast.makeText(UpdatePersonDetail.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
//        }else if (content.equals("")){
//            Toast.makeText(UpdatePersonDetail.this,"用户简介不能为空",Toast.LENGTH_SHORT).show();
//        }else if (Password.equals("")){
//            Toast.makeText(UpdatePersonDetail.this,"原密码不能为空",Toast.LENGTH_SHORT).show();
//        }else if (Password1.equals("")){
//            Toast.makeText(UpdatePersonDetail.this,"密码不能为空",Toast.LENGTH_SHORT).show();
//        }else if (Password2.equals("")){
//            Toast.makeText(UpdatePersonDetail.this,"二次密码不能为空",Toast.LENGTH_SHORT).show();
//        }else{
//            SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(UpdatePersonDetail.this);
//            SQLiteDatabase db = helper.getReadableDatabase();
//            Cursor cursor = db.rawQuery("select * from users",null);
//            boolean flag = false;
//            while (cursor.moveToNext()){
//                String _user = cursor.getString(cursor.getColumnIndex("_account"));
//                String _password = cursor.getString(cursor.getColumnIndex("_password"));
//                if (UserName.equals(_user) && Password.equals(_password)) {
//                    if (Password.equals(Password1)){
//                        Toast.makeText(UpdatePersonDetail.this,"密码与原密码相同",Toast.LENGTH_SHORT).show();
//                        password1.setText("");
//                        password2.setText("");
//                    }
//                    else if (Password1.equals(Password2)){
//                        String sql = ("update users set _password=? where _account = ?");
//                        String sql1 = ("update users set _image=? where _account = ?");
//                        String sql2 = ("update users set _content=? where _account = ?");
//                        db.execSQL(sql,new Object[]{Password1,UserName});
//                        db.execSQL(sql1,new Object[]{content,UserName});
//                        db.execSQL(sql2,new Object[]{image,UserName});
//                        flag = true;
//                        Toast.makeText(UpdatePersonDetail.this,"密码修改成功",Toast.LENGTH_SHORT).show();
//                        Intent ma2 = new Intent(UpdatePersonDetail.this, PersonalFragment.class);
//                        startActivity(ma2);
//                    }else{
//                        Toast.makeText(UpdatePersonDetail.this,"二次密码输入错误",Toast.LENGTH_SHORT).show();
//                        password1.setText("");
//                        password2.setText("");
//                    }
//                }
//            }
//            if (flag==false){
//                Toast.makeText(UpdatePersonDetail.this,"原密码输入错误",Toast.LENGTH_SHORT).show();
//                password.setText("");
//            }
//        }
    }

    public void updateUserBaseMessage(){
        String content = this.content.getText().toString();
        String username= this.username.getText().toString();
        if (username.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if (content.equals("")){
            Toast.makeText(UpdatePersonDetail.this,"用户简介不能为空",Toast.LENGTH_SHORT).show();
        }else{
            SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(UpdatePersonDetail.this);
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users",null);
            while (cursor.moveToNext()){
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                if (_user.equals(UserName)){
                    String sql = ("update users set _account=?,_image=?,_content=? where _account = ?");
                    db.execSQL(sql,new Object[]{username,image,content,UserName});
                    Toast.makeText(UpdatePersonDetail.this,"用户信息修改成功",Toast.LENGTH_SHORT).show();
                    Intent ma2 = new Intent(UpdatePersonDetail.this, PersonalFragment.class);
                    startActivity(ma2);
                }
            }
        }
    }
}
