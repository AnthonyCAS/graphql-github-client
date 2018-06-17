package com.github.zhira.githubgraphqlapp.utilities

import com.apollographql.apollo.ApolloClient
import com.github.zhira.githubgraphqlapp.BuildConfig
import okhttp3.OkHttpClient

abstract class GraphQlTools {
    companion object {
        @JvmStatic
        fun setupApollo(): ApolloClient {
            val okHttp = OkHttpClient
                    .Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val builder = original.newBuilder().method(original.method(),
                                original.body())
                        builder.addHeader("Authorization"
                                , "Bearer " + BuildConfig.AUTH_TOKEN)
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