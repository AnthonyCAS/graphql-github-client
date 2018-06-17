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
import android.support.v4.content.ContextCompat
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.github.zhira.githubgraphqlapp.utilities.GraphQlTools
import kotlin.collections.ArrayList


class GithubUsersActivity : AppCompatActivity() {

    @BindView(R.id.search_box) lateinit var searchEditText: EditText
    @BindView(R.id.users_list_rv) lateinit var usersRecyclerView: RecyclerView

    var timer = Timer()
    private val delay: Long = 1000

    private lateinit var client: ApolloClient
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_users)
        ButterKnife.bind(this)
        client = GraphQlTools.setupApollo()
        val items =  getLists()
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.hasFixedSize()
        userAdapter = UserAdapter { item: SearchUserQuery.User -> selectUser(item)}
        usersRecyclerView.adapter = userAdapter
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_divider)!!)
        usersRecyclerView.addItemDecoration(itemDecorator)
    }

    // callback for searchBox, wait 1 second for a new query
    @OnTextChanged(value = R.id.search_box, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun queryChanged(text: CharSequence) {
        timer.cancel()
        timer = Timer()
        timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        Log.e("Query users", text.toString())
                        client.query(SearchUserQuery
                                .builder()
                                .query("AnthonyCAS")
                                .number(50)
                                .build())
                                .enqueue(object : ApolloCall.Callback<SearchUserQuery.Data>() {

                                    override fun onFailure(e: ApolloException) {
                                        Log.e("Error from: ", e.message.toString())
                                    }

                                    override fun onResponse(response: Response<SearchUserQuery.Data>) {
                                        runOnUiThread {
                                          userAdapter.updateData(
                                            response!!.data()!!.userEntry()!!.user() as List<SearchUserQuery.User>
                                          )
                                        }
                                    }

                                })
                    }
                },
                delay
        )
    }

    /**
     * callback from recycler view
     */
    private fun selectUser (item: SearchUserQuery.User) {
        val user = item.node()?.fragments()?.userFragment()
        Toast.makeText(this, "Selected user: ${user?.name()}", Toast.LENGTH_LONG).show()
        val intent = Intent(this@GithubUsersActivity, GithubRepositoriesActivity::class.java)
        intent.putExtra(Constants.LOGIN_USER_CODE, user?.login())
        intent.putExtra(Constants.NAME_USER_CODE, user?.name())
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
