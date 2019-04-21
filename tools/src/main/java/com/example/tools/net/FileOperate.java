package com.example.tools.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreferences的操作
 * Created by WTL on 2018/6/7.
 */

public class FileOperate {

    /**
    * 写入
    * */
    public static void writeFile(String cookie, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("cookie",Context.MODE_PRIVATE).edit();
        editor.putString("cookie",cookie);
        editor.apply();
    }
    /**
    * 读取
    * */
    public static String readFile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("cookie",Context.MODE_PRIVATE);
        String cookie = preferences.getString("cookie","");
        return cookie;
    }

}
