package com.example.cepc.db;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(obj);
            writer.close();

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String result = changeInputStream(connection.getInputStream());//将流转换为字符串。
                Log.d("kwwl","result============="+result);
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
            //System.out.println("Stream中数据："+sb.toString());
            return sb.toString();


//        String jsonString = "";
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        int len = 0;
//        byte[] data = new byte[1024];
//        try{
//            while ((len = inputStream.read(data)) != -1) {
//                outputStream.write(data, 0, len);
//            }
//            jsonString = new String(outputStream.toByteArray());
//            return jsonString;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
    }


//    public static Connection openConnection()
//            throws SQLException {
//        Connection conn = null;
//        final String DRIVER_NAME = "org.postgresql.Driver";
//        try {
//            Class.forName(DRIVER_NAME);
//            conn = (Connection) DriverManager.getConnection(URL, "postgres", "muxincute");
//            System.out.println("成功加载MySQL驱动！" + URL);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            e.printStackTrace();
//        }
//        return conn;
//    }
//    //查询用户是否存在、密码是否正确
//    public static int QueryUser(String id, String password)
//    {
//        int flag =0;
//        try
//        {
//            Connection conn = openConnection();
//            String sql = "select * from Users where Uid = "+ id ;
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.next())
//            {
//                String s = rs.getString("password");
//                if(password.equals(s)){
//                    flag = 1;
//                    System.out.println( "登陆成功！");
//                }
//                else {
//                    flag = -1;
//                    System.out.println( "密码错误！");
//                }
//            }
//            else {
//                System.out.println("用户名错误 or 不存在，请先注册！");
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println( "查询数据异常!");
//        }
//        return flag;
//    }
//
//    //新增用户
//    public static int InsertUser(String userid,String pd,String addr)
//    {
//        int flag = 2;
//        try
//        {
//            Connection conn = openConnection();
//            String sql = "select * from Users where Uid = "+ userid ;
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            if (rs.next())
//                System.out.println( "该账号已存在");
//            else {
//                sql = "insert into Users(Uid,password,address)" +
//                        " values ('"+userid+ "','" +pd+"','"+addr+"')";
//                stmt.executeUpdate(sql);
//                System.out.println( "用户注册成功!");
//                flag = 3;
//            }
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println( "插入用户异常!");
//        }
//        return flag;
//    }
//
//    //查询记录
//    public static ArrayList<String> QueryRecord(String id)
//    {
//        ArrayList<String> list = new ArrayList<>();
//        try
//        {
//            Connection conn = openConnection();
//            String sql = "select Users.Uid,address,Records.temperature,Records.date " +
//                    "from Users join Records on Records.Uid = Users.Uid and Records.Uid="+id;
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next())
//            {
//                list.add(rs.getString("address"));
//                list.add(rs.getString("temperature"));
//                list.add(rs.getString("date"));
//            }
//            System.out.println("查询记录成功!");
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("查询记录异常!");
//        }
//        return list;
//    }
//
//    //新增记录
//    public static int InsertRecord(String id,String temperature,String date)
//    {
//        int flag = 0;
//        try
//        {
//            Connection conn = openConnection();
//            String sql1 = "select * from Records where Records.Uid ="+id;
//            ResultSet rs1 = conn.createStatement(
//                    ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(sql1);
//            Double temp = Double.valueOf(temperature);
//            if (temp <= 37.4){
//                rs1.last();
//                System.out.println( rs1.getRow());
//                if(rs1.getRow()<=0) {
//                    Insert(id,temperature,date);
//                    System.out.println( "插入记录成功！————无数据时");
//                    flag =3;
//                }
//                else {
//                    rs1.last();
//                    String a = rs1.getString("date").substring(0,10);
//                    String b = date.substring(0,10);
//                    int c = rs1.getInt("Rid");
//                    String d = rs1.getString("temperature");
//                    Double d_ = Double.valueOf(d);
//                    if (a.equals(b) && d_<37.4 ){
//                        String sql = "update Records set date='"+ date +
//                                "',temperature='" + temperature +"' where Rid =" + c;
//                        Statement stmt = conn.createStatement();
//                        stmt.executeUpdate(sql);//修改记录
//                        stmt.close();
//                    }
//                    else {
//                        Insert(id,temperature,date);//插入记录
//                    }
//                    if(!SearchHigh(id))//查找有无高温记录
//                        flag =3;
//                    else flag =4;
//                }
//            }
//            else {
//                Insert(id,temperature,date);
//                flag =5;
//            }
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println( "插入记录异常!");
//        }
//        return flag;
//    }
//
//    public static void Insert(String id,String temperature,String date){
//        try
//        {
//            Connection conn = openConnection();
//            String sql = "insert into Records values ('"+id +"','" + temperature +"','"+ date +"')";
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate(sql);
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println( "插入记录异常!");
//        }
//    }
//
//    public static boolean SearchHigh(String id)
//    {
//        boolean flag = false;
//        try
//        {
//            Connection conn = openConnection();
//            String sql = "select * from Records where Records.Uid ="+id;
//            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(sql);
//            rs.last();
//            int i = 0;
//            do {
//                String str = rs.getString("temperature");
//                Double int_str = Double.valueOf(str);
//                i++;
//                if (int_str > 37.4)
//                    flag = true;
//            } while (rs.previous() && i < 14);
//            rs.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("查询High异常!");
//        }
//        return flag;
//    }

}
