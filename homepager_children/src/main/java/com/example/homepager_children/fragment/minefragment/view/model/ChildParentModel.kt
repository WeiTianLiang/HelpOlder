package com.example.homepager_children.fragment.minefragment.view.model

/**
 *
 * @author WeiTianLiang
 */
class ChildParentModel {

    val code: String? = null
    val data: Data? = null

    class Data {
        val parentList: List<ParentList>? = null
    }

    class ParentList {
        val parentCode: String? = null
        val gender: String? = null
        val name: String? = null
        val id: Int? = null
        val nickname: String? = null
    }

}