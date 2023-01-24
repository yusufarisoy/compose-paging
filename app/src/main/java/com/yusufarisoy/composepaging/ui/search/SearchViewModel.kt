package com.yusufarisoy.composepaging.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.yusufarisoy.composepaging.ComposePagerWithQuery
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.data.entity.SearchUiModel
import com.yusufarisoy.composepaging.domain.FetchCharactersUseCase
import com.yusufarisoy.composepaging.domain.SearchPagingSource
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.util.BaseViewModel
import com.yusufarisoy.composepaging.util.secureLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: FetchCharactersUseCase
) : BaseViewModel<SearchState, SearchEvent>(SearchState.Content(SearchUiModel())) {

    val feed: ComposePagingData<CharacterUiModel> = ComposePagingData(prefetchDistance = PREFETCH_DISTANCE)
    private val pager: ComposePagerWithQuery<SearchPagingSource.Params, CharacterUiModel> by lazy {
        ComposePagerWithQuery(
            pagingSource = SearchPagingSource(
                useCase = useCase
            )
        )
    }
    private val pagerExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("SearchViewModel", "Pager Error: $throwable")
    }

    private var page: Int = INITIAL_PAGE
    private val _queryFlow: MutableStateFlow<String> = MutableStateFlow(EMPTY_STRING)
    val queryFlow: StateFlow<String> = _queryFlow

    val loadState: StateFlow<ComposePagingSource.LoadState>
        get() = pager.loadState

    init {
        observeQuery()
        observePagerData()
    }

    fun onQueryChanged(query: String) {
        secureLaunch {
            _queryFlow.emit(query)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        secureLaunch(Dispatchers.IO) {
            _queryFlow
                .debounce(DEBOUNCE)
                .distinctUntilChanged()
                .onEach {
                    if (it.length > 2) {
                        search(it)
                    } else {
                        clearPage()
                    }
                }
                .launchIn(viewModelScope)
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

    private fun search(query: String) {
        page = INITIAL_PAGE
        feed.clear()
        secureLaunch(Dispatchers.IO, pagerExceptionHandler) {
            pager.searchPage(SearchPagingSource.Params(page, query))
        }
    }

    fun requestNextPage() {
        page++
        secureLaunch(Dispatchers.IO, pagerExceptionHandler) {
            pager.loadNextPage(SearchPagingSource.Params(page, _queryFlow.value))
        }
    }

    private fun clearPage() {
        if (feed.items.isNotEmpty()) {
            page = 0
            feed.clear()
        }
    }

    fun onFavoriteClicked(id: Int, favorite: Boolean) {
        val index = feed.items.indexOfFirst { character -> character.id == id }
        if (index != -1) {
            feed.items[index] = feed.items[index].copy(favorite = !favorite)
        }
    }

    fun onCharacterRemoved(id: Int, name: String) {
        val index = feed.items.indexOfFirst { character -> character.id == id }
        if (index != -1) {
            feed.items.removeAt(index)
        }
        Log.i("SearchViewModel", "$name removed")
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        setState {
            SearchState.Error(errorMessage = "Unexpected error occurred")
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PREFETCH_DISTANCE = 0
        private const val DEBOUNCE = 300L
        private const val EMPTY_STRING = ""
    }
}
