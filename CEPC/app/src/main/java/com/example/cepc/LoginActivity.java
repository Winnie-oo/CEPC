package com.example.cepc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cepc.db.PgSqlUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String URL = "http://"+IP+":8021/users";
    private EditText etUserName,etPassword,etAddress;
    private Button btLogin,btApply;

    private boolean password_currect = false;
    private boolean user_currect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etAddress = findViewById(R.id.address);
        btLogin = findViewById(R.id.bt3_login);
        btApply = findViewById(R.id.bt3_apply);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                String mUserName = etUserName.getText().toString();
                String mPassword = etPassword.getText().toString();
                if (mUserName.equals("") || mPassword.equals(""))
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = PgSqlUtil.getJsonContent(URL+"/findByName/"+mUserName);
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getString("password").isEmpty())
                                    user_currect=true;
                                if(jsonObject.getString("password").equals(mPassword) ) {
                                    password_currect = true;
                                } else {
                                    password_currect = false;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //登录事件
                    if(user_currect) { Toast.makeText(LoginActivity.this, "用户名错误 or 不存在，请先注册！", Toast.LENGTH_SHORT).show();}
                    if(password_currect) {
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("username", mUserName);
                        //跳转MainActivity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = etUserName.getText().toString();
                String mPassword = etPassword.getText().toString();
                String mAddress = etAddress.getText().toString();
                if (mUserName.equals("") || mPassword.equals(""))
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "申请用户成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
