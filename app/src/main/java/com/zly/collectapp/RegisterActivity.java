package com.zly.collectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zly.collectapp.entity.User;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.view.InputView;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private InputView etName,etPwd,etPwdConfirm;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView(){
        etName=findViewById(R.id.et_register_name);
        etPwd=findViewById(R.id.et_register_pwd);
        etPwdConfirm=findViewById(R.id.et_register_confirm);
        btnRegister=findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_register){
            String name=etName.getText().toString().trim();
            String password=etPwd.getText().toString().trim();
            String pwdConfirm=etPwdConfirm.getText().toString().trim();
            if (name.equals("")||password.equals("")||pwdConfirm.equals("")){
                Toast.makeText(this,"用户名、密码、确认密码都不能为空",Toast.LENGTH_SHORT).show();
            }else {
                if (!password.equals(pwdConfirm)){
                    Toast.makeText(this,"请确认密码",Toast.LENGTH_SHORT).show();
                }else {
                    User user=new User();
                    user.setName(name);
                    user.setPassword(password);
                    UserDatabase.getInstance(this).getUserDao().insert(user);
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
