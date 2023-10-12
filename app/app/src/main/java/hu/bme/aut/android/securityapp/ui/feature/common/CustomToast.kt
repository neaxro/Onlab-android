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
import androidx.compose.ui.tooling.preview.Preview
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
    LaunchedEffect(screenState) {
        screenState.collect{ newState ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = newState.message,
                    actionLabel = "Ok",
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
        ScreenState.Error("Something went wrong..."),
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
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
                onClick = {
                    index++
                    if(index > screenStates.lastIndex){
                        index = 0
                    }

                    screenState.value = screenStates[index]

                    /*scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = screenState.value.message,
                                actionLabel = "Ok",
                                duration = SnackbarDuration.Short
                            )
                    }*/
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
