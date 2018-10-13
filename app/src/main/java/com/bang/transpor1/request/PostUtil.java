package com.bang.transpor1.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 12457 on 2018/9/26.
 */

public class PostUtil {
    public static String getLogin(String username , String password){
        String path = "http://dev1.cn:8888";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action","login");
            jsonObject.put("object","user");
            jsonObject.put("username",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        System.out.println("username:"+username+"<-->password:"+password);
        return BaseRequest.postRequest(data , path);
    }

    public static String getReg(String username , String password){
        String path = "http://dev1.cn:8888";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action","reg");
            jsonObject.put("object","user");
            jsonObject.put("username",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        System.out.println("username:"+username+"<-->password:"+password);
        return BaseRequest.postRequest(data , path);
    }
}
