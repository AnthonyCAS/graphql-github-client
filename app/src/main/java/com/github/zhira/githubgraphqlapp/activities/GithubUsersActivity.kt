package com.github.zhira.githubgraphqlapp.activities

import android.content.Intent
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
import com.github.zhira.githubgraphqlapp.utilities.Constants
import java.util.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v4.content.ContextCompat
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.github.zhira.githubgraphqlapp.utilities.GraphQlTools

class GithubUsersActivity : AppCompatActivity() {

    private lateinit var END_CURSOR: String
    private var HAS_NEXT_PAGE: Boolean = false
    
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
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.hasFixedSize()
        userAdapter = UserAdapter { item: SearchUserQuery.User -> selectUser(item)}
        usersRecyclerView.adapter = userAdapter
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_divider)!!)
        usersRecyclerView.addItemDecoration(itemDecorator)
        setRecyclerViewScrollListener()
        loadUsers("a", 10)
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
                    loadUsers(text.toString(), 50)
                }
            }, delay
        )
    }

    private fun loadUsers (query: String, limit: Int) {
        client.query(SearchUserQuery
            .builder()
            .query(query)
            .number(limit)
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

    /**
     * callback from recycler view
     */
    private fun selectUser (item: SearchUserQuery.User) {
        val user = item.node()?.fragments()?.userFragment()
        val intent = Intent(this@GithubUsersActivity, GithubRepositoriesActivity::class.java)
        intent.putExtra(Constants.LOGIN_USER_CODE, user?.login())
        intent.putExtra(Constants.NAME_USER_CODE, user?.name())
        startActivity(intent)
    }

    private fun setRecyclerViewScrollListener () {
        usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!!) {
                    Log.e("REACH", "END");
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onBackPressed() {}
}
