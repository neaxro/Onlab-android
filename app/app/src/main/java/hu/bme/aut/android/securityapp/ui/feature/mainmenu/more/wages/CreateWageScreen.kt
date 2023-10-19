package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.ui.feature.common.MySnackbarHost
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWageScreen(
    navigateBack: () -> Unit = {},
    viewModel: CreateWageScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var isError by remember { mutableStateOf(false) }

    val wage = viewModel.wage.collectAsState().value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Create Wage",
                onNavigate = {
                    navigateBack()
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {



            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Spacer(modifier = Modifier.padding(top = 20.dp))

                WageEditor(
                    name = wage.name,
                    onNameChange = { newName ->
                        viewModel.evoke(CreateWageAction.UpdateWageName(newName = newName))
                    },
                    price = wage.price.toString(),
                    onPriceChange = { newPrice ->
                        viewModel.evoke(CreateWageAction.UpdateWagePrice(newPrice = newPrice))
                    },
                    isError = {
                        isError = it
                    },
                    isReadOnly = false,
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.evoke(CreateWageAction.CreateWage)
                    },
                    enabled = !isError
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Create")
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateWageScreenPreview() {
    CreateWageScreen()
}