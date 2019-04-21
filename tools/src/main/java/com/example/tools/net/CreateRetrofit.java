package com.example.tools.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * 创建retrofit对象并实例化
 * Created by WTL on 2018/6/5.
 */

public class CreateRetrofit {

    public static Retrofit requestRetrofit(String str) {
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        Retrofit retrofit = null;
        /*
        * 创建Retrofit对象
        * */
        if(str!=null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://123.206.82.241:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(AddHeader.addHeadersClient(str))
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://123.206.82.241:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
