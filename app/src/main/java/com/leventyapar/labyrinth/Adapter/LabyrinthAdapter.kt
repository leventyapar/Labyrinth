package com.leventyapar.labyrinth.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.leventyapar.labyrinth.Model.LabyrinthItem
import com.leventyapar.labyrinth.R

class LabyrinthAdapter : BaseAdapter {

    private var labyrinthList = ArrayList<LabyrinthItem>()
    private var context: Context? = null

    constructor(context: Context, labyrinthList : ArrayList<LabyrinthItem>): super(){
        this.context = context
        this.labyrinthList = labyrinthList
    }

    override fun getCount(): Int {
        return labyrinthList.size
    }

    override fun getItem(position: Int): Any {
        return labyrinthList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mazeItem = this.labyrinthList[position]

        var inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val labyrinthItemView = inflater.inflate(R.layout.labyrinth_item,parent,false)
        labyrinthItemView.findViewById<ImageView>(R.id.mazeItem_iv).setImageResource(mazeItem.image)
        return labyrinthItemView
    }
}