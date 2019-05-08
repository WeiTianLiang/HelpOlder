package com.example.tools.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tools.R
import com.example.tools.adapter.MedicineRecyclerAdapter
import com.example.tools.model.MedicineAndCount
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = "/tools/DetailActivity")
class DetailActivity : AppCompatActivity() {

    private val list1 = arrayListOf<MedicineAndCount>()
    private val adapter by lazy { MedicineRecyclerAdapter(list1,this) }

    var key = 0

    init {
        val medicineAndCount = MedicineAndCount()
        medicineAndCount.medicineName = "药物1"
        medicineAndCount.medicineCount = "一日三次"
        list1.add(medicineAndCount)
        val medicineAndCount1 = MedicineAndCount()
        medicineAndCount1.medicineName = "药物2"
        medicineAndCount1.medicineCount = "一日三次"
        list1.add(medicineAndCount1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ARouter.getInstance().inject(this)

        orderMedic.layoutManager = LinearLayoutManager(this)
        orderMedic.adapter = adapter

        key = intent.getIntExtra("key", 0)

        back.setOnClickListener {
            finish()
        }

        doIt.visibility = if(key == 1) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // 接受订单
        doIt.setOnClickListener {

        }
    }
}
