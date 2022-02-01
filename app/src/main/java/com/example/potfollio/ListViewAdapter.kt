package com.example.potfollio

import android.media.Image
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import kotlinx.android.synthetic.main.custom_list_item.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import kotlinx.android.synthetic.main.custom_list_item.view.*


class ListViewAdapter(private val items: MutableList<ListViewItem>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): ListViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.custom_list_item, parent, false)

        //val p = 0
        //if(p<position) {
            val item: ListViewItem = items[position]
            val image_title = convertView!!.findViewById<View>(R.id.image_title) as ImageView
            convertView!!.image_title.setImageBitmap(item.icon)
       // }
            return convertView
    }
}



