package com.bang.transpor1.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bang on 2018/9/26.
 *      欢迎界面SplashActivity工具类
 */

public class SharePreUtil {
    private static SharedPreferences sp;

   /*
    * 保存数据
    */
    public static void saveBoolean(Context context , String key , boolean value){
        if (sp == null){
            sp = context.getSharedPreferences("config" , Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key , value).commit();
    }
    public static void saveString(Context context , String key , String value){
        if (sp == null){
            sp = context.getSharedPreferences("config" , Context.MODE_PRIVATE);
        }
        sp.edit().putString(key , value).commit();
    }
    /*
    * 取出数据
    */
    public static boolean getBoolean(Context context , String key , Boolean defValue){
        if (sp == null){
            sp = context.getSharedPreferences("config" , Context.MODE_PRIVATE);
        }
        return  sp.getBoolean(key, defValue);
    }

    public static String getString(Context context , String key , String defValue){
        if (sp == null){
            sp = context.getSharedPreferences("config" , Context.MODE_PRIVATE);
        }
        return  sp.getString(key, defValue);
    }

    //清除数据
    public static String clearString(Context context , String key){
        if (sp == null){
            sp = context.getSharedPreferences("config" , Context.MODE_PRIVATE);
        }
        return  sp.getString(key, "");
    }

}
