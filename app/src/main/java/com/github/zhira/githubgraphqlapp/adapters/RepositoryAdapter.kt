package com.github.zhira.githubgraphqlapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.zhira.githubgraphqlapp.R
import com.github.zhira.githubgraphqlapp.models.Item

class RepositoryAdapter(private val dataSet: ArrayList<Item>): RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSet[position])
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItems (item: Item) {

        }
    }
}