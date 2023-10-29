package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    onNavigate: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    screenState: State<ScreenState> = mutableStateOf(ScreenState.Finished()),
    modifier: Modifier = Modifier
){
    var showLoadingBar by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(screenState.value){
        if(screenState.value is ScreenState.Loading){
            showLoadingBar = true
            coroutineScope.launch {
                delay(800)
                showLoadingBar = false
            }
        }
    }

    Surface(
        shadowElevation = 10.dp,
        tonalElevation = 5.dp
    ) {
        Column {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (onNavigate != null) {
                        IconButton(onClick = onNavigate) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.composable_navigate_back)
                            )
                        }
                    }
                },
                actions = actions,
                colors = TopAppBarDefaults.mediumTopAppBarColors(),
                modifier = modifier
            )

            if(showLoadingBar) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
fun MyTopAppBarPreview() {
    val state = remember {
        mutableStateOf(ScreenState.Loading())
    }

    MyTopAppBar(
        title = "Detail View Model Nagyon Hosszu",
        onNavigate = {},
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "asd")
            }
        },
        screenState = state
    )
}