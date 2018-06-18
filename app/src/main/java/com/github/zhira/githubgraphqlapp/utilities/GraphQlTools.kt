package com.github.zhira.githubgraphqlapp.utilities

import android.app.Application
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.github.zhira.githubgraphqlapp.R
import okhttp3.OkHttpClient

abstract class GraphQlTools: Application() {
    companion object {
        @JvmStatic
        fun setupApollo(context: Context): ApolloClient {
            val okHttp = OkHttpClient
                    .Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val builder = original.newBuilder().method(original.method(),
                                original.body())
                        builder.addHeader("Authorization"
                                , "Bearer " + context.getString(R.string.github_apikey))
                        chain.proceed(builder.build())
                    }
                    .build()
            return ApolloClient.builder()
                    .serverUrl(Constants.BASE_URL)
                    .okHttpClient(okHttp)
                    .build()
        }
    }
}