package com.bang.transpor1.util;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by zzsgz on 2018/11/1.
 */

public class MyDBUtil {
    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface FailCallback {
        void onFail();
    }

    public static void getDataByPostInterface(final Map map, final SuccessCallback successCallback,
                                       final FailCallback failCallback){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    if(successCallback!=null){
                        successCallback.onSuccess(s);

                    }
                }else{
                    if(failCallback!=null){
                        failCallback.onFail();

                    }
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                String json=null;
                String str=null;
                try {
                    //  URL url = new URL("http://dev1.cn:8888");
                    URL url=new URL((String) map.get("url"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();

                    JSONObject js= (JSONObject) map.get("jsonobject");
//            JSONObject js = new JSONObject();
//            js.put("action", "get");
//            js.put("object", "traffic");

                    out.write(js.toString().getBytes());
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();//低级流
                        BufferedReader buff = new BufferedReader(new InputStreamReader(in));//高级流
                        while ((str=buff.readLine())!=null){
                            json+=str;
                        }
                        buff.close();
                        in.close();
                        out.flush();
                        out.close();
                        //  Message message = handle.obtainMessage();
                        //  message.obj = str;
                        //  handle.sendMessage(message);
                        //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json.substring(4,json.length());
            }

        }.execute();
    }


    public  static String getDataByPost(Map map){
        String json=null;
        String str=null;
        try {
          //  URL url = new URL("http://dev1.cn:8888");
            URL url=new URL((String) map.get("url"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream out = conn.getOutputStream();

            JSONObject js= (JSONObject) map.get("jsonobject");
//            JSONObject js = new JSONObject();
//            js.put("action", "get");
//            js.put("object", "traffic");

            out.write(js.toString().getBytes());
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();//低级流
                BufferedReader buff = new BufferedReader(new InputStreamReader(in));//高级流
                while ((str=buff.readLine())!=null){
                    json+=str;
                }
                buff.close();
                in.close();
                out.flush();
                out.close();
              //  Message message = handle.obtainMessage();
              //  message.obj = str;
              //  handle.sendMessage(message);
                //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    return json;
    }
}
