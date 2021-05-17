package com.example.cepc;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.cepc.ui.main.CodeActivity;
import com.example.cepc.ui.main.MineActivity;
import com.example.cepc.ui.main.RecordActivity;



public class HomeActivity extends AppCompatActivity implements View.OnTouchListener {

    private LinearLayout mTitleBar;
    private ScrollView mScrollView;
    private TextView mText_0,mText_4,mText_3;
    private String myName;

    //图片的高度
    private float imageViewHeight;
    //titlebar的高度
    private float titleBarHeight;
    //滑动的距离
    private float scrollY;
    //开始设置透明度的高度
    private float startHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();


    }

    private void initView() {
        mTitleBar = (LinearLayout) findViewById(R.id.titleBar);
        //mTitle = (TextView) findViewById(R.id.title);
        mScrollView = (ScrollView) findViewById(R.id.id_scrollView);
        mScrollView.setOnTouchListener(this);
        mTitleBar.getBackground().setAlpha(0);
        myName = HomeActivity.this.getIntent().getExtras().getString("username");

        mText_0=findViewById(R.id.text0_0);
        mText_3=findViewById(R.id.text0_3);
        mText_4=findViewById(R.id.text0_4);
        //让TextView失去焦点
        mText_0.setFocusable(false);
        mText_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                myName = HomeActivity.this.getIntent().getExtras().getString("username");
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
                Bundle bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转RecordActivity
                Intent intent = new Intent(HomeActivity.this, CodeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mText_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", myName);
                //跳转RecordActivity
                Intent intent = new Intent(HomeActivity.this, MineActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

