package com.geekbrains.myfirsttests.presenter.search

import android.view.View
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.presenter.SchedulerProvider
import com.geekbrains.myfirsttests.repository.RepositoryCallback
import com.geekbrains.myfirsttests.view.search.ViewSearchContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: RepositoryContract,
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : PresenterSearchContract, RepositoryCallback {

    private var view: View? = null

    override fun searchGitHub(searchQuery: String) {
        //Dispose
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe { viewContract.displayLoading(true) }
                .doOnTerminate { viewContract.displayLoading(false) }
                .subscribeWith(object : DisposableObserver<SearchResponse>() {
                    override fun onNext(searchResponse: SearchResponse) {
                        val searchResults = searchResponse.searchResults
                        val totalCount = searchResponse.totalCount
                        if (searchResults != null && totalCount != null) {
                            viewContract.displaySearchResults(
                                searchResults,
                                totalCount
                            )
                        } else {
                            viewContract.displayError("Search results or total count are null")
                        }
                    }
                    override fun onError(e: Throwable) {
                        viewContract.displayError(e.message ?: "Response is null or unsuccessful")
                    }
                    override fun onComplete() {}
                }
                )
        )
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