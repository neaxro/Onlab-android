package hu.bme.aut.android.securityapp.ui.feature.wages

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.android.securityapp.domain.wrappers.ScreenState
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar
import hu.bme.aut.android.securityapp.ui.feature.common.WageEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WageDetailScreen(
    viewModel: WageDetailScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
){
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val wage = viewModel.wage.collectAsState().value

    var isError by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Wage Details",
                onNavigate = {
                    navigateBack()
                },
                actions = {
                    IconButton(onClick = {
                        isEditing = !isEditing
                    }) {
                        Icon(imageVector = Icons.Default.Edit, "Edit")
                    }
                    IconButton(onClick = {
                        viewModel.deleteWage()
                    }) {
                        Icon(imageVector = Icons.Default.Delete, "Delete")
                    }
                }
            )
        }
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
                        viewModel.onEvoke(WageDetailAction.UpdateName(name = newName))
                    },
                    price = wage.price.toString(),
                    onPriceChange = { newPrice ->
                        viewModel.onEvoke(WageDetailAction.UpdatePrice(price = newPrice.toDouble()))
                    },
                    isError = {
                        isError = it
                    },
                    isReadOnly = !isEditing
                )

                if(isEditing) {
                    OutlinedButton(
                        onClick = {
                            viewModel.saveWage()
                        },
                        enabled = !isError
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            Text(text = "Save")
                        }
                    }
                }
            }

            if(state is ScreenState.Success){
                Toast.makeText(context, "Succesfull update!", Toast.LENGTH_SHORT).show()
            }
        }

        if(state is ScreenState.Error){
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            Log.d("WAGE_ERROR", state.message)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WageDetailScreenPreview(){
    WageDetailScreen()
}