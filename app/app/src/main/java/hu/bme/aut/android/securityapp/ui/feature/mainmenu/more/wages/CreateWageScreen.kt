package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.wages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWageScreen(
    navigateBack: () -> Unit = {},
    viewModel: CreateWageScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("1300.0") }
    var isError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Create Wage",
                onNavigate = {
                    navigateBack()
                },
            )
        }
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
                    name = name,
                    onNameChange = { newName ->
                        name = newName
                    },
                    price = price,
                    onPriceChange = { newPrice ->
                        price = newPrice
                    },
                    isError = {
                        isError = it
                    },
                    isReadOnly = false,
                )

                Spacer(modifier = Modifier.padding(top = 20.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.createWage(name = name, price = price.toDouble())
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

            if(state is ScreenState.Loading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else if(state is ScreenState.Error){
                Text(
                    text = state.message,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Gray, RoundedCornerShape(10.dp))
                        .padding(10.dp)

                )
            }
            else if(state is ScreenState.Success){
                Toast.makeText(context, "Successfully created!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CreateWageScreenPreview() {
    CreateWageScreen()
}