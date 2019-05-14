package com.example.tools.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tools.R
import com.example.tools.model.OrderListModel
import kotlinx.android.synthetic.main.history_item.view.*

/**
 * 订单记录adapter
 * @author WeiTianLiang
 */
class ReleaseRecyclerAdapter(
    private val list: ArrayList<OrderListModel>,
    private val context: Context,
    private val keyCode: Int
) : RecyclerView.Adapter<ReleaseRecyclerAdapter.ViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReleaseRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ReleaseRecyclerAdapter.ViewHolder, position: Int) {
        holder.view.orderNumber.text = list[position].orderNumber
        holder.view.orderName.text = list[position].orderName
        holder.view.orderTime.text = list[position].orderTime
        holder.view.orderLocation.text = list[position].orderLocation
        holder.view.orderState.text = list[position].orderState
        holder.view.showD.setOnClickListener {
            ARouter.getInstance().build("/tools/DetailActivity").withInt("key",keyCode).withString("orderNo", list[position].orderNumber).navigation()
        }
    }

    fun addItem(model: OrderListModel) {
        list.add(model)
        notifyItemInserted(list.size - 1)
        notifyItemRangeInserted(list.size - 1, list.size)
    }

    private fun deleteItem(position: Int) {
        if (list.size < 2 && list.size != 0) {
            list.removeAt(0)
            notifyDataSetChanged()
        } else {
            list.removeAt(position)
            notifyDataSetChanged()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}