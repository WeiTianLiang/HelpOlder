package com.example.tools.picture

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

/**
 * 对图片进行处理
 * @author weitianliang
 */

/**
 * 对图片进行旋转
 */
fun rotateImage(bitmap: Bitmap, degree: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

/**
 * 获取图片的角度
 */
@SuppressLint("Recycle")
fun getOrientation(context: Context, photoUri: Uri): Float {
    var orientation = 0f
    val cursor = context.contentResolver.query(
        photoUri,
        arrayOf(MediaStore.Images.ImageColumns.ORIENTATION),
        null,
        null,
        null
    )
    if (cursor != null) {
        if (cursor.count != 1) {
            return -1f
        }
        cursor.moveToFirst()
        orientation = cursor.getFloat(0)
        cursor.close()
    }
    return orientation
}

/**
 * 将图片转化为文件
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun imagePath(context: Context, photoUri: Uri): String {
    val filePathArray = arrayOf(MediaStore.Images.ImageColumns.ORIENTATION)
    val cursor = context.contentResolver.query(
        photoUri,
        filePathArray,
        null,
        null,
        null
    )
    cursor.moveToFirst()
    val columnIndex = cursor.getColumnIndex(filePathArray[0])
    val imgPath = cursor.getString(columnIndex)
    cursor.close()
    return imgPath
}
