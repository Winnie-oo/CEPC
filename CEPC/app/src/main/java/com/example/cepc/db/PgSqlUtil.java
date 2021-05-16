package com.example.cepc.db;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class PgSqlUtil {

    public PgSqlUtil() {
    }

    public static String getJsonContent(String url_path) {
        try{
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            int response = connection.getResponseCode();
            //System.out.println("PgUtil中数据："+connection);
            if (response== HttpURLConnection.HTTP_OK)
            {
                return changeInputStream(connection.getInputStream());
            }else {
                System.out.println(response);
                return "not exsits";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String postJsonContent(String url_path,String obj) {
        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(obj.getBytes());
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseCode = connection.getResponseMessage();
            if(responseCode.equals("success")){
                String result = changeInputStream(connection.getInputStream());//将流转换为字符串。
                System.out.println("result+++++++++++++++++++++++++++++++"+result);
                Log.d("填报成功","result============="+result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    public static String putJsonContent(String url_path) {
        return "";
    }
    public static void deleteJsonContent(String url_path) {


    }

    private static String changeInputStream(InputStream inputStream) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
    }
}
