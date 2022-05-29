package com.thundersoft.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences sp = null;
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        CheckBox remember = findViewById(R.id.remember);
        Button login = findViewById(R.id.login);
        Button toregister = findViewById(R.id.toregister);
        sp = getSharedPreferences("UserLoginInfo",MODE_PRIVATE);
        //根据remember判断是否恢复apply之前状态
        //文档：在首选项编辑器中设置一个布尔值，一旦被调用 commit()或 apply() ，将被写回。
        if (sp.getBoolean("remember",false)){
            username.setText(sp.getString("username",""));
            password.setText(sp.getString("password",""));
            remember.setChecked(true);
        }
        SharedPreferences finalSp = sp;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入是否为空
                if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = finalSp.edit();
                if (remember.isChecked()) {
                    editor.putBoolean("remember", true);
                    editor.putString("username", username.getText().toString().trim());
                    editor.putString("password", password.getText().toString().trim());
                }else {
                    editor.clear();
                }
                editor.apply();
                //判断用户名和密码是否存在
                DBHelper dbHelper = new DBHelper(LoginActivity.this);
                if (dbHelper.isUserMatch(username.getText().toString().trim(),password.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}