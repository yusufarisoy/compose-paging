package com.yusufarisoy.composepagingapp.ui.search

import com.yusufarisoy.composepagingapp.data.entity.SearchUiModel
import com.yusufarisoy.composepagingapp.util.ViewEvent
import com.yusufarisoy.composepagingapp.util.ViewState

sealed class SearchState : ViewState {

    data class Content(val uiModel: SearchUiModel) : SearchState()

    data class Error(val errorMessage: String) : SearchState()
}

sealed class SearchEvent : ViewEvent
