package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cepc.R;
import com.example.cepc.RegisterActivity;
import com.example.cepc.db.PgSqlUtil;

import java.net.URLEncoder;

public class ChangePwActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String USER_URL = "http://"+IP+":8021/users";
    private EditText ch_pw,ch_re_pw;
    private Button ch_apply;
    private String ch_name;
    private boolean pw_currect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        ch_pw = findViewById(R.id.change_pw);
        ch_re_pw = findViewById(R.id.change_re_pw);
        ch_apply = findViewById(R.id.change_apply);
        ch_name = ChangePwActivity.this.getIntent().getExtras().getString("username");

        ch_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = ch_pw .getText().toString();
                String re_pw = ch_re_pw.getText().toString();
                if (pw.equals("") || re_pw.equals(""))
                    Toast.makeText(ChangePwActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    if(pw.equals(re_pw)){
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String str ="name="+ URLEncoder.encode(ch_name) +
                                        "&password="+URLEncoder.encode(pw);

                                String result = PgSqlUtil.postJsonContent(USER_URL+"/upDatePw",str);
                                System.out.println("注册中------------"+result);
                                if(result.equals("success")) {
                                    pw_currect = true;
                                }else
                                    pw_currect = false;
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(pw_currect)
                            Toast.makeText(ChangePwActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                        else if(!pw_currect)
                            Toast.makeText(ChangePwActivity.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePwActivity.this, "密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}