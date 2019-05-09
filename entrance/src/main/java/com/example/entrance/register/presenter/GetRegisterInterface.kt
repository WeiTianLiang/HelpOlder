package com.example.entrance.register.presenter

import com.example.entrance.register.model.RegisterModel
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
    fun createParentUser(@Body body: RequestBody): Call<RegisterModel>

    @POST("child/register")
    fun createChildUser(@Body body: RequestBody): Call<RegisterModel>

    @POST("parent/register")
    fun createEscortUser(@Body body: RequestBody): Call<RegisterModel>

    @GET("parent/check")
    fun parentCheck(@Query("nickname") nickname: String): Call<RegisterModel>

    @GET("parent/check")
    fun escortCheck(@Query("nickname") nickname: String): Call<RegisterModel>

    @GET("parent/check")
    fun childrenCheck(@Query("nickname") nickname: String): Call<RegisterModel>

}