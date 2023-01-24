package com.yusufarisoy.composepaging.view.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.yusufarisoy.composepaging.R
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.ui.theme.Gray900
import com.yusufarisoy.composepaging.ui.theme.Green
import com.yusufarisoy.composepaging.ui.theme.Red700
import com.yusufarisoy.composepaging.ui.theme.Red900
import com.yusufarisoy.composepaging.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Character(
    character: CharacterUiModel,
    modifier: Modifier = Modifier,
    onFavoriteClicked: (id: Int, favorite: Boolean) -> Unit,
    onCharacterRemoved: (id: Int, name: String) -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = { state ->
            if (state == DismissValue.DismissedToStart) {
                onCharacterRemoved(character.id, character.name)
            }
            true
        }
    )
    val dismissScope = rememberCoroutineScope()
    val dismissColor by animateColorAsState(
        if (dismissState.targetValue == DismissValue.Default) Gray900 else Red700
    )
    val dismissScale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 1f else 1.2f
    )
    val (favoriteIcon, favoriteColor) = remember(character) {
        if (character.favorite) {
            R.drawable.ic_favorite_fill to Red900
        } else {
            R.drawable.ic_favorite_outline to Gray900
        }
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.3f) },
        background = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(dismissColor)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.swipe_to_delete),
                    modifier = Modifier.scale(dismissScale)
                )
            }
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(White)
        ) {
            CharacterImage(character)

            Column(
                modifier = Modifier
                    .weight(weight = 1f, fill = false)
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            ) {
                Text(text = character.name, fontWeight = FontWeight.Bold)
                CharacterStatus(status = character.status)
                Text(text = character.gender)
                Text(text = character.location)
            }

            Column(
                modifier = Modifier.height(120.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onFavoriteClicked(character.id, character.favorite) }) {
                    Icon(
                        painter = painterResource(favoriteIcon),
                        contentDescription = stringResource(R.string.favorite_status),
                        tint = favoriteColor
                    )
                }

                IconButton(
                    onClick = {
                        dismissScope.launch {
                            dismissState.dismiss(DismissDirection.EndToStart)
                            onCharacterRemoved(character.id, character.name)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_swipe_to_delete),
                        contentDescription = stringResource(R.string.swipe_to_delete)
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterImage(character: CharacterUiModel) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp), clip = false)
            .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = character.species,
            modifier = Modifier
                .widthIn(min = 20.dp, max = 80.dp)
                .offset(x = (-4).dp, y = 4.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp), clip = false)
                .background(color = White, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .align(Alignment.TopEnd),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}

@Composable
private fun CharacterStatus(status: String) {
    val color = remember {
        when(status) {
            "Alive" -> Green
            "Dead" -> Red900
            else -> Gray900
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_circle),
            contentDescription = status,
            tint = color
        )
        Text(text = status, color = color)
    }
}
