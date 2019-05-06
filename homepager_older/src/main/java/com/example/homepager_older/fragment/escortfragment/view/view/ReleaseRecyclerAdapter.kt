package com.example.homepager_older.fragment.escortfragment.view.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homepager_older.R
import com.example.homepager_older.fragment.escortfragment.view.model.HistoryListModel
import kotlinx.android.synthetic.main.history_item.view.*

/**
 * 订单记录adapter
 * @author WeiTianLiang
 */
class ReleaseRecyclerAdapter(
    private val list: ArrayList<HistoryListModel>,
    private val context: Context
) : RecyclerView.Adapter<ReleaseRecyclerAdapter.ViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReleaseRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ReleaseRecyclerAdapter.ViewHolder, position: Int) {
        holder.view.orderNumber.text = list[position].historyNumber
        holder.view.orderName.text = list[position].historyName
        holder.view.orderTime.text = list[position].historyTime
    }

    fun addItem(model: HistoryListModel) {
        list.add(model)
        notifyItemInserted(list.size - 1)
        notifyItemRangeInserted(list.size - 1, list.size)
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}