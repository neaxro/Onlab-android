package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.feature.common.DashboardCard
import hu.bme.aut.android.securityapp.feature.common.WelcomeBoard
import hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel
){
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()

    val person by remember {
        mutableStateOf(Person(0, "Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", null))
    }

    val messages = remember { viewModel.dashboardMessages }

    viewModel.loadAllDashboards { errorMessage ->
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 5.dp
            ){
                TopAppBar(
                    title = {
                        Text(
                            text = "Dashboard",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    ),
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            Column() {
                WelcomeBoard(
                    person,
                    modifier = Modifier.padding(10.dp)
                )

                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(messages){message ->
                        DashboardCard(
                            message,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}