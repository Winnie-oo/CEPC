package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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

    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        setLocationOption();



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
            setContentView(R.layout.over);
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
                mLocationClient.start();

            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNotDouble(etTemperature.getText().toString())){
                    Toast.makeText(RecordActivity.this, "体温输入不规范！", Toast.LENGTH_SHORT).show();
                }
                else {
                    address_1 = btAddr.getText().toString();
                    System.out.println(address_1);
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

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            String town = location.getTown();    //获取乡镇信息

            System.out.println(province+city+district+street);
            btAddr.setText(province+city+district+street);
        }
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        option.setNeedNewVersionRgc(true);
//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
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