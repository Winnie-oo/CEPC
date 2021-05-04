package com.example.cepc.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.cepc.R;
import com.example.cepc.db.DataBaseHelper;
import com.example.cepc.model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItmFragment2 extends Fragment {

    private final static String AUTHORITY = "com.example.cepc.DataContentProvider";
    private final static Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/"+ DataBaseHelper.USERS_TABLE_NAME);
    private final static Uri RECORD_URI = Uri.parse("content://" + AUTHORITY + "/"+DataBaseHelper.RECORDS_TABLE_NAME);
    private Context mContext;

    private String name_2;
    private int daymark_2;
    private ImageView imQR_code;
    private Button mUpdate_2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_2, container, false);
        imQR_code  = root.findViewById(R.id.qr_code);
        mUpdate_2 = root.findViewById(R.id.update);
        name_2 = getActivity().getIntent().getExtras().getString("username");
        createQRcode("It is "+name_2+"'s QR code");

        mUpdate_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQRcode("It is "+name_2+"'s QR code");
            }
        });
        return root;
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
        int n = queryUserValue(name);//查找连续填报是否超过七日
        if(n!=0){
            i = 0;
            if (n>7){ i=i+1; }
            n = queryRecordValue(name);//查找发烧记录以及确诊记录，来判断返回值
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


    private int queryUserValue(String name) {
        Cursor cursor = mContext.getContentResolver().query(USER_URI, new String[]{"*"},"username =?",new String[]{ name },null);
        int daymark =0;
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getInt(cursor.getColumnIndex("daymarks")));
            daymark = user.getDaymarks();
        }
        cursor.close();
        return daymark;
    }
    private int queryRecordValue(String name) {
        int m = 0;//0为危险，1为限制，2为安全
        Cursor cursor = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},
                "user_name =? and temperature > ?",new String[]{ name,"37.4"},null);
        if (cursor.getCount()==0) {
            //判断有无发烧，无发烧则+1
            cursor.close();
            m=m+1;
            //所有记录和patient=“否”的记录作比较，一样则不属于四类人员，不一样则属于四类人员
            cursor= mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},
                    "user_name=? and patient=?",new String[]{ name , "否" },null);
            int a = cursor.getCount();
            cursor.close();
            cursor = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},
                    "user_name=?",new String[]{ name },null);
            int b =cursor.getCount();
            if (a==b) {m=m+1;}
            else {m=0;}
        }
        cursor.close();
//        Cursor cursor1 = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},"user_name =? and temperature > ?",new String[]{ name,"37.4"},null);
//        if (cursor1.getCount()==0) {
//            cursor1.close();
//            m=m+1;
//            Cursor cursor2= mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},"user_name=? and patient=?",new String[]{ name , "否" },null);
//            int a = cursor2.getCount();
//            cursor2.close();
//            Cursor cursor3 = mContext.getContentResolver().query(RECORD_URI, new String[]{"*"},"user_name=?",new String[]{ name },null);
//            int b =cursor3.getCount();
//            if (a==b) {m=m+1;}
//            else {m=0;}
//            cursor3.close();
//        }
//        cursor1.close();
        return m;
    }
}