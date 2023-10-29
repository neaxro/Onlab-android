package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WageDetailScreen(
    viewModel: WageDetailScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
){
    val context = LocalContext.current

    val wage = viewModel.wage.collectAsState().value

    var isError by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.composable_wage_details_title),
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = {
                        isEditing = !isEditing
                    }) {
                        Icon(imageVector = Icons.Default.Edit, stringResource(id = R.string.composable_edit))
                    }
                    IconButton(onClick = {
                        viewModel.evoke(WageDetailAction.DeleteWage)
                    }) {
                        Icon(imageVector = Icons.Default.Delete,
                            stringResource(R.string.composable_delete))
                    }
                },
                screenState = viewModel.screenState.collectAsState()
            )
        },
        snackbarHost = {
            MySnackbarHost(
                screenState = viewModel.screenState,
            )
        },
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(modifier = Modifier.padding(top = paddingTop)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingTop)
            ) {
                Spacer(modifier = Modifier.padding(top = 30.dp))

                WageEditor(
                    name = wage.name,
                    onNameChange = { newName ->
                        viewModel.evoke(WageDetailAction.UpdateName(name = newName))
                    },
                    price = wage.price.toString(),
                    onPriceChange = { newPrice ->
                        viewModel.evoke(WageDetailAction.UpdatePrice(price = newPrice.toDouble()))
                    },
                    isError = {
                        isError = it
                    },
                    isReadOnly = !isEditing
                )

                if(isEditing) {
                    OutlinedButton(
                        onClick = {
                            viewModel.evoke(WageDetailAction.SaveWage)
                        },
                        enabled = !isError
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(
                                id = R.string.composable_refresh
                            ))
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            Text(text = stringResource(
                                id = R.string.composable_refresh
                            ))
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WageDetailScreenPreview(){
    WageDetailScreen()
}