package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun MySnackbarHost(
    screenState: StateFlow<ScreenState>,
    actionPerformed: () -> Unit = {},
    dismissed: () -> Unit = {},
){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(screenState){
        screenState.collect{ newState ->
            if(newState !is ScreenState.Loading && newState.show) {

                scope.launch {
                    val actionResult = snackbarHostState
                        .showSnackbar(
                            message = newState.message,
                            actionLabel = "Ok",
                            duration = SnackbarDuration.Short
                        )

                    when (actionResult) {
                        SnackbarResult.ActionPerformed -> {
                            actionPerformed()
                        }

                        SnackbarResult.Dismissed -> {
                            dismissed()
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySnackbarHostPreview(){
    val screenState = MutableStateFlow<ScreenState>(ScreenState.Loading())
    val states = listOf(
        ScreenState.Loading(),
        ScreenState.Error(message = "Error"),
        ScreenState.Success(),
        ScreenState.Finished()
    )
    var index = 0

    Scaffold(
        snackbarHost = {
            MySnackbarHost(
                screenState = screenState,
            )
        },
        topBar = {
            MyTopAppBar(title = "Snackbar Host")
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
                onClick = {
                    index += 1
                    if(index >= states.size){
                        index = 0
                    }

                    screenState.value = states[index]
                }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()
    }
}