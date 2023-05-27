package com.example.bbs.ui.personal;

import android.app.Person;
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

import com.example.bbs.Main2Activity;
import com.example.bbs.ui.login.MySqliteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bbs.R;
import com.example.bbs.ui.login.Register;
import com.example.bbs.ui.login.ShowIcon;
import com.example.bbs.ui.login.User;


public class UpdatePersonDetail extends AppCompatActivity {
    private Button SureUpdate,choose,cancel;
    private ImageView update_user_icon;
    private User user;
    private TextView username,content;
    private Integer image;

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
                updateUserBaseMessage();
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
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        image = user.getImage();
        update_user_icon.setImageResource(user.getImage());
        username.setText(user.getUserName());
        content.setText(user.getContent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12||requestCode==21){
            image = data.getIntExtra("image",image);
            update_user_icon.setImageResource(data.getIntExtra("image",R.drawable.login));
        }
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
                if (_user.equals(user.getUserName())){
                    String sql = ("update users set _account=?,_image=?,_content=? where _account = ?");
                    db.execSQL(sql,new Object[]{username,image,content,user.getUserName()});
                    Toast.makeText(UpdatePersonDetail.this,"用户信息修改成功",Toast.LENGTH_SHORT).show();
                    User user = new User(username,this.user.getPassword(),content,image);
                    //main2Activity.setUser(user);
                    Main2Activity.instance.setUser(user);
                    Intent intent = new Intent(UpdatePersonDetail.this,Main2Activity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
//                    finish();
                }
            }
        }
    }
}
