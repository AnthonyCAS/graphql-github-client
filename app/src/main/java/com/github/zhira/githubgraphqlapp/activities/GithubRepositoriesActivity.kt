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
import java.util.ArrayList

class GithubRepositoriesActivity : AppCompatActivity() {

    @BindView(R.id.repositories_toolbar) lateinit var toolbar: android.support.v7.widget.Toolbar
    @BindView(R.id.repositories_list_rv) lateinit var repositoryRecyclerView: RecyclerView

    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var client: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repositories)
        ButterKnife.bind(this)
        client = GraphQlTools.setupApollo()

        val userLogin = intent.getStringExtra(Constants.LOGIN_USER_CODE)
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
        if (userLogin != null)
            loadUsers(userLogin, 50)
    }

    private fun loadUsers (login: String, limit: Int) {
        client.query(SearchRepositoryQuery
                .builder()
                .login(login)
                .number(limit)
                .build())
                .enqueue(object : ApolloCall.Callback<SearchRepositoryQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.e("Error from: ", e.message.toString())
                    }
                    override fun onResponse(response: Response<SearchRepositoryQuery.Data>) {
                        runOnUiThread {
                            repositoryAdapter.updateData(
                                    response!!.data()!!.user()!!.repository().repositoryEntry() as List<SearchRepositoryQuery.RepositoryEntry>
                            )
                        }
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
