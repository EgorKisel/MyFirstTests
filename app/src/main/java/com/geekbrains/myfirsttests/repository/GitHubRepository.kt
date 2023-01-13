package com.geekbrains.myfirsttests.repository

import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GitHubRepository(private val gitHubApi: GitHubApi) : RepositoryContract {

    override fun searchGithub(
        query: String,
        callBack: RepositoryCallback
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

    override fun searchGithub(query: String): Observable<SearchResponse> {
        return gitHubApi.searchGithubRx(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}