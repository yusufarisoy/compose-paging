package com.yusufarisoy.composepaging.ui.home

import com.yusufarisoy.composepaging.data.entity.HomeUiModel
import com.yusufarisoy.composepaging.util.ViewEvent
import com.yusufarisoy.composepaging.util.ViewState

sealed class HomeState : ViewState {

    object Loading : HomeState()

    data class Content(val uiModel: HomeUiModel) : HomeState()

    data class Error(val errorMessage: String) : HomeState()
}

sealed class HomeEvent : ViewEvent {

    object OpenSearch : HomeEvent()
}
