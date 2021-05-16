package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cepc.MyService;
import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItmFragment1 extends Fragment {

    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records";
    private Context mContext;

    private ArrayList<String> arrayList = new ArrayList<>();

    public static final String action = "ha";
    private Button btAddr ;
    private EditText etTemperature;
    private RadioGroup mRadio;
    private Button btSubmit;
    private TextView tvDate;
    private String name_1;
    private double temperature_1;
    private String patient_1;
    private String date_1;
    private String address_1 ;

    private boolean record_currect = false;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); mContext=this.getContext();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_1, container, false);
        return root;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btAddr = view.findViewById(R.id.bt1_address);
        etTemperature = view.findViewById(R.id.et1_temperature);
        btSubmit = view.findViewById(R.id.submit1);
        tvDate = view.findViewById(R.id.date);
        mRadio = view.findViewById(R.id.radio_group1);

        name_1 = getActivity().getIntent().getExtras().getString("username");
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

        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId!=0){
                    RadioButton rb = view.findViewById(checkedId);
                    patient_1 = rb.getText().toString();
                }
            }
        });

        btAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取地理位置
                Intent intent= new Intent(mContext, MyService.class);
                //getActivity().startService(intent); // 开始启动服务
                //注册广播接收器
                initBroadcastReceiver();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperature_1 = Double.parseDouble(etTemperature.getText().toString());
                //address_1 = btAddr.getText().toString();
                address_1 = "海虹四栋";
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PgSqlUtil.postJsonContent(RECORD_URI+"/save",
                                "{\"name\":"+name_1+
                                        ",\"temperature\":"+temperature_1+
                                        ",\"patient\":"+patient_1+
                                        ",\"date\":"+date_1+
                                        ",\"address\":"+address_1+"}");
                    }
                });
                thread.start();


                btSubmit.setText("今日已填报");
                btSubmit.setBackgroundColor(Color.parseColor("#777777"));
                btSubmit.setTextColor(Color.parseColor("#F7F7F7"));
                btSubmit.setEnabled(false);

                Intent intent = new Intent();
                intent.setAction("ha");
                getActivity().sendBroadcast(intent);
            }
        });
    }

    //获取广播数据
    public void initBroadcastReceiver (){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (intent.getAction() == "com.example.cepc.myservice") {
                    Toast.makeText(mContext, "传进来了", Toast.LENGTH_SHORT).show();
                    Double latitude=bundle.getDouble("latitude");
                    Double longitude=bundle.getDouble("longitude");
                    btAddr.setText(latitude + ","+longitude);
                    address_1 = btAddr.getText().toString();
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.cepc.myservice");
        getActivity().registerReceiver(broadcastReceiver,filter);
    }


    private int getValue(String s){
        //获取月和月之间的差值，暂时忽略了润年之分
        int m=0;
        switch (s){
            case "01":m=30;break;
            case "03":m=30;break;
            case "05":m=30;break;
            case "07":m=30;break;
            case "08":m=30;break;
            case "10":m=30;break;
            case "12":m=30;break;
            case "02":m=27;break;
            case "04":m=29;break;
            case "06":m=29;break;
            case "09":m=29;break;
            case "11":m=29;break;
        }
        return m;
    }
}