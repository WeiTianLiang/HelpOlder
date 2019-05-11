package com.example.tools.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tools.R
import com.example.tools.model.MedicineAndCount
import kotlinx.android.synthetic.main.recycler_item.view.*

/**
 * 药物 recyclerlist 适配器
 * @author weitianliang
 */
class MedicineRecyclerAdapter(
    private val list: ArrayList<MedicineAndCount>,
    private val context: Context
) : RecyclerView.Adapter<MedicineRecyclerAdapter.ViewHolder>() {

    private var changeClick: OnChangeClick? = null
    private var deleteClick: OnDeleteClick? = null

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.name.text = list[position].medicineName
        holder.view.identity.text = list[position].medicineCount
        holder.view.delete.setOnClickListener {
            deleteItem(position)
            deleteClick?.deleteClick(position)
        }
        holder.view.change.setOnClickListener {
            changeClick?.changeClick(position)
        }
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

    fun changeItem(position: Int, model: MedicineAndCount) {
        list[position].medicineName = model.medicineName
        list[position].medicineCount = model.medicineCount
        notifyItemChanged(position)
    }

    fun addItem(model: MedicineAndCount) {
        list.add(model)
        notifyItemInserted(list.size - 1)
        notifyItemRangeInserted(list.size - 1, list.size)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface OnChangeClick {
        fun changeClick(position: Int)
    }

    interface OnDeleteClick {
        fun deleteClick(position: Int)
    }

    fun setOnChangeClick(changeClick: OnChangeClick) {
        this.changeClick = changeClick
    }

    fun setOnDeleteClick(deleteClick: OnDeleteClick) {
        this.deleteClick = deleteClick
    }
}
