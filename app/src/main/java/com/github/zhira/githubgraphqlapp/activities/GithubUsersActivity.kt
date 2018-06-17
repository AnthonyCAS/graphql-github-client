package com.github.zhira.githubgraphqlapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.github.zhira.githubgraphqlapp.R
import butterknife.OnTextChanged
import com.github.zhira.githubgraphqlapp.adapters.UserAdapter
import com.github.zhira.githubgraphqlapp.models.Item
import com.github.zhira.githubgraphqlapp.utilities.Constants
import java.util.*
import android.support.v7.widget.DividerItemDecoration



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
        usersRecyclerView.adapter = UserAdapter(items, { item: Item -> selectUser(item)})
        usersRecyclerView.addItemDecoration(DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL))
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

    private fun selectUser (item: Item) {
        Toast.makeText(this, "Selected user: ${item.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this@GithubUsersActivity, GithubRepositoriesActivity::class.java)
        intent.putExtra(Constants.LOGIN_USER_CODE, item.login)
        intent.putExtra(Constants.NAME_USER_CODE, item.name)
        startActivity(intent)
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
