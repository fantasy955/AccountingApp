package com.experiment.lenovo.accountingsoftware.LoginPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;

import com.experiment.lenovo.accountingsoftware.MainActivity;
import com.experiment.lenovo.accountingsoftware.R;
import com.experiment.lenovo.accountingsoftware.common.User;
import com.experiment.lenovo.accountingsoftware.database.DBManager;
import com.experiment.lenovo.accountingsoftware.tool.MyDate;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {
    List<User> userList;
    private static int ssss;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        ssss = 0;
        MyDate myDate = new MyDate();
        Log.d("LoginActivity",myDate.toString());

        userList = new ArrayList<User>();
//        登陆模块
        setContentView(R.layout.activity_login);
        Button button_login  = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean exist  =false;
                User user = getUserFromInput();
                for(User u : userList){
                    if(u.getUserName().equals(user.getUserName())){
                        if(u.getPassword().equals(user.getPassword())){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user",u);
                            startActivity(intent);
                            exist = true;
                            break;
                        }
                    }
                }
                if(!exist){
                    ssss++;
                    new AlertDialog.Builder(LoginActivity.this).setTitle("警告!").setMessage("用户不存在或密码错误").show();
                    if(ssss == 10 && user.getUserName().equals("add")){
                        Button button_register = (Button) findViewById(R.id.button_register);
                        button_register.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
        //注册模块
        Button button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                User user = getUserFromInput();
                if(!checkInputUserInfo(user)){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("警告!").setMessage("请输入正确的信息").show();
                }else {
                    boolean flag = true;
                    for(User u : userList){
                        if(u.getUserName().equals(user.getUserName())){
                            new AlertDialog.Builder(LoginActivity.this).setTitle("警告!").setMessage("用户名已经存在").show();
                            flag = false;
                            break;
                        }
                    }
                    if(flag == true){
                        user = DBManager.getInstance(LoginActivity.this).getUserDataManager().addUser(user);
                        userList.add(user);
                        new AlertDialog.Builder(LoginActivity.this).setTitle("Ok!").setMessage("注册成功").show();
                        Button button_register = (Button) findViewById(R.id.button_register);
                        button_register.setVisibility(View.GONE);
                    }
                }
            }
        });
        Button button_exit = (Button) findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userList = DBManager.getInstance(this).getUserDataManager().getAllUser();
    }

    private User getUserFromInput(){
        EditText editText_userName = (EditText) findViewById(R.id.editText_username);
        String userName = String.valueOf(editText_userName.getText());
        EditText editText_password  = (EditText) findViewById(R.id.editText_password);
        String password = String.valueOf(editText_password.getText());
        User user = new User(userName,password);
        return user;
    }
    private boolean checkInputUserInfo(User user){
        if(user.getUserName() == null || user.getUserName().equals("") || user.getUserName().length() >20){
            return false;
        }
        if(user.getPassword()==null || user.getPassword().equals("") || user.getPassword().length()<3 ||user.getPassword().length() >20){
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
