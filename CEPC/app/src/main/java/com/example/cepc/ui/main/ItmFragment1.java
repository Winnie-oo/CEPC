package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.cepc.model.Record;

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
    private final static String RECORD_URI = "http://"+IP+":8021/records/";
    private Context mContext;

    private ArrayList<String> arrayList = new ArrayList<>();

    public static final String action = "ha";
    private Button btAddr ;
    private EditText etTemperature;
    private RadioGroup mRadio;
    private Button btSubmit;
    private TextView tvDate;
    private String name_1;
    private String temperature_1;
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
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject.getString("name"));
                    System.out.println(jsonObject.getString("password"));
                    if(jsonObject.getString("date").isEmpty())
                        record_currect=false;
                    else {
                        record_currect=true;
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
                Intent startIntent = new Intent(mContext, MyService.class);
                getActivity().startService(startIntent); // 开始启动服务
                //注册广播接收器
                initBroadcastReceiver();
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperature_1 = etTemperature.getText().toString();
                address_1 = btAddr.getText().toString();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PgSqlUtil.postJsonContent(RECORD_URI+"/save",
                                "{name:"+name_1+
                                ",temperature:"+temperature_1+
                                ",patient:"+patient_1+
                                ",date:"+date_1+
                                ",address:"+address_1+"}");
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


//    private Cursor queryValue(String name,String date) {
//        Cursor cursor = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},"user_name =? and date=?",new String[]{ name,date },null);
//        return cursor;
//    }
//
//    private void updateDaymark(String updateName){
//        Cursor cursor = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},"user_name=?",new String[]{ updateName },null);
//        int mcount=0;
//        if (cursor.getCount()!= 0) {
//            if (cursor.moveToLast()) {
//                do{
//                    Record record = new Record(
//                            cursor.getInt(cursor.getColumnIndex("record_id")),
//                            cursor.getString(cursor.getColumnIndex("user_name")),
//                            cursor.getString(cursor.getColumnIndex("temperature")),
//                            cursor.getString(cursor.getColumnIndex("patient")),
//                            cursor.getString(cursor.getColumnIndex("date")),
//                            cursor.getString(cursor.getColumnIndex("address")));
//                    arrayList.add(record.getDate());
//                }while (cursor.moveToPrevious());
//            }
//            mcount=1;
//            for(int i =1;i<arrayList.size();i++){
//                String s1 = arrayList.get(i-1);
//                String s2 = arrayList.get(i);
//                int m = getValue(s2.substring(5,7));
//                if(Integer.valueOf(s1.substring(5,7)) == Integer.valueOf(s2.substring(5,7))){
//                    //判断日期是不是连续的
//                    if(Integer.valueOf(s1.substring(8,10)) == Integer.valueOf(s2.substring(8,10))+1){
//                        mcount=mcount+1;
//                    }else break;
//                } else if(Integer.valueOf(s1.substring(8,10))+m == Integer.valueOf(s2.substring(8,10))){
//                    mcount=mcount+1;
//                }else break;
//            }
//        }
//        arrayList.clear();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("daymarks",Integer.toString(mcount));
//        getActivity().getContentResolver().update(USER_URI,contentValues,"username = ?",new String[] {updateName});
//        cursor.close();
//    }

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