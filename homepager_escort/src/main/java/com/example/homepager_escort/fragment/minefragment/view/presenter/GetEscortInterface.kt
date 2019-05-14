package com.example.homepager_escort.fragment.minefragment.view.presenter

import com.example.homepager_escort.fragment.minefragment.view.model.EscortModel
import com.example.homepager_escort.fragment.minefragment.view.model.EscortParentModel
import com.example.homepager_escort.fragment.minefragment.view.model.FallModel
import com.example.tools.model.BaseStringModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *
 * @author WeiTianLiang
 */
interface GetEscortInterface {

    @GET("escort/batch")
    fun getEscortData(@Query("nickname") nickname: String): Call<EscortModel>

    @PUT("escort/update")
    fun putEscortData(@Body body: RequestBody): Call<BaseStringModel>

    @GET("escort/batch/parent")
    fun getOlderData(@Query("nickname") nickname: String): Call<EscortParentModel>

    @DELETE("parent/escort/delete")
    fun deleteParentDate(@Query("parent") parent: String, @Query("escort") escort: String): Call<BaseStringModel>

    @POST("parent/escort/save")
    fun postEscortDate(@Body body: RequestBody): Call<BaseStringModel>

    @GET("fall/record/batch/list")
    fun getOlderState(@Query("nickname") nickname: String): Call<FallModel>

}