package com.zly.collectapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;
import com.zly.collectapp.entity.User;
import com.zly.collectapp.test.TestActivity;
import com.zly.collectapp.utils.UserDatabase;
import com.zly.collectapp.utils.UserUtils;
import com.zly.collectapp.view.InputView;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGIN_TAG="LoginActivity";

    private InputView etName,etPwd;
    private Button loginBtn,bindServiceBtn;
    private CheckBox rememberMe;
    private TextView toRegister;
//    private TestService.MyBinder binder;
//    private ServiceConnection serviceConnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//                binder= (TestService.MyBinder) service;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etName=findViewById(R.id.et_name);
        etPwd=findViewById(R.id.et_password);
        loginBtn=findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        rememberMe = findViewById(R.id.remember_me);
        toRegister=findViewById(R.id.to_register);
        toRegister.setOnClickListener(this);
        //进行数据的初始化展示
        setLoginInfo();
        //RoomExplorer.show(this, UserDatabase.class, "BeautyCollect");

//        bindServiceBtn=findViewById(R.id.bind_service);
//        bindServiceBtn.setOnClickListener(this);
    }

    public void jump(@Nullable View view){
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.login_btn:
                String name=etName.getText().toString().trim();
                String password=etPwd.getText().toString().trim();
                //Log.e("login",name+"-"+password);
                if (!password.equals("") && !name.equals("")) {
                    //开始登陆验证
                    User user = UserDatabase.getInstance(this).getUserDao().findOneByNameAndPwd(name, password);
                    if (user!=null){
                        SharedPreferences.Editor editor=getSharedPreferences("account",MODE_PRIVATE).edit();
                        if(rememberMe.isChecked()){
                            editor.putBoolean("isRemember",true);
                            editor.putInt("id",user.getId());
                            editor.putString("name",name);
                            editor.putString("password",password);
                        }else {
                            editor.clear();
                        }
                        editor.apply();
                        UserUtils.setUserId(user.getId());
                        UserUtils.setUserName(user.getName());
                        intent=new Intent(this,MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.to_register:
                intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
//            case R.id.bind_service:
//                Intent in=new Intent(this,TestService.class);
//                bindService(in,serviceConnection,BIND_AUTO_CREATE);
//                break;
        }
    }

    /**
     * 判断是否有缓存，有缓存，将账号密码进行显示，
     */
    public void setLoginInfo(){
        SharedPreferences spf=getSharedPreferences("account", Activity.MODE_PRIVATE);
        boolean remember = spf.getBoolean("isRemember", false);
        String name = spf.getString("name", "");
        String password = spf.getString("password", "");
        if (remember) {
            etName.setText(name);
            etPwd.setText(password);
            rememberMe.setChecked(true);
        }
    }


}
