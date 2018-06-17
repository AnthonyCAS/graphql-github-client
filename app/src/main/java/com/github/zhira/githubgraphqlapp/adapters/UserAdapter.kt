package com.github.zhira.githubgraphqlapp.adapters

import android.app.FragmentBreadCrumbs
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.zhira.githubgraphqlapp.R
import com.github.zhira.githubgraphqlapp.models.Item
import kotlinx.android.synthetic.main.adapter_user_item.view.*

/**
 * User Adapter
 * It helps to map user information into the layout github' users
 */
class UserAdapter(private val dataset: ArrayList<Item>, val clickListener: (Item) -> Unit): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bindItems(dataset[position], clickListener)
    }

    override fun getItemCount() = dataset.size

    /**
     * it contains:
     *  name
     *  location
     *  login
     *  picture
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Item, clickListener: (Item) -> Unit) {
            itemView.user_name.text = item.name
            itemView.user_location.text = item.location
            itemView.user_login.text = item.login
            if (item.picture_url != null)
                itemView.user_pic.setImageURI("https://avatars3.githubusercontent.com/u/5943602?s=400&u=bfe341bbda63034ef33408fbec7fc5f9c0a2d3d5&v=4")
            itemView.setOnClickListener { clickListener(item) }
        }
    }
}