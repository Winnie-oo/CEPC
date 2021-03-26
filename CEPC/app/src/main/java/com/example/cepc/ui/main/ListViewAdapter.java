package com.example.cepc.ui.main;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cepc.R;
import com.example.cepc.db.DataBaseHelper;
import com.example.cepc.model.Record;



public class ListViewAdapter extends BaseAdapter {
    private final static String AUTHORITY = "com.example.cepc.DataContentProvider";
    private final static Uri RECORD_URI = Uri.parse("content://" + AUTHORITY + "/"+DataBaseHelper.RECORDS_TABLE_NAME);
    private Context mContext;

    private String mName;
    private LayoutInflater mLayoutInflater;

    public ListViewAdapter(Context context,String name){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mName = name;
    }

    @Override
    public int getCount() {
        Cursor cursor = queryValue(mName);
        if (cursor.getCount()>14)
            return 14;
        else
            return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView tv3_temperature;
        public TextView tv3_patient;
        public TextView tv3_date;
        public TextView tv3_address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.record_item,null);
            holder = new ViewHolder();
            holder.tv3_temperature = (TextView)convertView.findViewById(R.id.item_temperature);
            holder.tv3_patient = (TextView)convertView.findViewById(R.id.item_patient);
            holder.tv3_date = (TextView)convertView.findViewById(R.id.item_date);
            holder.tv3_address = (TextView)convertView.findViewById(R.id.item_address);
            convertView.setTag(holder);
        }else { holder = (ViewHolder) convertView.getTag(); }
        Cursor cursor = queryValue(mName);
        if (cursor!= null) {
            if (cursor.moveToPosition(cursor.getCount()-position-1)) {
                    Record record = new Record(
                            cursor.getInt(cursor.getColumnIndex("record_id")),
                            cursor.getString(cursor.getColumnIndex("user_name")),
                            cursor.getString(cursor.getColumnIndex("temperature")),
                            cursor.getString(cursor.getColumnIndex("patient")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getString(cursor.getColumnIndex("address")));
                    holder.tv3_temperature.setText(record.getTemperature()+"℃");
                    holder.tv3_patient.setText("是否为四类患者："+record.getPatient());
                    holder.tv3_date.setText("日期："+record.getDate());
                    holder.tv3_address.setText("地点(经纬度)："+record.getAddress());
            }
            cursor.close();
        }
        return convertView;
    }

    private Cursor queryValue(String name) {
        Cursor cursor = mContext.getContentResolver().query(RECORD_URI, new String[] {"*"},"user_name =?",new String[]{ name },null);
        return cursor;
    }

}

