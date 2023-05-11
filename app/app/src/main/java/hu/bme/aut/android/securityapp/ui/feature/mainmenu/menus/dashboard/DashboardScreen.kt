package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.domain.wrappers.Roles
import hu.bme.aut.android.securityapp.ui.feature.common.DashboardCard
import hu.bme.aut.android.securityapp.ui.feature.common.WelcomeBoard
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus.dashboard.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navigateToCreateMessage: () -> Unit,
    navigateToDetails: (Int) -> Unit,
){
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()

    val person by remember { viewModel.person }
    val messages = remember { viewModel.dashboardMessages }

    LaunchedEffect(true){
        viewModel.loadAllDashboards { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadPersonData()
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
        },
        floatingActionButton = {
            val role = LoggedPerson.getRole()
            if(role is Roles.Owner || role is Roles.Admin){
                FloatingActionButton(
                    onClick = {
                          navigateToCreateMessage()
                    },
                    shape = ShapeDefaults.Medium,
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Create new message"
                        )
                    }
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
                            onDetails = { messageId ->
                                navigateToDetails(messageId)
                            },
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}