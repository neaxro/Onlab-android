package hu.bme.aut.android.securityapp.ui.feature.mainmenu.menus

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.domain.wrappers.Roles
import hu.bme.aut.android.securityapp.ui.feature.common.MoreMenuItem
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.model.MoreMenu
import hu.bme.aut.android.securityapp.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MoreScreen(
    userRole: Roles = LoggedPerson.getRole(),
    navigateTo: (String) -> Unit,
){
    val anonymousMenuItems = listOf(
        MoreMenu(title = stringResource(id = R.string.composable_my_profile_title), icon = Icons.Default.AccountBox, url = Screen.Profile.fullRoute, backgroundColor = Color.Green),
    )
    val userMenuItems = listOf(
        *(anonymousMenuItems.toTypedArray()),
        MoreMenu(title = stringResource(id = R.string.composable_statistics_title), icon = Icons.Default.QueryStats, url = Screen.Statistics.fullRoute, backgroundColor = Color.Green),
    )
    val adminMenuItems = listOf(
        *(userMenuItems.toTypedArray()),
        MoreMenu(title = stringResource(R.string.composable_pending_shifts_title), icon = Icons.Default.WorkHistory, url = Screen.PendingShifts.fullRoute, backgroundColor = Color.Green),
        MoreMenu(title = stringResource(id = R.string.composable_job_statistics_title), icon = Icons.Default.AddChart, url = Screen.JobStatistics.fullRoute, backgroundColor = Color.Green),
    )
    val ownerMenuItems = listOf(
        *(adminMenuItems.toTypedArray()),
        MoreMenu(title = stringResource(R.string.composable_people_on_job_title), icon = Icons.Default.People, url = Screen.People.fullRoute, backgroundColor = Color.Green),
        MoreMenu(title = stringResource(R.string.composable_wage_categories_title), icon = Icons.Default.MonetizationOn, url = Screen.Wages.fullRoute, backgroundColor = Color.Green),
    )

    val menuItems = when(userRole){
        is Roles.Owner -> ownerMenuItems
        is Roles.Admin -> adminMenuItems
        is Roles.User -> userMenuItems
        else -> anonymousMenuItems
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_more_title),
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(
            modifier = Modifier
                .padding(top = paddingTop)
        ){
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize()
            ){
                items(menuItems) { menu ->
                    MoreMenuItem(
                        title = menu.title,
                        icon = menu.icon!!,
                        color = menu.color,
                        backgroundColor = menu.backgroundColor,
                        onClick = { navigateTo(menu.url) },
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenStatistics(){
    MoreScreen(Roles.Owner(), {})
}