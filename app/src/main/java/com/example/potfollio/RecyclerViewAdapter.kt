package com.example.potfollio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_recycler_item.view.*

class RecyclerViewAdapter(private val items: ArrayList<RecyclerViewItem>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        val listener = View.OnClickListener {
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.custom_recycler_item, parent, false)
        return ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v
        fun bind(listener: View.OnClickListener, item: RecyclerViewItem) {
            view.imageview.setImageBitmap(item.image)
            view.setOnClickListener(listener)
        }
    }
}
