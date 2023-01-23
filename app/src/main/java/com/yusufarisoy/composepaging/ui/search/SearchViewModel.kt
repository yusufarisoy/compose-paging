package com.yusufarisoy.composepaging.ui.search

import android.util.Log
import com.yusufarisoy.composepaging.ComposePagerWithQuery
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.domain.FetchCharactersUseCase
import com.yusufarisoy.composepaging.domain.SearchPagingSource
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData
import com.yusufarisoy.composepaging.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: FetchCharactersUseCase
) : BaseViewModel<SearchState, SearchEvent>(SearchState.Loading) {

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

    init {
        // TODO: Finish SearchPage with ComposePagerWithQuery
    }

    companion object {
        private const val PREFETCH_DISTANCE = 0
    }
}
