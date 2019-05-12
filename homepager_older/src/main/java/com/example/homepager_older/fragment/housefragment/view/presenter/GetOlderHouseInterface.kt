package com.example.homepager_older.fragment.housefragment.view.presenter

import com.example.homepager_older.fragment.housefragment.view.model.MedicineModel
import com.example.homepager_older.fragment.housefragment.view.model.StepModel
import com.example.tools.model.BaseStringModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *
 * @author WeiTianLiang
 */
interface GetOlderHouseInterface {

    @POST("parent/record/save")
    fun postOlderStep(@Body body: RequestBody): Call<BaseStringModel>

    @GET("parent/record/batch/list")
    fun getOlderStep(@Query("nickname") nickname: String): Call<StepModel>

    @PUT("parent/update")
    fun putParentMap(@Body body: RequestBody): Call<BaseStringModel>

    @GET("medicine/compliance/batch/list")
    fun getMedicine(@Query("nickname") nickname: String): Call<MedicineModel>

}