package com.example.homepager_older.fragment.minefragment.model

/**
 *
 * @author WeiTianLiang
 */
class ChildrenModel {

    val code: String? = null
    val data: Data? = null

    class Data {
        val childList: List<ChildList>? = null
    }

    class ChildList {
        val childCode: String? = null
        val gender: String? = null
        val name: String? = null
        val id: Int? = null
        val nickname: String? = null
    }

}