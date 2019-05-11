package com.example.homepager_older.fragment.minefragment.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.homepager_older.R
import kotlinx.android.synthetic.main.medicine_dialog.*

/**
 * 添加药品弹窗
 * @author weitianliang
 */
class MedicineDialog(
    context: Context
) : Dialog(context, R.style.dialog) {

    private var click: OnClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine_dialog)

        cancel.setOnClickListener {
            click?.cancelClick()
        }

        sure.setOnClickListener {
            click?.sureClick(medicineName.text.toString(), medicineCount.text.toString(), medicineTime.text.toString())
            medicineName.setText("")
            medicineCount.setText("")
        }
    }

    fun setName(name: String) {
        medicineName.setText(name)
    }

    fun setCount(count: String) {
        medicineCount.setText(count)
    }

    fun setTime(time: String) {
        medicineTime.setText(time)
    }

    interface OnClick {
        fun sureClick(medicineName: String, medicineCount: String, medicineTime: String)
        fun cancelClick()
    }

    fun setOnClick(click: OnClick) {
        this.click = click
    }
}