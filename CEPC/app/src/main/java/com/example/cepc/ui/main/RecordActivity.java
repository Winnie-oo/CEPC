package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cepc.MyService;
import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecordActivity extends AppCompatActivity {

    private static final String IP="192.168.43.74";
    private final static String RECORD_URI = "http://"+IP+":8021/records";


    private Button btAddr ;
    private EditText etTemperature;
    private RadioGroup mRadio1,mRadio2;
    private Button btSubmit;
    private TextView tvDate;
    private String name_1;
    private double temperature_1;
    private String patient_1;
    private String date_1;
    private String address_1 ;
    private String hadVac_1 ;

    private boolean record_currect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        btAddr = findViewById(R.id.bt1_address);
        etTemperature = findViewById(R.id.et1_temperature);
        btSubmit = findViewById(R.id.submit1);
        tvDate = findViewById(R.id.date);
        mRadio1 = findViewById(R.id.radio_group1);
//        mRadio2 = findViewById(R.id.radio_group2);
        name_1 = this.getIntent().getExtras().getString("username");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        tvDate.setText(simpleDateFormat.format(date));
        date_1 = simpleDateFormat.format(date);


        //查看今天是否填报
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(RECORD_URI+"/findByNameAndDate/"+name_1+"/"+date_1);
                if(result.isEmpty())
                    record_currect=false;
                else
                    record_currect=true;
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if( record_currect) {
            btSubmit.setText("今日已填报");
            btSubmit.setBackgroundColor(Color.parseColor("#777777"));
            btSubmit.setTextColor(Color.parseColor("#F7F7F7"));
            btSubmit.setEnabled(false);
        }

        mRadio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId!=0){
                    RadioButton rb = findViewById(checkedId);
                    patient_1 = rb.getText().toString();
                }
            }
        });

//        mRadio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId!=0){
//                    RadioButton rb = findViewById(checkedId);
//                    hadVac_1 = rb.getText().toString();
//                }
//            }
//        });

        btAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取地理位置
                Intent intent= new Intent(RecordActivity.this, MyService.class);
                //getActivity().startService(intent); // 开始启动服务
                //注册广播接收器
                //initBroadcastReceiver();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNotDouble(etTemperature.getText().toString())){
                    Toast.makeText(RecordActivity.this, "体温输入不规范！", Toast.LENGTH_SHORT).show();
                }
                else {
                    address_1 = "海虹";
                    String tem_x = etTemperature.getText().toString();
                    if(patient_1==null || address_1==null || tem_x==null){//  || hadVac_1==null
                        Toast.makeText(RecordActivity.this, "数据信息不得为空！", Toast.LENGTH_SHORT).show();
                    }else {
                        temperature_1 = Double.parseDouble(tem_x);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String str ="name="+ URLEncoder.encode(name_1) +
                                        "&temperature="+URLEncoder.encode(""+temperature_1)+
                                        "&patient="+URLEncoder.encode(patient_1)+
                                        "&date="+URLEncoder.encode(date_1)+
//                                        "&hadvac="+URLEncoder.encode(hadVac_1)+
                                        "&address="+URLEncoder.encode(address_1);
                                PgSqlUtil.postJsonContent(RECORD_URI+"/save",str);
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                            Toast.makeText(RecordActivity.this, "填报成功！", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        btSubmit.setText("今日已填报");
                        btSubmit.setBackgroundColor(Color.parseColor("#777777"));
                        btSubmit.setTextColor(Color.parseColor("#F7F7F7"));
                        btSubmit.setEnabled(false);
                    }
                }
            }
        });
    }
    //判断输入是否为double,不是则返回true
    public static Boolean isNotDouble(String str)
    {
        Boolean flag = false;
        if (str.startsWith(".") || str.endsWith(".")) {
            flag = true;
        } else {
            for (int i = 0; i < str.length(); i++)
            {
                if (!(Character.isDigit(str.charAt(i)) || str.charAt(i)=='.'))
                {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

//
//    //获取广播数据
//    public void initBroadcastReceiver (){
//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getExtras();
//                if (intent.getAction() == "com.example.cepc.myservice") {
//                    Toast.makeText(RecordActivity.this, "传进来了", Toast.LENGTH_SHORT).show();
//                    Double latitude=bundle.getDouble("latitude");
//                    Double longitude=bundle.getDouble("longitude");
//                    btAddr.setText(latitude + ","+longitude);
//                    address_1 = btAddr.getText().toString();
//                }
//            }
//        };
//        IntentFilter filter=new IntentFilter();
//        filter.addAction("com.example.cepc.myservice");
//        this.registerReceiver(broadcastReceiver,filter);
//    }

}