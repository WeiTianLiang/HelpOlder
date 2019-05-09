package com.example.entrance.login.presenter

import com.example.entrance.register.model.RegisterModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author WeiTianLiang
 */
interface GetLoginInterface {

    @GET("parent/check")
    fun parentLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<RegisterModel>

    @GET("parent/check")
    fun escortLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<RegisterModel>

    @GET("parent/check")
    fun childrenLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<RegisterModel>

}