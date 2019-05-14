package com.example.homepager_children.fragment.minefragment.view.presenter

import com.example.homepager_children.fragment.minefragment.view.model.ChildModel
import com.example.homepager_children.fragment.minefragment.view.model.ChildParentModel
import com.example.tools.model.BaseStringModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *
 * @author WeiTianLiang
 */
interface GetChildInterface {

    @GET("child/batch")
    fun getChildData(@Query("nickname") nickname: String): Call<ChildModel>

    @PUT("child/update")
    fun putChildDate(@Body body: RequestBody): Call<BaseStringModel>

    @GET("child/batch/parent")
    fun getParentData(@Query("nickname") nickname: String): Call<ChildParentModel>

    @DELETE("parent/child/delete")
    fun deleteChildrenDate(@Query("parent") parent: String, @Query("child") child: String): Call<BaseStringModel>

    @POST("parent/child/save")
    fun postChildrenDate(@Body body: RequestBody): Call<BaseStringModel>

    @GET("fall/record/batch/list")
    fun getOlderState(@Query("nickname") nickname: String): Call<FallModel>

}