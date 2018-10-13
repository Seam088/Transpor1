package com.bang.transpor1.request;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 12457 on 2018/9/19.
 */

public class BaseRequest {


    public static String postRequest(String date, String path) {
        String result = "";
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");  //设置POST方式请求数据
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");  //设置请求报文头，设定请求数据类型
            conn.setRequestProperty("Content-Length", date.getBytes().length + "");      //设置请求数据长度
            conn.setDoOutput(true); // 打开输出流
            outputStream = conn.getOutputStream();
            outputStream.write(date.getBytes());
            System.out.println("===========>" + conn.getResponseCode() + "<============");

            if (conn == null) {
                return "";
            } else {
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    while ((len = inputStream.read(bytes)) != -1) {
                        byteArrayOutputStream.write(bytes, 0, len);
                    }
                    byte[] b = byteArrayOutputStream.toByteArray();
                    result = new String(b);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closePutStream(outputStream,inputStream,byteArrayOutputStream);
        }
        return result;
    }

    public static String postRequest1(String data, String path) {
        String result = "";
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("Content-Length", data.getBytes().length + "");

            conn.setDoOutput(true);
            outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes());
            System.out.println("----" + conn.getResponseCode());
            if (conn == null) {
                return "";
            } else {
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    byteArrayOutputStream = new ByteArrayOutputStream();

                    while ((len = inputStream.read(bytes)) != -1) {
                        byteArrayOutputStream.write(bytes, 0, len);
                    }

                    byte[] b = byteArrayOutputStream.toByteArray();
                    result = new String(b);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closePutStream(outputStream,inputStream,byteArrayOutputStream);
        }
        return result;
    }

    public static String getRequest(String path) {
        //定义一个接收Json
        String result = "";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置本次Http请求使用的方法
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            // 设置连接网络的超时时间
            conn.setConnectTimeout(5000);
            conn.disconnect();
            if (conn == null) {
                return "";
            } else {
                if (conn.getResponseCode() == 200) {   //获取连接响应码，200为成功，如果为其他，均表示有问题
                    // getInputStream获取服务端返回的数据流
                    inputStream = conn.getInputStream();
                    //把服务端返回的输入流转换成字符串格式
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    while ((len = inputStream.read(bytes)) != -1) {
                        byteArrayOutputStream.write(bytes, 0, len);
                    }
                    byte[] b = byteArrayOutputStream.toByteArray();
                    result = new String(b);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closePutStream(null,inputStream,byteArrayOutputStream);
        }
        return result;
    }

    /*
    * 关闭流
    * */
    public static void closePutStream(OutputStream outputStream, InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}