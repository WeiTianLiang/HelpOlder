package com.example.entrance.login.presenter

import com.example.tools.model.BaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author WeiTianLiang
 */
interface GetLoginInterface {

    @GET("parent/login")
    fun parentLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<BaseModel>

    @GET("parent/login")
    fun escortLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<BaseModel>

    @GET("parent/login")
    fun childrenLogin(@Query("nickname") nickname: String, @Query("password") password: String): Call<BaseModel>

}