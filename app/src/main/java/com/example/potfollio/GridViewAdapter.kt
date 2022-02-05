package com.example.potfollio

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import kotlinx.android.synthetic.main.custom_list_item.view.*


class GridViewAdapter(private val items: MutableList<GridViewItem>): BaseAdapter()  {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): GridViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        var convertView = view

        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(
            R.layout.custom_list_item,
            parent,
            false
        )

        val item: GridViewItem = items[position]
        // val image_title = convertView!!.findViewById<View>(R.id.itemimgview) as ImageView
        convertView!!.itemimgview.setImageBitmap(item.image)
        return convertView
    }
}