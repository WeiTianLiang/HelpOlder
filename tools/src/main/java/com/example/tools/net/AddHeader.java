package com.example.tools.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 添加请求头
 * Created by WTL on 2018/6/6.
 */

public class AddHeader {

    public static OkHttpClient addHeadersClient(final String headers) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        /*
                        * 向post请求事件添加请求头
                        * */
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", headers)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return client;
    }

}
