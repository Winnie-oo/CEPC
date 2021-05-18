package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VaccinesActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records";
    private final static String VACCINE_URI = "http://"+IP+":8021/vaccines";
    private final static String COMMUNITY_URI = "http://"+IP+":8021/community";
    private final static String APPOINTRECORD_URI = "http://"+IP+":8021/appointRecord";

    private String VACCINE_ACTION = "appoint_cancle";

    private String[] myDate = null;
    private String str_id, str_date, str_name,str1;
    private EditText et_5;
    private Spinner mySpinner_5;
    private Button btSubmit_5;
    private JSONArray jsonArray=null;
    private Boolean ifAppoint = false;
    private TextView tv_cancle,tv_date_5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines);
        initView();
        someListener();
//        mySpinner_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                str_date = parent.getItemAtPosition(position).toString();
//                Toast.makeText(VaccinesActivity.this, str_id,Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void initView() {
        str_name = VaccinesActivity.this.getIntent().getExtras().getString("username");
        et_5 = findViewById(R.id.et1_id_card);
        tv_cancle = findViewById(R.id.appoint_cancle);
        mySpinner_5 = findViewById(R.id.spinner_5);
        btSubmit_5 = findViewById(R.id.submit_5);
        tv_date_5 = findViewById(R.id.date_5);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        tv_date_5.setText(simpleDateFormat.format(date));
       // initBroadcastReceiver ();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result1 = PgSqlUtil.getJsonContent(APPOINTRECORD_URI+"/findByName/"+URLEncoder.encode(str_name));

                if(result1.isEmpty()){
                    //没有预约过
                    tv_cancle.setVisibility(View.GONE);
                    ifAppoint =false;
                    String str_temp = PgSqlUtil.getJsonContent(VACCINE_URI+"/findAll");
                    System.out.println("获取疫苗数量-------"+str_temp);
                    //String str_temp = VaccinesActivity.this.getIntent().getExtras().getString("ListDate");
                    try {
                        jsonArray = new JSONArray(str_temp);
                        String[] temp = new String[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            temp[i]=jsonObject.getString("date")+"剩余"+jsonObject.getString("number")+"名额";
                        }
                        myDate = temp;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    //预约过
                    ifAppoint = true;
                    try {
                        JSONObject jsonObject = new JSONObject(result1);
                        tv_cancle.setVisibility(View.VISIBLE);
                        tv_cancle.setText("已预约"+jsonObject.getString("date")+"疫苗接种！");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(ifAppoint) {
            //设置文本显示预约信息
            btSubmit_5.setText("已预约");
            btSubmit_5.setEnabled(false);
            et_5.setEnabled(false);
            mySpinner_5.setEnabled(false);
        }else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,myDate);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner_5.setAdapter(adapter);
        }
    }


    private void someListener() {
        btSubmit_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = et_5.getText().toString();
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(mySpinner_5.getSelectedItemPosition());
                    str_date =jsonObject.getString("date");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(str_id.equals("")|| str_date.equals("")){
                    Toast.makeText(VaccinesActivity.this,"信息需填写完整！",Toast.LENGTH_LONG).show();
                }else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str ="name="+ URLEncoder.encode(str_name)+
                                    "&date="+str_date+
                                    "&id_card="+ str_id;
                            System.out.println(str);
                            PgSqlUtil.postJsonContent(APPOINTRECORD_URI+"/save",str);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    btSubmit_5.setText("已预约");
                    btSubmit_5.setBackgroundColor(Color.parseColor("#777777"));
                    btSubmit_5.setTextColor(Color.parseColor("#F7F7F7"));
                    btSubmit_5.setEnabled(false);
                    et_5.setEnabled(false);
                    mySpinner_5.setEnabled(false);
                    tv_cancle.setVisibility(View.VISIBLE);
                    tv_cancle.setText("已预约"+str_date+"疫苗接种！");

//                    Intent intent = new Intent();
//                    intent.setAction(VACCINE_ACTION);
//                    VaccinesActivity.this.sendBroadcast(intent);
                }
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PgSqlUtil.deleteJsonContent(APPOINTRECORD_URI+"/deleteByName/"+URLEncoder.encode(str_name));
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv_cancle.setVisibility(View.GONE);
                btSubmit_5.setText("预约");
                btSubmit_5.setBackgroundColor(Color.parseColor("#ffcc99"));
                btSubmit_5.setEnabled(true);
                et_5.setEnabled(true);
                mySpinner_5.setEnabled(true);
//                Intent intent = new Intent();
//                intent.setAction(VACCINE_ACTION);
//                VaccinesActivity.this.sendBroadcast(intent);
            }
        });
    }

    //获取广播数据
    public void initBroadcastReceiver (){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (intent.getAction().equals(VACCINE_ACTION)) {
                    Toast.makeText(VaccinesActivity.this, "传进来了", Toast.LENGTH_SHORT).show();
                    //tv_cancle.notify();
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        filter.addAction(VACCINE_ACTION);
        this.registerReceiver(broadcastReceiver,filter);
    }
}