package com.example.homepager_older.fragment.minefragment.presenter

import com.example.homepager_older.fragment.minefragment.model.ChildrenModel
import com.example.homepager_older.fragment.minefragment.model.MedicationModel
import com.example.homepager_older.fragment.minefragment.model.OlderDataModel
import com.example.tools.model.BaseStringModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 *
 * @author WeiTianLiang
 */
interface GetOlderInterface {

    @GET("parent/batch")
    fun getOlderData(@Query("nickname") nickname: String): Call<OlderDataModel>

    @GET("medicine/compliance/batch/list")
    fun getMedicationData(@Query("nickname") nickname: String): Call<MedicationModel>

    @POST("medicine/compliance/save")
    fun postMedicationAdd(@Body body: RequestBody): Call<BaseStringModel>

    @PUT("parent/update")
    fun putOlderData(@Body body: RequestBody): Call<BaseStringModel>

    @PUT("medicine/compliance/update")
    fun putMedicationData(@Body body: RequestBody): Call<BaseStringModel>

    @GET("parent/batch/child")
    fun getChildrenData(@Query("nickname") nickname: String): Call<ChildrenModel>

    @PUT("parent/child/update")
    fun putChildrenDate(@Body body: RequestBody): Call<BaseStringModel>

    @POST("parent/child/save")
    fun postChildrenDate(@Body body: RequestBody): Call<BaseStringModel>

    @DELETE("parent/child/delete")
    fun deleteChildrenDate(@Query("parent") parent: String, @Query("child") child: String): Call<BaseStringModel>
}