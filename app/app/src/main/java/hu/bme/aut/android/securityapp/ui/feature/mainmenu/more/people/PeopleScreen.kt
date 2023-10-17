package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import hu.bme.aut.android.securityapp.ui.feature.common.MySearchBar
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.PeopleOnJobCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    viewModel: PeopleScreenViewModel = hiltViewModel()
){
    val state = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var searchbarVisible by remember { mutableStateOf(false) }

    val screenState = viewModel.screenState.collectAsState().value
    val people = viewModel.people.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "People On Job",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(
                        onClick = {
                            searchbarVisible = !searchbarVisible
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Show Searchbar"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.evoke(PeopleAction.Refresh)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh data"
                        )
                    }
                },
                screenState = screenState
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop),
        ) {

            AnimatedVisibility(visible = searchbarVisible) {
                MySearchBar(
                    onSearch = { text ->
                        searchbarVisible = false
                        val indexOfItem = people.indexOfFirst { p ->
                            p.basicInfo.nickname.lowercase().contains(text.lowercase())
                        }

                        if(indexOfItem >= 0) {
                            coroutineScope.launch {
                                state.animateScrollToItem(indexOfItem)
                            }
                        }
                    }
                )
            }

            if(screenState is ScreenState.Error){
                Text(text = screenState.message)
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(5.dp),
                state = state
            ){
                items(people.sortedBy { person ->
                    person.basicInfo.fullName
                }){ item ->
                    PeopleOnJobCard(
                        person = item,
                        onClick = { clickedPerson ->
                            navigateToDetail(clickedPerson.basicInfo.id)
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PeopleScreenPreview(){
    PeopleScreen(
        navigateBack = {},
        navigateToDetail = {}
    )
}
