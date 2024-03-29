package com.geekbrains.myfirsttests

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.search.ScheduleProviderStub
import com.geekbrains.myfirsttests.repository.FakeGitHubRepository
import com.geekbrains.myfirsttests.view.search.ScreenState
import com.geekbrains.myfirsttests.view.search.SearchViewModel
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import androidx.lifecycle.Observer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var repository: FakeGitHubRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(repository)
    }

//    @Test
//    fun search_Test() {
//        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
//            Observable.just(
//                SearchResponse(1, listOf())
//            )
//        )
//        searchViewModel.searchGitHub(SEARCH_QUERY)
//        verify(repository, times(1)).searchGithub(SEARCH_QUERY)
//    }

//    @Test
//    fun liveData_TestReturnValueIsNotNull() {
//        val observer = Observer<ScreenState> {}
//        val liveData = searchViewModel.subscribeToLiveData()
//
//        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
//            Observable.just(
//                SearchResponse(1, listOf())
//            )
//        )
//        try {
//            liveData.observeForever(observer)
//            searchViewModel.searchGitHub(SEARCH_QUERY)
//            Assert.assertNotNull(liveData.value)
//        } finally {
//            liveData.removeObserver(observer)
//        }
//    }

//    @Test
//    fun liveData_TestReturnValueIsError() {
//        val observer = Observer<ScreenState> {}
//        val liveData = searchViewModel.subscribeToLiveData()
//        val error = Throwable(ERROR_TEXT)
//
//        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
//            Observable.error(error)
//        )
//
//        try {
//            liveData.observeForever(observer)
//            searchViewModel.searchGitHub(SEARCH_QUERY)
//            val value: ScreenState.Error = liveData.value as ScreenState.Error
//            Assert.assertEquals(value.error.message, error.message)
//        } finally {
//            liveData.removeObserver(observer)
//        }
//    }

    @Test
    fun coroutines_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(1, listOf())
            )
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(null, listOf())
            )
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, ERROR_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
        private const val EXCEPTION_TEXT = "Response is null or unsuccessful"
    }
}