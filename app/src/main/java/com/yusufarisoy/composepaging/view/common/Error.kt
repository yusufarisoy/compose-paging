package com.yusufarisoy.composepaging.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.yusufarisoy.composepaging.R

@Composable
fun Error(
    message: String,
    modifier: Modifier = Modifier,
    showRetryButton: Boolean = false,
    retry: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.error_state_message)
        )
        Text(text = message)

        if (showRetryButton) {
            Button(onClick =  retry) {
                Text(text = stringResource(R.string.retry_button))
            }
        }
    }
}
