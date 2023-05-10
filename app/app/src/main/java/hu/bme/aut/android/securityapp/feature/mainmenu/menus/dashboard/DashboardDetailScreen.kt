package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.feature.common.EditDashboardMessageScreen
import hu.bme.aut.android.securityapp.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    messageId: Int,
    navigateBack: () -> Unit,
){
    var isReadOnly by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Message Detail - $messageId",
                onNavigate = navigateBack,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete message"
                        )
                    }
                    IconButton(onClick = {
                        isReadOnly = !isReadOnly
                    }) {
                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit message"
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        val paddingTop = paddingValue.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            EditDashboardMessageScreen(
                wages = listOf(),
                onClick = {},
                isReadOnly = isReadOnly,
                buttonContent = {
                    Icon(
                        imageVector = Icons.Rounded.Save,
                        contentDescription = "Save Message"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Create")
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DashboardDetailScreenPreview(){
    DashboardDetailScreen(
        messageId = 7,
        navigateBack = {},
    )
}