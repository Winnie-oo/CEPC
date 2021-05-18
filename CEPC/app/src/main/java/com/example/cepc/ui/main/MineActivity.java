package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class MineActivity extends AppCompatActivity {
    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records/";
    private final static String VACCINE_URI = "http://"+IP+":8021/vaccines";
    private final static String COMMUNITY_URI = "http://"+IP+":8021/community";
    private final static String APPOINTRECORD_URI = "http://"+IP+":8021/appointRecord";

    static final String action = "ha";

    private String name_3;
    private int daymark_3;
    private TextView tv3_name, tv3_daymark;
    private ListView listView;
    private ListViewAdapter adapter;
    private Button mbt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        tv3_name = findViewById(R.id.person_name);
        tv3_daymark = findViewById(R.id.person_daymark);
        listView = findViewById(R.id.person_lv);
        mbt_exit = findViewById(R.id.exit_3);
        initBroadcastReceiver ();

        name_3 = MineActivity.this.getIntent().getExtras().getString("username");
        tv3_name.setText(name_3);
        SetDaymark(name_3);

        listView.setAdapter(new ListViewAdapter(MineActivity.this,name_3));

        mbt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(MineActivity.this, MyService.class);
                MineActivity.this.stopService(stopIntent);//退出账户的同时，关闭服务
                Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                startActivity(intent);
                MineActivity.this.finish();
            }
        });
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

    private void initBroadcastReceiver(){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter = (ListViewAdapter) listView.getAdapter();
                if (intent.getAction().equals(action)) {
                    MineActivity.this.adapter.notifyDataSetChanged();
                    SetDaymark(name_3);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        MineActivity.this.registerReceiver(broadcastReceiver,filter);
    }
}