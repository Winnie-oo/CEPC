package com.example.cepc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cepc.db.DataBaseHelper;
import com.example.cepc.model.User;

public class LoginActivity extends AppCompatActivity {
    private final static String AUTHORITY = "com.example.cepc.DataContentProvider";
    private final static Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/"+ DataBaseHelper.USERS_TABLE_NAME);
    private Context mContext;
    private EditText etUserName,etPassword;
    private Button btLogin,btApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;

        etUserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
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
                else if (queryPassword(mUserName)== null)
                    Toast.makeText(LoginActivity.this, "用户名错误 or 不存在，请先注册！", Toast.LENGTH_SHORT).show();
                else {
                    String pw = queryPassword(mUserName);
                    if (!pw.equals(mPassword))
                        Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("username", mUserName);
                        //跳转MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = etUserName.getText().toString();
                String mPassword = etPassword.getText().toString();
                if (mUserName.equals("") || mPassword.equals(""))
                    Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    if (queryPassword(mUserName) == null)
                        insertValue(mUserName, mPassword);
                    else Toast.makeText(LoginActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertValue(String name,String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",name);
        contentValues.put("password",password);
        contentValues.put("daymarks",0);
        //通过getContentResolver()得到对象，调用其insert
        mContext.getContentResolver().insert(USER_URI,contentValues);
        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    private String queryPassword(String name) {
        //通过getContentResolver()得到对象，调用其query，得到Cursor
        Cursor cursor = getContentResolver().query(USER_URI, new String[]{"*"},
                "username=?",new String[]{ name },null);
        String password = null;
        if(cursor != null){
            if (cursor.moveToFirst()) {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex("user_id")),
                        cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getInt(cursor.getColumnIndex("daymarks")));
                password = user.getPassword();
            }
        }
        return password;
    }

//    private void deleteValue() {
//        getContentResolver().delete(USER_URI,"name = ?",new String[]{"update"});
//    }

}
