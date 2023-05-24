package com.example.bbs;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbs.ui.login.MySqliteOpenHelper;
import com.example.bbs.ui.login.Register;
import com.example.bbs.ui.login.User;

import java.util.Objects;

public class start extends AppCompatActivity {
    private TextView user;
    private TextView password;
    private Button Login_button;
    private Button Register_button;
    private CheckBox checkBox,cbSave;

    public static final String PREFERENCE_NAME = "SaveLogin";
    public static int MODE = Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start.this, Register.class);
                startActivity(intent);
            }
        });
        checkBox.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    void init(){
        Login_button = findViewById(R.id.Login);
        Register_button = findViewById(R.id.Register);
        user = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        checkBox = findViewById(R.id.checkBox);
        cbSave = findViewById(R.id.cbSaveLogin);


    }

    private void saveSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME,MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Name",user.getText().toString());
        editor.putString("Password",password.getText().toString());
        if (cbSave.isChecked()){
            editor.putBoolean("SaveFlag",true);
        }
        else{
            editor.putBoolean("SaveFlag",false);
        }
        editor.commit();
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME,MODE);
        String name = sharedPreferences.getString("Name","");
        String password = sharedPreferences.getString("Password","");
        Boolean save = sharedPreferences.getBoolean("SaveFlag",false);
        if (save){
            user.setText(name);
            this.password.setText(password);
            cbSave.setChecked(true);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSharedPreferences();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSharedPreferences();
    }

    public void login(View view){
        EditText user=findViewById(R.id.editTextTextPersonName);
        EditText password=findViewById(R.id.editTextTextPassword);
        String got_user=user.getText().toString();
        String got_password=password.getText().toString();
        SQLiteOpenHelper helper = MySqliteOpenHelper.getMInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from users",null);
            boolean flag = false;
            while (cursor.moveToNext()){
                String _user = cursor.getString(cursor.getColumnIndex("_account"));
                String _password = cursor.getString(cursor.getColumnIndex("_password"));
                String _content = cursor.getString(cursor.getColumnIndex("_content"));
                Integer _image = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_image")));
                if (Objects.equals(_user,got_user) && Objects.equals(_password,got_password)) {
                    Toast.makeText(this,"用户登录成功"+got_user,Toast.LENGTH_SHORT).show();
                    User user1 = new User(_user,_password,_content,_image);
//                    Intent intent = new Intent(start.this,Main2Activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("user", user1);
//                    intent.putExtras(bundle);
//                    setResult(1,intent);
////                    finish();
//                    startActivity(intent);
                    Intent intent = new Intent(start.this,Main2Activity.class);
                    intent.putExtra("user",user1);
                    startActivity(intent);
                    flag =true;
                }
            }
            if (flag==false){
                Toast.makeText(this,"用户名或密码错误"+got_user,Toast.LENGTH_SHORT).show();
                user.setText("");
                password.setText("");
            }
            cursor.close();
            db.close();
        }
    }
}
