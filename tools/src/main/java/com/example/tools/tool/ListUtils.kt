package com.example.tools.tool

/**
 *
 * @author WeiTianLiang
 */
fun listMax(list: List<Int>): Int {

    var k = 0
    for (i in 0 until list.size) {
        if (list[k] < list[i]) {
            k = i
        }
    }
    return list[k]
}