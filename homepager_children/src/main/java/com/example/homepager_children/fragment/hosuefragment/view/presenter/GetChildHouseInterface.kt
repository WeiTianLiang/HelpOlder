package com.example.homepager_children.fragment.hosuefragment.view.presenter

import com.example.homepager_children.fragment.hosuefragment.view.model.StepCountModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author WeiTianLiang
 */
interface GetChildHouseInterface {

    @GET("parent/record/batch/list")
    fun getOlderStep(@Query("nickname") nickname: String): Call<StepCountModel>

}