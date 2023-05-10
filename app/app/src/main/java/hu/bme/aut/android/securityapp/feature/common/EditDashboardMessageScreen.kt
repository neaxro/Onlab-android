package hu.bme.aut.android.securityapp.feature.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.constants.LoggedPerson
import hu.bme.aut.android.securityapp.data.model.dashboard.CreateDashboardData
import hu.bme.aut.android.securityapp.data.model.wage.Wage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDashboardMessageScreen(
    message: CreateDashboardData = CreateDashboardData("", "", LoggedPerson.CURRENT_JOB_ID, LoggedPerson.ID, 0),
    wages: List<Wage>,
    onClick: (CreateDashboardData) -> Unit,
    buttonContent: @Composable RowScope.() -> Unit,
    isReadOnly: Boolean = false,
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current
    val maxTitleLength = 20
    val maxMessageLength = 200

    var message by remember { mutableStateOf(message) }
    var titleLength by remember { mutableStateOf(0) }
    var messageLength by remember { mutableStateOf(0) }
    var titleError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem: Wage? by remember { mutableStateOf(null) }

    if(message.groupId > 0 && wages.isNotEmpty()){
        selectedItem = wages.first { it.id == message.groupId }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = message.title,
            onValueChange = {
                if(it.length <= maxTitleLength) {
                    message = message.copy(title = it)
                    titleLength = it.length
                    titleError = checkTitle(it)
                }
            },
            label = {
                Text(text = "Title")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Title, contentDescription = "Title")
            },
            singleLine = true,
            supportingText = {
                Text(
                    text = "$titleLength/$maxTitleLength",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            isError = titleError,
            trailingIcon = {
                if(titleError){
                    Icon(imageVector = Icons.Rounded.Error, contentDescription = "Invalid Title", tint = Color.Red)
                }
                else if(titleLength > 0){
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = "Valid Title", tint = Color.Green)
                }
            },
            readOnly = isReadOnly,
            enabled = !isReadOnly,
            modifier = Modifier.width(300.dp)
        )

        OutlinedTextField(
            value = message.message,
            onValueChange = {
                if(it.length <= maxMessageLength){
                    message = message.copy(message = it)
                    messageLength = it.length
                }
            },
            label = {
                Text(text = "Message")
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
                .height(180.dp)
                .width(300.dp),
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {
            OutlinedTextField(
                value = selectedItem?.name ?: "",
                onValueChange = {},
                label = {
                    Text(text = "Category")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Category,
                        contentDescription = "Categories"
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

            if(!isReadOnly) {
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
                                selectedItem = category
                                message = message.copy(groupId = selectedItem!!.id)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }


        if(!isReadOnly) {
            Spacer(modifier = Modifier.height(30.dp))

            OutlinedButton(
                onClick = {
                    val canUpload = isReadyForUpload(titleError, selectedItem) { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    if (canUpload) {
                        onClick(message)
                    }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    buttonContent()
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

private fun isReadyForUpload(titleError: Boolean, category: Wage?, onError: (String) -> Unit): Boolean{
    if(titleError) return false

    if(category == null){
        onError("Select a category!")
        return false
    }

    return true
}

@Composable
@Preview(showBackground = true)
fun EditDashboardMessageScreenPreview(){
    val wages = listOf(
        Wage(1, "Default", 1000, "Valami"),
        Wage(2, "Dog", 1000, "Valami"),
        Wage(3, "Special", 1000, "Valami"),
    )
    EditDashboardMessageScreen(
        message = CreateDashboardData("Valami", "Hello vilag mi a helyzet?", LoggedPerson.CURRENT_JOB_ID, LoggedPerson.ID, 2),
        wages = wages,
        onClick = {},
        isReadOnly = false,
        buttonContent = {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Create Message"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Create")
        }
    )
}