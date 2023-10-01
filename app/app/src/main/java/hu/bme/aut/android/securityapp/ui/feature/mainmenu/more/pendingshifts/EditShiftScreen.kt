package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.ui.feature.common.DoubleDataRow
import hu.bme.aut.android.securityapp.ui.feature.common.MyDropDownMenu
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditShiftScreen(
    navigateBack: () -> Unit,
){
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Edit Shift",
                onNavigate = { navigateBack() }
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
                .padding(10.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DoubleDataRow(
                    title = { Text(text = "Start date:") },
                    value = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "2023.04.14")
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Start time:") },
                    value = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "22:50")
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "End date:") },
                    value = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "2023.04.14")
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "End time:") },
                    value = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "22:50")
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Start date:") },
                    value = {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "2023.04.14")
                        }
                    }
                )

                DoubleDataRow(
                    title = { Text(text = "Wage:") },
                    value = {
                        MyDropDownMenu(
                            list = listOf("Default", "Kutyas", "VIP"),
                            onItemChange = {},
                            icon = Icons.Default.Wallet,
                            label = "Choose wage"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditShiftScreenPreview(){
    EditShiftScreen(
        navigateBack = {}
    )
}