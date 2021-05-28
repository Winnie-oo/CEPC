package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cepc.LoginActivity;
import com.example.cepc.MyService;
import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LookActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private String name_3;
    private int daymark_3;
    private TextView tv3_name, tv3_daymark;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        tv3_name = findViewById(R.id.person_name);
        tv3_daymark = findViewById(R.id.person_daymark);
        listView = findViewById(R.id.person_lv);

        name_3 = LookActivity.this.getIntent().getExtras().getString("username");
        tv3_name.setText(name_3);
        SetDaymark(name_3);
        listView.setAdapter(new ListViewAdapter(LookActivity.this,name_3));

    }


    private void SetDaymark(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(USER_URI+"/findByName/"+name);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject.getInt("day_mark"));
                    daymark_3 = jsonObject.getInt("day_mark");
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
        tv3_daymark.setText("连续打卡" + daymark_3 + "天");
    }

}