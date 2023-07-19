package hu.bme.aut.android.securityapp.ui.feature.wages

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WagesScreen(
    navigateBack: () -> Unit = {},
    viewModel: WagesViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val state = viewModel.screenState.collectAsState().value
    val wages = viewModel.wages.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Wage Categories",
                onNavigate = {
                    navigateBack()
                },
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        if(state is WagesScreenStates.Error){
            Text(text = state.message, modifier = Modifier.padding(top = paddingTop))
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            items(wages){ item ->
                WageListItem(
                    wage = item,
                    onEdit = {
                         Toast.makeText(context, "Edit: ${item}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                            end = 5.dp,
                            bottom = 5.dp,
                            top = if(wages.indexOf(item) == 0) 5.dp else 0.dp
                        )
                )
            }
        }
    }
}
