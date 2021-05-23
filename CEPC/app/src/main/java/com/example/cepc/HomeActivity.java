package com.example.cepc;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cepc.db.PgSqlUtil;
import com.example.cepc.ui.main.CodeActivity;
import com.example.cepc.ui.main.MineActivity;
import com.example.cepc.ui.main.RecordActivity;
import com.example.cepc.ui.main.VaccinesActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeActivity extends AppCompatActivity implements View.OnTouchListener {

    private LinearLayout mTitleBar;
    private ScrollView mScrollView;
    private TextView mText_0,mText_4,mText_3,mText_5,mText_6,mText_7,mText_8,Top_text;
    private String myName;
    private Bundle bundle;
    private CardView myCard_1,myCard_2;

    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records";
    private final static String VACCINE_URI = "http://"+IP+":8021/vaccines";
    private final static String COMMUNITY_URI = "http://"+IP+":8021/community";
    private final static String APPOINTRECORD_URI = "http://"+IP+":8021/appointRecord";
    //图片的高度
    private float imageViewHeight;
    //titlebar的高度
    private float titleBarHeight;
    //滑动的距离
    private float scrollY;
    //开始设置透明度的高度
    private float startHeight;

    public HomeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        SomeListener();
    }

    private void initView() {
        myCard_1 =findViewById(R.id.cardView_1);
        myCard_2 = findViewById(R.id.cardView_2);
        mTitleBar =  findViewById(R.id.titleBar);
        //mTitle = (TextView) findViewById(R.id.title);
        mScrollView = findViewById(R.id.id_scrollView);
        Top_text =findViewById(R.id.theTopText);
        mScrollView.setOnTouchListener(this);
        mTitleBar.getBackground().setAlpha(0);
        myName = HomeActivity.this.getIntent().getExtras().getString("username");
        updateText();

        mText_0=findViewById(R.id.text0_0);
        mText_3=findViewById(R.id.text0_3);
        mText_4=findViewById(R.id.text0_4);
        mText_5=findViewById(R.id.text0_5);
        mText_6=findViewById(R.id.text0_6);
        mText_8=findViewById(R.id.text0_8);

//        //让TextView失去焦点
//        mText_0.setFocusable(false);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(COMMUNITY_URI+"/find");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    mText_6.setText("剩余名额：" + jsonObject.getString("vaccines"));
                    mText_8.setText("确诊人数："+jsonObject.getString("patients"));
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
        initBroadcastReceiver ();
    }


    private void SomeListener(){
        mText_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转RecordActivity
                Intent intent = new Intent(HomeActivity.this, RecordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mText_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转CodeActivity
                Intent intent = new Intent(HomeActivity.this, CodeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mText_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转VaccinesActivity
                Intent intent = new Intent(HomeActivity.this, VaccinesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mText_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转RecordActivity
                Intent intent = new Intent(HomeActivity.this, MineActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        myCard_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转VaccinesActivity
                Intent intent = new Intent(HomeActivity.this, VaccinesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void updateText(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String date_0 = simpleDateFormat.format(date);
        //查看今天是否填报
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(RECORD_URI+"/findByNameAndDate/"+myName+"/"+date_0);
                if(!result.isEmpty()) {
                    Top_text.setText("今日您已填报！");
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


    //获取广播数据
    public void initBroadcastReceiver (){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (intent.getAction().equals("appoint_cancle")) {
                    initView();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("appoint_cancle");
        HomeActivity.this.registerReceiver(broadcastReceiver,filter);
    }

    /**
     * 测量控件的高度
     * @param view
     * @return
     */
    private float getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        Log.i("zyh","Height :  "+view.getMeasuredHeight());
        return  view.getMeasuredHeight();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                scrollY= mScrollView.getScrollY();
                setUpTitleBarBackground(scrollY);
                break;
        }
        return false;
    }


    /**
     *  动态设置导航栏背景透明度
     * @param scrollY
     */
    private  void setUpTitleBarBackground(float scrollY){

        Log.i("Zyh--setUpTitleBarBackg","ScrollY  :"+scrollY);

        if (scrollY <=startHeight ){
            mTitleBar.getBackground().setAlpha(0);
        }else  if (scrollY> startHeight && scrollY <= imageViewHeight-titleBarHeight) {
            //注意这个值 它是关键
            float realHeight = scrollY-startHeight;
            Log.i("Zyh--realHeight  :", "" + realHeight);
            //（有效的滑动距离 - 开始设置透明度的高度）/ 导航栏的高度 * 255就是我们要的透明度了
            int alpha = (int)Math.floor((realHeight / titleBarHeight * 255));
            Log.i("Zyh--alpha", "alpha  :" + alpha);
            //将我们计算好的透明度 赋值给导航栏就可以了。
            mTitleBar.getBackground().setAlpha(alpha);
        }else if (scrollY >imageViewHeight ){
            mTitleBar.getBackground().setAlpha(255);
        }
    }

}

