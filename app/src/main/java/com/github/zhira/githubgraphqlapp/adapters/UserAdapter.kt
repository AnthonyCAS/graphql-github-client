package com.github.zhira.githubgraphqlapp.adapters

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.zhira.githubgraphqlapp.R
import kotlinx.android.synthetic.main.adapter_user_item.view.*

/**
 * User Adapter
 * It helps to map user information into the layout github' users
 */
class UserAdapter(val clickListener: (SearchUserQuery.User) -> Unit): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val userEntries: MutableList<SearchUserQuery.User>

    init {
        this.userEntries = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bindItems(userEntries[position], clickListener)
    }

    fun updateData(data: List<SearchUserQuery.User>, end_cursor: String?) {
        if (end_cursor == null)
            userEntries.clear()
        userEntries.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = userEntries.size

    /**
     * it contains:
     *  name
     *  location
     *  login
     *  picture
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: SearchUserQuery.User, clickListener: (SearchUserQuery.User) -> Unit) {
            val user = item.node()?.fragments()?.userFragment()
            var user_name = user?.name().toString()
            if (user_name == null || TextUtils.isEmpty(user_name))
                user_name = "No name"
            itemView.user_name.text = "${user_name}, "
            itemView.user_location.text = "No location"
            if (user?.location() != null)
                itemView.user_location.text = user?.location()
            itemView.user_login.text = user?.login()
            if (user?.avatarUrl() != null)
                itemView.user_pic.setImageURI(user?.avatarUrl().toString())
            itemView.setOnClickListener { clickListener(item) }
        }
    }
}