package com.geekbrains.myfirsttests.presenter.search

import android.view.View
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.repository.GitHubRepository
import com.geekbrains.myfirsttests.view.search.ViewSearchContract
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepository.GitHubRepositoryCallback {

    private var view: View? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(view: View) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount =searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Response is null or unsuccessful")
            }
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}