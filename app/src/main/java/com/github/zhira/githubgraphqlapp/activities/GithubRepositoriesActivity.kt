package com.github.zhira.githubgraphqlapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.github.zhira.githubgraphqlapp.R
import com.github.zhira.githubgraphqlapp.adapters.RepositoryAdapter
import com.github.zhira.githubgraphqlapp.models.Item
import com.github.zhira.githubgraphqlapp.utilities.Constants
import com.github.zhira.githubgraphqlapp.utilities.GraphQlTools
import kotlinx.android.synthetic.main.activity_github_repositories.view.*
import kotlinx.android.synthetic.main.adapter_user_item.*
import java.util.ArrayList

class GithubRepositoriesActivity : AppCompatActivity() {

    private var END_CURSOR: String? = null
    private var HAS_NEXT_PAGE: Boolean = false

    @BindView(R.id.repositories_toolbar) lateinit var toolbar: android.support.v7.widget.Toolbar
    @BindView(R.id.repositories_list_rv) lateinit var repositoryRecyclerView: RecyclerView

    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var client: ApolloClient
    private lateinit var userLogin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repositories)
        ButterKnife.bind(this)
        client = GraphQlTools.setupApollo()

        userLogin = intent.getStringExtra(Constants.LOGIN_USER_CODE)
        val userName = intent.getStringExtra(Constants.NAME_USER_CODE)

        toolbar.title = ""
        toolbar.toolbar_title.text = userName
        toolbar.setNavigationIcon(R.drawable.ic_back_button2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repositoryRecyclerView.layoutManager = LinearLayoutManager(this)
        repositoryRecyclerView.hasFixedSize()
        repositoryAdapter = RepositoryAdapter()
        repositoryRecyclerView.adapter = repositoryAdapter
        repositoryRecyclerView.addItemDecoration(DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL))
        setRecyclerViewScrollListener()
        if (userLogin != null)
            loadRepositories()
    }

    private fun loadRepositories () {
        client.query(SearchRepositoryQuery
                .builder()
                .login(userLogin)
                .number(Constants.QUERY_LIMIT)
                .after(END_CURSOR)
                .build())
                .enqueue(object : ApolloCall.Callback<SearchRepositoryQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.e("Error from: ", e.message.toString())
                    }
                    override fun onResponse(response: Response<SearchRepositoryQuery.Data>) {
                        runOnUiThread {
                            repositoryAdapter.updateData(
                                response!!.data()!!.user()!!.repository().repositoryEntry() as List<SearchRepositoryQuery.RepositoryEntry>,
                                END_CURSOR
                            )
                            END_CURSOR = response!!.data()!!.user()!!.repository()!!.pageInfo()!!.endCursor().toString()
                            HAS_NEXT_PAGE = response!!.data()!!.user()!!.repository()!!.pageInfo()!!.hasNextPage()
                        }
                    }
                })
    }

    /**
     * Scroll Listener to get new data
     */
    private fun setRecyclerViewScrollListener () {
        repositoryRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!! && HAS_NEXT_PAGE && userLogin != null) {
                    loadRepositories()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
