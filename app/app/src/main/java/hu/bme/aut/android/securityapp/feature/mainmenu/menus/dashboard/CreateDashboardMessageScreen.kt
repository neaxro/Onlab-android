package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.data.model.wage.Wage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDashboardMessgaeScreen(
    viewModel: CreateDashboardMessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateBack: () -> Unit
){
    val context = LocalContext.current
    val maxTitleLength = 20
    val maxMessageLength = 200

    var title by remember { viewModel.title }
    var titleLength by remember { mutableStateOf(0) }
    var titleError by remember { mutableStateOf(false) }

    var message by remember { viewModel.message }
    var messageLength by remember { mutableStateOf(0) }

    var expanded by remember { mutableStateOf(false) }

    val categories = remember { viewModel.categories }
    var selectedItem by remember { viewModel.selectedCategory }


    LaunchedEffect(true){
        viewModel.loadCategories { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 5.dp
            ) {
                TopAppBar(
                    title = {
                        Text(text = "Create Message")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigateBack()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Go Back"
                            )
                        }
                    }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        if(it.length <= maxTitleLength) {
                            title = it
                            titleLength = title.length
                            titleError = checkTitle(title)
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
                    modifier = Modifier.width(300.dp)
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        if(it.length <= maxMessageLength){
                            message = it
                            messageLength = message.length
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
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp),
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = category.name)
                                       },
                                onClick = {
                                    selectedItem = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedButton(
                    onClick = {
                        val canUpload = isReadyForUpload(titleError, selectedItem){ errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }

                        if(canUpload){
                            viewModel.createMessage(
                                onSuccess = navigateBack,
                                onError = { errorMessage ->
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Done,
                            contentDescription = "Create Message"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

private fun checkTitle(title: String): Boolean{
    val numberRegex = Regex("^[0-9*#+]+\$")
    val specialCharacterRegex = Regex("[!#\$%&'()*+,-./:;\\\\<=>?@[\\\\]^_`{|}~]")

    val result = title.contains(numberRegex) || title.contains(specialCharacterRegex)

    return title.length == 0 && result
}

private fun isReadyForUpload(titleError: Boolean, category: Wage?, onError: (String) -> Unit): Boolean{
    if(titleError) return false

    if(category == null){
        onError("Select a category!")
        return false
    }

    return true
}