package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cepc.HomeActivity;
import com.example.cepc.R;


public class MineActivity extends AppCompatActivity {
    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records/";
    private final static String VACCINE_URI = "http://"+IP+":8021/vaccines";
    private final static String COMMUNITY_URI = "http://"+IP+":8021/community";
    private final static String APPOINTRECORD_URI = "http://"+IP+":8021/appointRecord";

    static final String Mine_action = "exit";

    private String name_3;
    private int daymark_3;
    private TextView tv3_name, tv3_chPw, tv3_upUser;
    private ListView listView;
    private ListViewAdapter adapter;
    private Button mbt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        tv3_name = findViewById(R.id.person_name);
        tv3_chPw = findViewById(R.id.change_password);
        tv3_upUser = findViewById(R.id.update_data);
        mbt_exit = findViewById(R.id.exit_3);

        name_3 = MineActivity.this.getIntent().getExtras().getString("username");
        tv3_name.setText(name_3);


        mbt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent stopIntent = new Intent(MineActivity.this, MyService.class);
//                MineActivity.this.stopService(stopIntent);//退出账户的同时，关闭服务
                Intent intent = new Intent();
                intent.setAction(Mine_action);
                MineActivity.this.sendBroadcast(intent);
                MineActivity.this.finish();
            }
        });

        tv3_chPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", name_3);
                //跳转RecordActivity
                Intent intent = new Intent(MineActivity.this, ChangePwActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tv3_upUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", name_3);
                //跳转RecordActivity
                Intent intent = new Intent(MineActivity.this, UpdateUserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}