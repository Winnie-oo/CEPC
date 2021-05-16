package com.example.cepc.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.cepc.db.PgSqlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItmFragment3 extends Fragment {

    private static final String IP="192.168.43.74";
    private static final String USER_URI = "http://"+IP+":8021/users";
    private final static String RECORD_URI = "http://"+IP+":8021/records/";
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
        SetDaymark(name_3);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private void SetDaymark(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = PgSqlUtil.getJsonContent(USER_URI+"/findByName/"+name);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject.getInt("day_mark"));
                    daymark_3 = jsonObject.getInt("day_mark");
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
        tv3_daymark.setText("连续打卡" + daymark_3 + "天");
    }

    private void initBroadcastReceiver(){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter = (ListViewAdapter) listView.getAdapter();
                if (intent.getAction().equals(action)) {
                    ItmFragment3.this.adapter.notifyDataSetChanged();
                    SetDaymark(name_3);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        getActivity().registerReceiver(broadcastReceiver,filter);
    }
}