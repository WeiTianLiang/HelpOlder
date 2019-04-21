package com.example.tools.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.example.tools.R
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import com.example.tools.view.MyViewPagerAdapter
import com.example.tools.view.addBottomBar
import java.io.FileNotFoundException

/**
 * Activity跳转方法
 * @author weitianliang
 */

/**
 * 无参跨model跳转
 */
fun jumpActivity(path: String, activity: Activity) {
    ARouter.getInstance()
        .build(path)
        .withTransition(R.anim.activity_right_out, R.anim.activity_right_in)
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
 * Activity中hide Fragment
 */
fun AppCompatActivity.showFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction { show(fragment) }
}

/**
 * 从相册获取图片
 */
fun doGetPicture(activity: Activity) {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT//Pick an item from the data
    intent.type = "image/*"//从所有图片中进行选择
    intent.putExtra("crop", "true")//设置为裁切
    intent.putExtra("aspectX", 1)//裁切的宽比例
    intent.putExtra("aspectY", 1)//裁切的高比例
    intent.putExtra("outputX", 100)//裁切的宽度
    intent.putExtra("outputY", 100)//裁切的高度
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())//裁切成的图片的格式
    activity.startActivityForResult(intent, 123)
}
