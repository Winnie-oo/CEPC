package com.example.cepc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cepc.db.PgSqlUtil;
import com.example.cepc.ui.main.RecordActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String USER_URL = "http://"+IP+":8021/users";
    private EditText regName,regPw,regAddr,regTel,regGender,regRepw;
    private Button regLogin,regApply;

    private boolean user_currect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.reg_name);
        regPw = findViewById(R.id.reg_pw);
        regAddr = findViewById(R.id.reg_addr);
        regTel = findViewById(R.id.reg_tel);
        regGender = findViewById(R.id.reg_gender);
        regLogin = findViewById(R.id.reg_login);
        regApply = findViewById(R.id.reg_apply);
        regRepw = findViewById(R.id.reg_re_pw);

        regLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                //跳转LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = regName.getText().toString();
                String mPassword = regPw.getText().toString();
                String mAddress = regAddr.getText().toString();
                String mTel = regTel.getText().toString();
                String mGender = regGender.getText().toString();
                String mRepw = regRepw.getText().toString();

                if (mUserName.equals("") || mPassword.equals("") || mAddress.equals("") || mTel.equals("") || mGender.equals("") || mRepw.equals(""))
                    Toast.makeText(RegisterActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                else {
                    if(mPassword.equals(mRepw)){
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String str ="name="+ URLEncoder.encode(mUserName) +
                                        "&password="+URLEncoder.encode(mPassword)+
                                        "&tel="+URLEncoder.encode(mTel)+
                                        "&gender="+URLEncoder.encode(mGender)+
                                        "&address="+URLEncoder.encode(mAddress);

                                String result = PgSqlUtil.postJsonContent(USER_URL+"/save",str);
                                System.out.println("注册中------------"+result);
                                if(result.equals("success")) {
                                    user_currect = true;
                                }else
                                    user_currect = false;
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(user_currect)
                            Toast.makeText(RegisterActivity.this, "用户申请成功！", Toast.LENGTH_SHORT).show();
                        else if(!user_currect)
                            Toast.makeText(RegisterActivity.this, "用户名已存在！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisterActivity.this, "密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}