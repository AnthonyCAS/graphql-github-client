package com.github.zhira.githubgraphqlapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.zhira.githubgraphqlapp.R

class GithubRepositoriesActivity : AppCompatActivity() {

    @BindView(R.id.repositories_toolbar) lateinit var toolbar: android.support.v7.widget.Toolbar
    @BindView(R.id.repositories_list_rv) lateinit var repositoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repositories)
        ButterKnife.bind(this)
        toolbar.title = "User"
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
