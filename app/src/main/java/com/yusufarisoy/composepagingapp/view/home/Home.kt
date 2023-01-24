package com.yusufarisoy.composepagingapp.view.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusufarisoy.composepagingapp.R
import com.yusufarisoy.composepagingapp.data.entity.CharacterUiModel
import com.yusufarisoy.composepagingapp.data.entity.HomeUiModel
import com.yusufarisoy.composepaging.items
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepagingapp.view.common.Error
import com.yusufarisoy.composepagingapp.ui.home.HomeState.Loading
import com.yusufarisoy.composepagingapp.ui.home.HomeState.Content
import com.yusufarisoy.composepagingapp.ui.home.HomeState.Error
import com.yusufarisoy.composepagingapp.ui.home.HomeViewModel
import com.yusufarisoy.composepagingapp.ui.theme.Blue900
import com.yusufarisoy.composepagingapp.ui.theme.White
import com.yusufarisoy.composepagingapp.view.common.Character
import com.yusufarisoy.composepagingapp.view.common.Loading
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.stateFlow.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scaffoldScope = rememberCoroutineScope()
    val context = LocalContext.current
    val requestNextPage = remember {
        {
            viewModel.requestNextPage()
            Toast.makeText(context, "New Page Requested", Toast.LENGTH_LONG).show()
        }
    }
    val onCharacterRemoved = remember<(Int, String) -> Unit> {
        { id, name ->
            viewModel.onCharacterRemoved(id)
            scaffoldScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("$name removed")
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state is Loading && loadState is ComposePagingSource.LoadState.Initial -> Loading()
                state is Error || loadState is ComposePagingSource.LoadState.Error -> Error(
                    message = (state as Error).errorMessage,
                    showRetryButton = true,
                    retry = viewModel::resetPage
                )
                state is Content -> ContentState(
                    uiModel = (state as Content).uiModel,
                    feed = viewModel.feed,
                    requestNextPage = requestNextPage,
                    onFavoriteClicked = viewModel::onFavoriteClicked,
                    onCharacterRemoved = onCharacterRemoved,
                    onSearchClicked = viewModel::onSearchClicked
                )
            }

            // Shows ProgressBar at the bottom only when new page is loading
            if (loadState is ComposePagingSource.LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    color = Blue900
                )
            }
        }
    }
}

@Composable
private fun ContentState(
    uiModel: HomeUiModel,
    feed: ComposePagingData<CharacterUiModel>,
    requestNextPage: () -> Unit,
    onFavoriteClicked: (id: Int, favorite: Boolean) -> Unit,
    onCharacterRemoved: (id: Int, name: String) -> Unit,
    onSearchClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "home_header") {
            Header(onSearchClicked)
        }

        item(key = "home_characters_title") {
            CharactersTitle(uiModel)
        }

        items(
            feed = feed,
            requestNextPage = requestNextPage,
            key = { character -> character.id }
        ) { item ->
            Character(
                character = item,
                onFavoriteClicked = onFavoriteClicked,
                onCharacterRemoved = onCharacterRemoved
            )
        }
    }
}

@Composable
private fun Header(onSearchClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = onSearchClicked,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.search_button)
            )
        }
    }
}

@Composable
private fun CharactersTitle(uiModel: HomeUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = uiModel.titleRes),
            fontWeight = FontWeight.Bold
        )
        Text(text = uiModel.itemCount.toString(), fontWeight = FontWeight.Light)
    }
}
