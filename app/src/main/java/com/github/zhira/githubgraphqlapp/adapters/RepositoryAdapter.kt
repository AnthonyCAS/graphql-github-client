package com.github.zhira.githubgraphqlapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.zhira.githubgraphqlapp.R
import com.github.zhira.githubgraphqlapp.models.Item
import kotlinx.android.synthetic.main.adapter_repository_item.view.*

/**
 * Repository Adapter
 * It helps to map data into the layout all repositories for the current selected user
 */
class RepositoryAdapter(private val dataSet: ArrayList<Item>): RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_repository_item, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataSet[position])
    }

    /**
     * it contains:
     *  repository name
     *  repository pull request count
     *  repository description
     */
    class ViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView) {
        fun bindItems (item: Item) {
            itemView.repository_name.text = item.login
            itemView.repository_pr_count.text = "${context.resources.getString(R.string.github_repositories_pr_label)} ${item.location}"
            itemView.repository_description.text = item.picture_url
        }
    }
}