package com.yusufarisoy.composepaging.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.yusufarisoy.composepaging.ComposePager
import com.yusufarisoy.composepaging.R
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.data.entity.HomeUiModel
import com.yusufarisoy.composepaging.domain.FetchCharactersUseCase
import com.yusufarisoy.composepaging.domain.HomePagingSource
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.ui.home.HomeState.Content
import com.yusufarisoy.composepaging.ui.home.HomeState.Error
import com.yusufarisoy.composepaging.ui.home.HomeState.Loading
import com.yusufarisoy.composepaging.util.BaseViewModel
import com.yusufarisoy.composepaging.util.secureLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: FetchCharactersUseCase
) : BaseViewModel<HomeState, HomeEvent>(Loading) {

    val feed: ComposePagingData<CharacterUiModel> = ComposePagingData(prefetchDistance = PREFETCH_DISTANCE)
    private val pager: ComposePager<CharacterUiModel> by lazy {
        ComposePager(
            pagingSource = HomePagingSource(
                useCase = useCase,
                onPageLoaded = ::onPageLoaded
            ),
            initialPage = INITIAL_PAGE
        )
    }
    private val pagerExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HomeViewModel", "Pager Error: $throwable")
        onError(throwable)
    }

    val loadState: StateFlow<ComposePagingSource.LoadState>
        get() = pager.loadState

    init {
        loadFirstPage()
        observePagerData()
    }

    private fun loadFirstPage() {
        secureLaunch(dispatcher = Dispatchers.IO, exceptionHandler = pagerExceptionHandler) {
            pager.loadFirstPage()
        }
    }

    private fun observePagerData() {
        secureLaunch(Dispatchers.IO, pagerExceptionHandler) {
            pager.dataFlow
                .onEach { page ->
                    feed.addPage(page)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun onPageLoaded(itemCount: Int) {
        setState {
            Content(HomeUiModel(titleRes = R.string.home_title, itemCount = itemCount))
        }
    }

    fun requestNextPage() {
        secureLaunch(Dispatchers.IO, pagerExceptionHandler) {
            pager.loadNextPage()
        }
    }

    fun resetPage() {
        secureLaunch(Dispatchers.IO, pagerExceptionHandler) {
            pager.reset()
        }
    }

    fun onFavoriteClicked(id: Int, favorite: Boolean) {
        val index = feed.items.indexOfFirst { character -> character.id == id }
        if (index != -1) {
            feed.items[index] = feed.items[index].copy(favorite = !favorite)
        }
    }

    fun onCharacterRemoved(id: Int) {
        val index = feed.items.indexOfFirst { character -> character.id == id }
        if (index != -1) {
            feed.items.removeAt(index)
        }
    }

    fun onSearchClicked() {
        pushEvent(HomeEvent.OpenSearch)
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        setState {
            Error(errorMessage = "Unexpected error occurred")
        }
    }

    companion object {
        private const val PREFETCH_DISTANCE = 2
        private const val INITIAL_PAGE = 1
    }
}
