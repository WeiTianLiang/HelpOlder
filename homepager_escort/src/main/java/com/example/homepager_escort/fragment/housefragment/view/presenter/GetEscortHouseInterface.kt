package com.example.homepager_escort.fragment.housefragment.view.presenter

import com.example.homepager_escort.fragment.housefragment.view.model.MedicineModel
import com.example.homepager_escort.fragment.housefragment.view.model.StepCountModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author WeiTianLiang
 */
interface GetEscortHouseInterface {

    @GET("parent/record/batch/list")
    fun getOlderStep(@Query("nickname") nickname: String): Call<StepCountModel>

    @GET("medicine/compliance/batch/list")
    fun getMedicine(@Query("nickname") nickname: String): Call<MedicineModel>

}