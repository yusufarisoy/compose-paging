package com.yusufarisoy.composepaging.ui.search

import com.yusufarisoy.composepaging.util.ViewEvent
import com.yusufarisoy.composepaging.util.ViewState

sealed class SearchState : ViewState {

    object Loading : SearchState()

    data class Content(val uiModel: String) : SearchState()

    data class Error(val errorMessage: String) : SearchState()
}

sealed class SearchEvent : ViewEvent {

    object GoBack : SearchEvent()
}
