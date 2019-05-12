package com.example.entrance.register.presenter

import com.example.tools.model.BaseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * @author WeiTianLiang
 */
interface GetRegisterInterface {

    @POST("parent/register")
    fun createParentUser(@Body body: RequestBody): Call<BaseModel>

    @POST("child/register")
    fun createChildUser(@Body body: RequestBody): Call<BaseModel>

    @POST("escort/register")
    fun createEscortUser(@Body body: RequestBody): Call<BaseModel>

    @GET("parent/check")
    fun parentCheck(@Query("nickname") nickname: String): Call<BaseModel>

    @GET("escort/check")
    fun escortCheck(@Query("nickname") nickname: String): Call<BaseModel>

    @GET("child/check")
    fun childrenCheck(@Query("nickname") nickname: String): Call<BaseModel>

}