package com.example.tools.fragment.interfaces

import com.example.tools.fragment.model.OrderModel
import com.example.tools.fragment.model.ParentOrderModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *
 * @author WeiTianLiang
 */
interface GetIssueInterface {

    @POST("order/save")
    fun postOrderSave(@Body body: RequestBody): Call<OrderModel>

    @GET("order/{orderNo}")
    fun getOrderWithOrderNo(@Path("orderNo") orderNo: String): Call<OrderModel>

    @GET("order/parent")
    fun getParentOrder(@Query("nickname") nickname: String): Call<ParentOrderModel>

}