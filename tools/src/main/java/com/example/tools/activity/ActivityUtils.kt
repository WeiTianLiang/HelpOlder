package com.example.tools.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tools.R

/**
 * Activity跳转方法
 * @author weitianliang
 */

/**
 * 无参跨model跳转
 */
fun jumpActivity(path: String, activity: Activity, nickname: String? = null, id: Int = -1) {
    ARouter.getInstance()
        .build(path)
        .withTransition(R.anim.activity_right_out, R.anim.activity_right_in)
        .withString("nickname", nickname)
        .withInt("ID", id)
        .navigation()
    activity.finish()
}

/**
 * 结束Activity
 */
fun finishWithAnimal(activity: Activity) {
    activity.finish()
    activity.overridePendingTransition(R.anim.activity_right_out, R.anim.activity_right_in)
}

/**
 * 简化Activity添加Fragment的写法
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

/**
 * Fragment中add Fragment
 */
fun Fragment.addFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.inTransaction { add(frameId, fragment) }
}

/**
 * Activity中replace Fragment
 */
fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.inTransaction { replace(frameId, fragment) }
}

/**
 * Activity中hide Fragment
 */
fun Fragment.hideFragment(fragment: Fragment) {
    childFragmentManager.inTransaction { hide(fragment) }
}

/**
 * Activity中show Fragment
 */
fun Fragment.showFragment(fragment: Fragment) {
    childFragmentManager.inTransaction { show(fragment) }
}

/**
 * Activity中add Fragment
 */
fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

/**
 * Activity中replace Fragment
 */
fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

/**
 * Activity中hide Fragment
 */
fun AppCompatActivity.hideFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { hide(fragment) }
}

/**
 * Activity中show Fragment
 */
fun AppCompatActivity.showFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { show(fragment) }
}

/**
 * 从相册获取图片
 */
fun doGetPicture(activity: Activity? = null, fragment: Fragment? = null) {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    intent.type = "image/*" // 从所有图片中进行选择
    intent.putExtra("crop", "true") // 设置为裁切
    intent.putExtra("aspectX", 1) // 裁切的宽比例
    intent.putExtra("aspectY", 1) // 裁切的高比例
    intent.putExtra("outputX", 100) // 裁切的宽度
    intent.putExtra("outputY", 100) // 裁切的高度
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())//裁切成的图片的格式
    if (activity != null) {
        activity.startActivityForResult(intent, 123)
    } else {
        fragment?.startActivityForResult(intent, 123)
    }
}
