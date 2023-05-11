package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.domain.wrappers.Roles
import hu.bme.aut.android.securityapp.ui.feature.common.DashboardCard
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WelcomeBoard
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.dashboard.DashboardViewModel

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
             MyTopAppBar(title = "Dashboard")
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
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            if(messages.isEmpty()){
                Text(text = "There are no messages!", modifier = Modifier.align(Alignment.Center))
            }
            else{
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(messages){message ->
                        if(messages.indexOf(message) == 0){
                            Spacer(modifier = Modifier.height(120.dp))
                        }

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

            WelcomeBoard(
                person,
                modifier = Modifier
                    //.padding(10.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}