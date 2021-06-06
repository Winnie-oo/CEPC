package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class UpdateUserActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String USER_URL = "http://"+IP+":8021/users";
    private EditText up_tel,up_addr,up_name,up_gender;
    private Button up_apply;
    private Integer up_id;
    private Boolean flag;
    private String up_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        up_tel = findViewById(R.id.update_tel);
        up_addr = findViewById(R.id.update_addr);
        up_name = findViewById(R.id.update_name);
        up_gender = findViewById(R.id.update_gender);
        up_apply = findViewById(R.id.update_apply);

        String name = UpdateUserActivity.this.getIntent().getExtras().getString("username");
        setData(name);


        up_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = up_tel.getText().toString();
                String addr = up_addr.getText().toString();
                String gender = up_gender.getText().toString();
                String name = up_name.getText().toString();
                if (tel.equals("") || addr.equals("")|| gender.equals("")|| name.equals(""))
                    Toast.makeText(UpdateUserActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str ="id="+ URLEncoder.encode(""+up_id) +
                                    "&name="+URLEncoder.encode(name)+
                                    "&password="+URLEncoder.encode(up_pw)+
                                    "&tel="+URLEncoder.encode(tel)+
                                    "&gender="+URLEncoder.encode(gender)+
                                    "&address="+URLEncoder.encode(addr);

                            System.out.println("oooooooooo"+str);
                            String result = PgSqlUtil.postJsonContent(USER_URL+"/upDateUser",str);
                            System.out.println("update中------------"+result);
                            if(result.equals("success")) {
                                flag = true;
                            }else
                                flag = false;
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(flag)
                        Toast.makeText(UpdateUserActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    else if(!flag)
                        Toast.makeText(UpdateUserActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(USER_URL+"/findByName/" + name);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    up_id = jsonObject.getInt("id");
                    up_pw = jsonObject.getString("password");
                    up_tel.setText(jsonObject.getString("tel"));
                    up_addr.setText(jsonObject.getString("address"));
                    up_gender.setText(jsonObject.getString("gender"));
                    up_name.setText(jsonObject.getString("name"));
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
    }

}