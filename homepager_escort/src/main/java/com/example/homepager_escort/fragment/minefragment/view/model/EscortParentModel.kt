package com.example.homepager_escort.fragment.minefragment.view.model

/**
 *
 * @author WeiTianLiang
 */
class EscortParentModel {

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