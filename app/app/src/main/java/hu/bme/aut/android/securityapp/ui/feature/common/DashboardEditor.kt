package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.wage.Wage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardEditor(
    title: String,
    onTitleChange: (String) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    selectedWage: Wage,
    onWageChange: (Wage) -> Unit,
    wages: List<Wage>,
    isReadOnly: Boolean = false,
    isError: Boolean = false,
    onErrorChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    val maxTitleLength = 20
    val maxMessageLength = 200

    var titleLength by remember { mutableStateOf(0) }
    var messageLength by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                if (it.length <= maxTitleLength) {
                    onTitleChange(it)
                    titleLength = it.length
                    onErrorChanged(checkTitle(it))
                }
            },
            label = {
                Text(text = stringResource(R.string.composable_title))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Title,
                    contentDescription = stringResource(R.string.composable_title)
                )
            },
            singleLine = true,
            supportingText = {
                Text(
                    text = "$titleLength/$maxTitleLength",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            isError = isError,
            trailingIcon = {
                if (isError) {
                    Icon(
                        imageVector = Icons.Rounded.Error,
                        contentDescription = stringResource(R.string.composable_invalid_title),
                        tint = Color.Red
                    )
                } else if (titleLength > 0) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = stringResource(R.string.composable_valid_title),
                        tint = Color.Green
                    )
                }
            },
            readOnly = isReadOnly,
            enabled = !isReadOnly,
            modifier = Modifier.width(300.dp)
        )

        OutlinedTextField(
            value = message,
            onValueChange = {
                if (it.length <= maxMessageLength) {
                    onMessageChange(it)
                    messageLength = it.length
                }
            },
            label = {
                Text(text = stringResource(R.string.composable_message))
            },
            singleLine = false,
            maxLines = 10,
            supportingText = {
                Text(
                    text = "$messageLength/$maxMessageLength",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            readOnly = isReadOnly,
            enabled = !isReadOnly,
            modifier = Modifier
                .height(220.dp)
                .width(300.dp),
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {
            OutlinedTextField(
                value = selectedWage.name,
                onValueChange = {},
                label = {
                    Text(text = stringResource(R.string.composable_category))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Category,
                        contentDescription = stringResource(R.string.composable_categories)
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                enabled = !isReadOnly,
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .width(300.dp),
            )

            if (!isReadOnly) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    wages.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(text = category.name)
                            },
                            onClick = {
                                onWageChange(category)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun checkTitle(title: String): Boolean{
    val numberRegex = Regex(".*[0-9]+.*")
    val specialCharacterRegex = Regex(".*[!#\$%&'()*+,-./:;\\\\<=>?@[\\\\]^_`{|}~].*")

    val result = title.matches(numberRegex) || title.matches(specialCharacterRegex)

    return title.isEmpty() || result
}

@Composable
@Preview(showBackground = true)
fun EditDashboardMessageScreenPreview(){
    var dash by remember {
        mutableStateOf(Dashboard(groupId = 2))
    }
    val wages = listOf(
        Wage(1, "Default", 1000.0, "Valami"),
        Wage(2, "Dog", 1000.0, "Valami"),
        Wage(3, "Special", 1000.0, "Valami"),
    )

    DashboardEditor(
        title = dash.title,
        onTitleChange = {dash = dash.copy(title = it)},
        message = dash.message,
        onMessageChange = {dash = dash.copy(message = it)},
        selectedWage = wages.first { it.id == dash.groupId }.copy(),
        onWageChange = {dash = dash.copy(groupId = it.id)},
        wages = wages,
        isError = false,
        onErrorChanged = {}
    )
}