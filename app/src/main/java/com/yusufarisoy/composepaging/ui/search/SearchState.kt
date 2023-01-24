package com.yusufarisoy.composepaging.ui.search

import com.yusufarisoy.composepaging.data.entity.SearchUiModel
import com.yusufarisoy.composepaging.util.ViewEvent
import com.yusufarisoy.composepaging.util.ViewState

sealed class SearchState : ViewState {

    data class Content(val uiModel: SearchUiModel) : SearchState()

    data class Error(val errorMessage: String) : SearchState()
}

sealed class SearchEvent : ViewEvent
