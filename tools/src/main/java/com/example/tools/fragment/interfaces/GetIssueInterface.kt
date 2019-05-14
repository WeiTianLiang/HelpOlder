package com.example.tools.fragment.interfaces

import com.example.tools.fragment.model.ChildParentModel
import com.example.tools.fragment.model.EscortParentModel
import com.example.tools.fragment.model.OrderModel
import com.example.tools.fragment.model.ParentOrderModel
import com.example.tools.model.BaseStringModel
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

    @PUT("order/update/escort")
    fun putWithEscort(@Query("nickname") nickname: String, @Query("id") id: Int): Call<BaseStringModel>

    @GET("escort/batch/parent")
    fun getOlderData(@Query("nickname") nickname: String): Call<EscortParentModel>

    @PUT("order/update/status")
    fun putOrderStatus(@Query("orderStatus") orderStatus: Int, @Query("id") id: Int): Call<BaseStringModel>

    @GET("child/batch/parent")
    fun getParentData(@Query("nickname") nickname: String): Call<ChildParentModel>

    @GET("order/valid")
    fun getValid(): Call<ParentOrderModel>

    @POST("parent/escort/save")
    fun postEscortDate(@Body body: RequestBody): Call<BaseStringModel>

}