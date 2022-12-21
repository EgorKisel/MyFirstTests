package com.geekbrains.myfirsttests.repository

import com.geekbrains.myfirsttests.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GitHubRepository(private val gitHubApi: GitHubApi) {

    fun searchGithub(
        query: String,
        callBack: GitHubRepositoryCallback
    ) {
        val call = gitHubApi.searchGithub(query)
        call?.enqueue(object : Callback<SearchResponse?> {
            override fun onResponse(
                call: Call<SearchResponse?>,
                response: Response<SearchResponse?>
            ) {
                callBack.handleGitHubResponse(response)
            }

            override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                callBack.handleGitHubError()
            }

        })
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<SearchResponse?>?)
        fun handleGitHubError()
    }
}