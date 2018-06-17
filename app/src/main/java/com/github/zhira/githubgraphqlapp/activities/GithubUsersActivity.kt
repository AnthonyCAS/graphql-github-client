package com.github.zhira.githubgraphqlapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.github.zhira.githubgraphqlapp.R
import butterknife.OnTextChanged
import com.github.zhira.githubgraphqlapp.adapters.UserAdapter
import com.github.zhira.githubgraphqlapp.models.Item
import java.util.*

class GithubUsersActivity : AppCompatActivity() {

    @BindView(R.id.search_box) lateinit var searchEditText: EditText
    @BindView(R.id.users_list_rv) lateinit var usersRecyclerView: RecyclerView

    var timer = Timer()
    private val delay: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_users)
        ButterKnife.bind(this)
        val items =  getLists()
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.hasFixedSize()
        usersRecyclerView.adapter = UserAdapter(items)
    }

    // callback for searchBox, wait 1 second for a new query
    @OnTextChanged(value = R.id.search_box, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun queryChanged(text: CharSequence) {
        timer.cancel()
        timer = Timer()
        timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        Log.e("Some", text.toString())
                    }
                },
                delay
        )
    }

    fun getLists(): ArrayList<Item> {
        var lists = ArrayList<Item>()
        lists.add(Item("1", "Item 1", "Descripcion 1", "anthony"))
        lists.add(Item("2", "Item 2", "Descripcion 2", "some"))
        lists.add(Item("3", "Item 3", "Descripcion 3", "juan"))
        lists.add(Item("4", "Item 4", "Descripcion 4", "tomas"))
        return lists;
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onBackPressed() {}
}
