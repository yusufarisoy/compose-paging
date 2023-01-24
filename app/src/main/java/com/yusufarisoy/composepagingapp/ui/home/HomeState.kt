package com.yusufarisoy.composepagingapp.ui.home

import com.yusufarisoy.composepagingapp.data.entity.HomeUiModel
import com.yusufarisoy.composepagingapp.util.ViewEvent
import com.yusufarisoy.composepagingapp.util.ViewState

sealed class HomeState : ViewState {

    object Loading : HomeState()

    data class Content(val uiModel: HomeUiModel) : HomeState()

    data class Error(val errorMessage: String) : HomeState()
}

sealed class HomeEvent : ViewEvent {

    object OpenSearch : HomeEvent()
}
