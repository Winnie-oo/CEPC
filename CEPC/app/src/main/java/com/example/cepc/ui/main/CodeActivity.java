package com.example.cepc.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cepc.R;
import com.example.cepc.db.PgSqlUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeActivity extends AppCompatActivity {

    private static final String IP = "192.168.43.74";
    private static final String USER_URI = "http://" + IP + ":8021/users";
    private final static String RECORD_URI = "http://" + IP + ":8021/records/";

    private String name_2;
    private int daymark_2, rank;//rank: 0为危险，1为限制，2为安全
    private ImageView imQR_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        imQR_code = findViewById(R.id.qr_code);
        name_2 = this.getIntent().getExtras().getString("username");
        createQRcode("It is " + name_2 + "'s QR code");

        imQR_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQRcode("It is "+name_2+"'s QR code");
            }
        });
    }
    public void createQRcode(String string){
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(string, BarcodeFormat.QR_CODE, 250, 250);
            int fixbit[]=new int[bitMatrix.getWidth()*bitMatrix.getHeight()];
            int color = Color.BLACK;
            switch (judge(name_2)){
                case -1:
                    break;
                case 0:
                    color = Color.RED; break;
                case 1:
                    color = Color.parseColor("#ffcc00"); break;
                case 2:
                    color = Color.GREEN; break;
            }
            for (int y=0;y<bitMatrix.getHeight();y++){
                for (int x=0;x<bitMatrix.getWidth();x++){
                    if (bitMatrix.get(x,y)){
                        fixbit[x+y*bitMatrix.getWidth()]= color;
                    }else{
                        fixbit[x+y*bitMatrix.getWidth()]=Color.WHITE;
                    }
                }
            }

            Bitmap bitmap=Bitmap.createBitmap(fixbit,bitMatrix.getWidth(),bitMatrix.getHeight(), Bitmap.Config.ARGB_8888);
            imQR_code.setImageBitmap(bitmap);
        }catch (WriterException e){
        }
    }

    private int judge(String name) {
        int i = -1;//0为危险，1为限制，2为安全
        int n = checkDayMark(name);//查找连续填报是否超过七日
        if(n!=0){
            i = 0;
            if (n>7){ i=i+1; }
            n = judgeRank(name);//查找发烧记录以及确诊记录，来判断返回值
            switch (n){
                case 0:
                    i=0;//红
                    break;
                case 2:
                    i=i+1;
                    break;
            }
        }
        return i;
    }


    private int checkDayMark(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(USER_URI+"/findByName/"+name);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    daymark_2 = jsonObject.getInt("day_mark");
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
        return daymark_2;
    }

    private int judgeRank(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(RECORD_URI+"/judgeRank/"+name);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println("result："+result);
                    rank=Integer.valueOf(result);
                    System.out.println("rank:"+rank);
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
        return rank;
    }
}