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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String URL = "http://"+IP+":8021/users";
    private EditText regName,regPw,regAddr;
    private Button regLogin,regApply;

    private boolean password_currect = false;
    private boolean user_currect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.reg_name);
        regPw = findViewById(R.id.reg_pw);
        regAddr = findViewById(R.id.reg_addr);
        regLogin = findViewById(R.id.reg_login);
        regApply = findViewById(R.id.reg_apply);

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
                if (mUserName.equals("") || mPassword.equals(""))
                    Toast.makeText(RegisterActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = PgSqlUtil.getJsonContent(URL+"/findByName/"+mUserName);
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getString("name") .isEmpty()) {
                                    user_currect = true;

                                    PgSqlUtil.postJsonContent(URL+"/save","{name:"+mUserName+",password:"+mPassword+",address:"+mAddress+"}");
                                }else user_currect = false;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    if(user_currect) {
                        Toast.makeText(RegisterActivity.this, "申请用户成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}