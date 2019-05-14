package com.example.tools.fragment.model

/**
 *
 * @author WeiTianLiang
 */
class OrderModel {

    val code: String? = null
    val data: Data? = null

    class Data {
        val id: Int? = null
        val orderNo: String? = null
        val position: String? = null
        val address: String? = null
        val emergencyStatus: Int? = null
        val parentEscort: String? = null
        val parentName: String? = null
        val healthStatus: Int? = null
        val escortType: Int? = null
        val escortStart: Long? = null
        val escortEnd: Long? = null
        val desc: String? = null
        val orderStatus: Int? = null
        val escortName: String? = null
        val escortRealName: String? = null
    }
}