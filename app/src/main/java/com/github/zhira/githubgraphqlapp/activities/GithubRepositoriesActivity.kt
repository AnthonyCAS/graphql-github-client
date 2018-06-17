package com.github.zhira.githubgraphqlapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.github.zhira.githubgraphqlapp.R
import com.github.zhira.githubgraphqlapp.adapters.RepositoryAdapter
import com.github.zhira.githubgraphqlapp.models.Item
import com.github.zhira.githubgraphqlapp.utilities.Constants
import java.util.ArrayList

class GithubRepositoriesActivity : AppCompatActivity() {

    @BindView(R.id.repositories_toolbar) lateinit var toolbar: android.support.v7.widget.Toolbar
    @BindView(R.id.repositories_list_rv) lateinit var repositoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repositories)
        ButterKnife.bind(this)
        val userLogin = intent.getStringExtra(Constants.LOGIN_USER_CODE)
        val userName = intent.getStringExtra(Constants.NAME_USER_CODE)
        toolbar.title = userName
        val items =  getLists()
        repositoryRecyclerView.layoutManager = LinearLayoutManager(this)
        repositoryRecyclerView.hasFixedSize()
        repositoryRecyclerView.adapter = RepositoryAdapter(items)
        repositoryRecyclerView.addItemDecoration(DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL))
    }

    fun getLists(): ArrayList<Item> {
        var lists = ArrayList<Item>()
        lists.add(Item("1", "Item 1", "Descripcion 1", "anthony"))
        lists.add(Item("2", "Item 2", "Descripcion 2", "some"))
        lists.add(Item("3", "Item 3", "Descripcion 3", "juan"))
        lists.add(Item("4", "Item 4", "Descripcion 4", "tomas"))
        return lists;
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
