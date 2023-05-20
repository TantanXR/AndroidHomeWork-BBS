//package com.example.bbs.ui.login;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class UpdatePassword extends AppCompatActivity {
//    private Button SureUpdatePassword,cancel;
//    private String UserName;
//    private TextView password,password1,password2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.update_layout);
//        init();
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        SureUpdatePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updatePassword();
//            }
//        });
//    }
//
//    void init(){
//        cancel=findViewById(R.id.cancel);
//        SureUpdatePassword=findViewById(R.id.UpdatePassword);
//        password=findViewById(R.id.password);
//        password1=findViewById(R.id.editTextTextPassword);
//        password2=findViewById(R.id.SurePassword);
//        Intent intent = getIntent();
//        UserName = intent.getStringExtra("userName");
//    }
//
//    public void updatePassword(){
//        String Password = password.getText().toString();
//        System.out.println("Password："+Password);
//        String Password1 = password1.getText().toString();
//        System.out.println("Password1："+Password1);
//        String Password2 = password2.getText().toString();
//        System.out.println("Password2："+Password2);
//        if (Password.equals("")){
//            Toast.makeText(UpdatePassword.this,"原密码不能为空",Toast.LENGTH_SHORT).show();
//        }else if (Password1.equals("")){
//            Toast.makeText(UpdatePassword.this,"密码不能为空",Toast.LENGTH_SHORT).show();
//        }else if (Password2.equals("")){
//            Toast.makeText(UpdatePassword.this,"二次密码不能为空",Toast.LENGTH_SHORT).show();
//        }else{
//            SQLiteOpenHelper helper = com.example.android.MySqliteOpenHelper.getMInstance(UpdatePassword.this);
//            SQLiteDatabase db = helper.getReadableDatabase();
//            Cursor cursor = db.rawQuery("select * from users",null);
//            boolean flag = false;
//            while (cursor.moveToNext()){
//                String _user = cursor.getString(cursor.getColumnIndex("_account"));
//                String _password = cursor.getString(cursor.getColumnIndex("_password"));
//                if (UserName.equals(_user) && Password.equals(_password)) {
//                    if (Password.equals(Password1)){
//                        Toast.makeText(UpdatePassword.this,"密码与原密码相同",Toast.LENGTH_SHORT).show();
//                        password1.setText("");
//                        password2.setText("");
//                    }
//                    else if (Password1.equals(Password2)){
//                        String sql = ("update users set _password=? where _account = ?");
//                        db.execSQL(sql,new Object[]{Password1,UserName});
//                        flag = true;
//                        Toast.makeText(UpdatePassword.this,"密码修改成功",Toast.LENGTH_SHORT).show();
//                        Intent ma2 = new Intent(UpdatePassword.this,MainActivity.class);
//                        startActivity(ma2);
//                    }else{
//                        Toast.makeText(UpdatePassword.this,"二次密码输入错误",Toast.LENGTH_SHORT).show();
//                        password1.setText("");
//                        password2.setText("");
//                    }
//                }
//            }
//            if (flag==false){
//                Toast.makeText(UpdatePassword.this,"原密码输入错误",Toast.LENGTH_SHORT).show();
//                password.setText("");
//            }
//        }
//    }
//}
