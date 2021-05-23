package com.example.cepc.db;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class PgSqlUtil {

    public PgSqlUtil() {
    }

    public static String getJsonContent(String url_path) {
        try{
            System.out.println("get请求中URL-------"+url_path);
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
                System.out.println("get请求中状态码-------"+response);
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
            String result="";
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(obj);
            outputStream.flush();
            outputStream.close();
            System.out.println("post状态码-------"+connection.getResponseCode());
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = null;
                while((inputLine=in.readLine())!=null)
                {
                    result+=inputLine;
                }
                System.out.println("post中result------------"+result);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String putJsonContent(String url_path) {
        try {
            String result="";
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            System.out.println("put状态码-------"+connection.getResponseCode());
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){

                System.out.println("put中result------------"+result);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void deleteJsonContent(String url_path) {
        try{
            URL url = new URL(url_path);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();
            int response = httpCon.getResponseCode();
            String string = Integer.toString(response);
            System.out.println("delete中状态码-----"+string);
            //System.out.println("PgUtil中数据："+connection);

        }catch (Exception e) {
            e.printStackTrace();
        }
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
