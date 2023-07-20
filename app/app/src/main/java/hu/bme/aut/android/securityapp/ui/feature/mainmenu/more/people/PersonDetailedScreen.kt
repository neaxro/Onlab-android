package hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.people

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.QuestionMark
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
import hu.bme.aut.android.securityapp.ui.feature.common.MyDropDownMenu
import hu.bme.aut.android.securityapp.ui.feature.common.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailedScreen(
    navigateBack: () -> Unit,
    viewModel: PersonDetailScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current
    
    val person = viewModel.person.collectAsState().value
    val wages = viewModel.wages.collectAsState().value
    val roles = viewModel.roles.collectAsState().value

    var selectedWage by remember { mutableStateOf<String?>("") }
    var selectedRole by remember { mutableStateOf<String?>("") }

    selectedWage = wages.firstOrNull() { it.id == person.wage.id }?.name
    selectedRole = roles.firstOrNull(){ it.id == person.role.id }?.title

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = person.basicInfo.fullName,
                onNavigate = {
                    navigateBack()
                },
            )
        }
    ) {
        val paddingTop = it.calculateTopPadding()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            Spacer(modifier = Modifier.padding(top = 30.dp))

            MyDropDownMenu(
                list = wages.map { it.name },
                onItemChange = {
                    selectedWage = it
                },
                icon = Icons.Default.Money,
                label = "Wages",
                item = selectedWage
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            MyDropDownMenu(
                list = roles.map { it.title },
                onItemChange = {
                    selectedRole = it
                },
                icon = Icons.Default.QuestionMark,
                label = "Roles",
                item = selectedRole
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            OutlinedButton(
                onClick = {
                    // TODO: Save
                },
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Create")
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PersonDetailedScreenPreview(){
    PersonDetailedScreen(
        navigateBack = {}
    )
}