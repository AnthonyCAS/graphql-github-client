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
class RepositoryAdapter(): RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private val repositoryEntries: MutableList<SearchRepositoryQuery.RepositoryEntry>

    init {
        this.repositoryEntries = ArrayList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_repository_item, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun getItemCount() = repositoryEntries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(repositoryEntries[position])
    }

    fun updateData(data: List<SearchRepositoryQuery.RepositoryEntry>, end_cursor: String?) {
        if (end_cursor == null)
            repositoryEntries.clear()
        repositoryEntries.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * it contains:
     *  repository name
     *  repository pull request count
     *  repository description
     */
    class ViewHolder(itemView: View, val context: Context): RecyclerView.ViewHolder(itemView) {
        fun bindItems (item: SearchRepositoryQuery.RepositoryEntry) {
            itemView.repository_name.text = item.node()!!.name()
            itemView.repository_pr_count.text = "${context.resources.getString(R.string.github_repositories_pr_label)} ${item.node()!!.pullRequests().totalCount()}"
            itemView.repository_description.text = "No description."
            if (item.node()!!.description()!= null)
                itemView.repository_description.text = item.node()!!.description()
        }
    }
}