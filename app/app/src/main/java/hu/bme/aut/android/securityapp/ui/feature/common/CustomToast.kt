package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun MySnackBar(
    screenState: MutableStateFlow<ScreenState>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current

    LaunchedEffect(screenState) {
        screenState.collect{ newState ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = newState.message,
                    actionLabel = context.getString(R.string.composable_ok),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
fun CustomToastDemo() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val screenStates = listOf(
        ScreenState.Loading(),
        ScreenState.Success(),
        ScreenState.Error(message = stringResource(R.string.composable_something_went_wrong)),
        ScreenState.Finished(),
    )

    var index = 0
    val screenState = MutableStateFlow<ScreenState>(screenStates[index])

    Scaffold(
        snackbarHost = {
            //SnackbarHost(hostState = snackbarHostState)
            MySnackBar(screenState = screenState, snackbarHostState = snackbarHostState, scope = scope)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.composable_show_snackbar)) },
                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
                onClick = {
                    index++
                    if(index > screenStates.lastIndex){
                        index = 0
                    }

                    screenState.value = screenStates[index]
                }
            )
        }
    ) { contentPadding ->
        // Screen content
        val padding = contentPadding.calculateTopPadding()
    }
}

@Preview
@Composable
fun CustomToastDemoPreview() {
    CustomToastDemo()
}
