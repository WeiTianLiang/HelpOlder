package com.example.tools.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tools.R
import com.example.tools.model.ChildrenToOlder
import kotlinx.android.synthetic.main.recycler_item.view.*

/**
 * recyclerView 适配器
 * @author weitianliang
 */
class MyRecyclerViewAdapter(
    private var list: ArrayList<ChildrenToOlder>,
    val context: Context
) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    private var deleteClick: OnDeleteClick? = null

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.view.name.text = list[position].nametext
        holder.view.identity.text = list[position].identity
        holder.view.delete.setOnClickListener {
            deleteItem(position)
            deleteClick?.deleteClick(position)
        }
        holder.view.change.visibility = View.GONE
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

    fun upadte(list1: ArrayList<ChildrenToOlder>) {
        list = list1
        notifyDataSetChanged()
    }

    fun addItem(model: ChildrenToOlder) {
        list.add(model)
        notifyItemInserted(list.size - 1)
        notifyItemRangeInserted(list.size - 1, list.size)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface OnDeleteClick {
        fun deleteClick(position: Int)
    }

    fun setOnDeleteClick(deleteClick: OnDeleteClick) {
        this.deleteClick = deleteClick
    }

}
