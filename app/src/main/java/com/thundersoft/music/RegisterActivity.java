package com.thundersoft.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        EditText username = findViewById(R.id.reg_username);
        EditText password = findViewById(R.id.reg_password);
        EditText email = findViewById(R.id.reg_email);
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入是否为空
                if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("") || email.getText().toString().trim().equals("")){
                    Toast.makeText(RegisterActivity.this,"用户名或密码或邮箱不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbHelper = new DBHelper(RegisterActivity.this);
                if (dbHelper.isUserExist(username.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.register(username.getText().toString().trim(),password.getText().toString().trim(),email.getText().toString().trim());
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}