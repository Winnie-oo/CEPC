package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cepc.LoginActivity;
import com.example.cepc.MyService;
import com.example.cepc.R;
import com.example.cepc.db.DataBaseHelper;
import com.example.cepc.model.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItmFragment3 extends Fragment {

    private final static String AUTHORITY = "com.example.cepc.DataContentProvider";
    private final static Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/"+DataBaseHelper.USERS_TABLE_NAME);
    private final static Uri RECORD_URI = Uri.parse("content://" + AUTHORITY + "/"+DataBaseHelper.RECORDS_TABLE_NAME);
    private Context mContext;

    static final String action = "ha";

    private String name_3;
    private int daymark_3;
    private TextView tv3_name, tv3_daymark;
    private ListView listView;
    private ListViewAdapter adapter;
    private Button mbt_exit;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);mContext=this.getContext(); }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        tv3_name = view.findViewById(R.id.person_name);
        tv3_daymark = view.findViewById(R.id.person_daymark);
        listView = view.findViewById(R.id.person_lv);
        mbt_exit = view.findViewById(R.id.exit_3);
        initBroadcastReceiver ();

        name_3 = getActivity().getIntent().getExtras().getString("username");
        tv3_name.setText(name_3);
        daymark_3 = queryDaymark(name_3);
        tv3_daymark.setText("连续打卡" + daymark_3 + "天");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Cursor cursor = queryValue(name_3);
        if(cursor!=null)
            listView.setAdapter(new ListViewAdapter(ItmFragment3.this.getContext(),name_3));
        mbt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(getContext(), MyService.class);
                getActivity().stopService(stopIntent);//退出账户的同时，关闭服务
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private int queryDaymark(String name) {
        Cursor cursor = mContext.getContentResolver().query(USER_URI, new String[]{"user_id", "username","password","daymarks"},"username=?",new String[]{ name },null);
        int mcount = 0;
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getInt(cursor.getColumnIndex("daymarks")));
            mcount = user.getDaymarks();
        }
        cursor.close();
        return mcount;
    }

    private Cursor queryValue(String name) {
        Cursor cursor = mContext.getContentResolver().query(RECORD_URI, new String[] {"*"},"user_name =?",new String[]{ name },null);
        return cursor;
    }

    private void initBroadcastReceiver(){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter = (ListViewAdapter) listView.getAdapter();
                if (intent.getAction().equals(action)) {
                    ItmFragment3.this.adapter.notifyDataSetChanged();
                    daymark_3 = queryDaymark(name_3);
                    tv3_daymark.setText("连续打卡" + daymark_3 + "天");
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        getActivity().registerReceiver(broadcastReceiver,filter);
    }
}