package com.example.tools.FallDeteation

import com.example.tools.model.BaseStringModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *
 * @author WeiTianLiang
 */
interface GetFallDeteationInterface {

    @POST("fall/record/save")
    fun postFallSave(@Body body: RequestBody): Call<BaseStringModel>

}