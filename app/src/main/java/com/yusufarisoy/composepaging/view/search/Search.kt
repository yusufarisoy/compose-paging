package com.yusufarisoy.composepaging.view.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusufarisoy.composepaging.R
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.data.entity.SearchUiModel
import com.yusufarisoy.composepaging.items
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.ui.search.SearchState.Content
import com.yusufarisoy.composepaging.ui.search.SearchState.Error
import com.yusufarisoy.composepaging.ui.search.SearchViewModel
import com.yusufarisoy.composepaging.ui.theme.Blue900
import com.yusufarisoy.composepaging.ui.theme.Gray900
import com.yusufarisoy.composepaging.ui.theme.White
import com.yusufarisoy.composepaging.view.common.Character
import com.yusufarisoy.composepaging.view.common.Error

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state by viewModel.stateFlow.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val searchQuery by viewModel.queryFlow.collectAsState()
    val context = LocalContext.current
    val requestNextPage = remember {
        {
            viewModel.requestNextPage()
            Toast.makeText(context, "New Page Requested", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state is Error || loadState is ComposePagingSource.LoadState.Error -> Error(
                    message = (state as Error).errorMessage
                )
                state is Content -> ContentState(
                    uiModel = (state as Content).uiModel,
                    feed = viewModel.feed,
                    searchQuery = { searchQuery },
                    onQueryChanged = viewModel::onQueryChanged,
                    requestNextPage = requestNextPage,
                    onFavoriteClicked = viewModel::onFavoriteClicked,
                    onCharacterRemoved = viewModel::onCharacterRemoved
                )
            }

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
    uiModel: SearchUiModel,
    feed: ComposePagingData<CharacterUiModel>,
    modifier: Modifier = Modifier,
    searchQuery: () -> String,
    onQueryChanged: (String) -> Unit,
    requestNextPage: () -> Unit,
    onFavoriteClicked: (id: Int, favorite: Boolean) -> Unit,
    onCharacterRemoved: (id: Int, name: String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "home_header") {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        item(key = "search_bar") {
            SearchBar(
                query = searchQuery,
                onQueryChanged = onQueryChanged
            )
        }

        if (feed.items.size == 0) {
            item(key = "search_empty_placeholder") {
                PlaceHolder()
            }
        }

        if (uiModel.itemCount != null) {
            item(key = "search_title") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.search_title, uiModel.itemCount),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = uiModel.itemCount.toString(), fontWeight = FontWeight.Light)
                }
            }
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
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: () -> String,
    onQueryChanged: (String) -> Unit
) {
    TextField(
        value = query(),
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Gray900, shape = RoundedCornerShape(16.dp)),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.search_button)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            leadingIconColor = Blue900,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun PlaceHolder() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = false)
                .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.search_placeholder),
                contentDescription = stringResource(R.string.search_button)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = stringResource(R.string.search_placeholder), color = Gray900)
    }
}
