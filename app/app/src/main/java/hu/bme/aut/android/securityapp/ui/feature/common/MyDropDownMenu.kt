package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dock
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenu(
    isReadOnly: Boolean = false,
    list: List<String>,
    item: String? = null,
    onItemChange: (String) -> Unit,
    icon: ImageVector,
    label: String,
){
    var expanded by remember { mutableStateOf(false) }
    var activeItem by remember { mutableStateOf("") }

    activeItem = item ?: if(list.isEmpty()) "" else list[0]

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        }
    ) {
        OutlinedTextField(
            value = activeItem,
            onValueChange = {},
            label = {
                Text(text = label)
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon"
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
                list.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it)
                        },
                        onClick = {
                            onItemChange(it)
                            activeItem = it
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MyDropDownMenuPreview(){
    val list = listOf("Default", "Kutyas", "Specialis", "Default", "Kutyas", "Specialis")
    MyDropDownMenu(
        list = list,
        onItemChange = {

        },
        icon = Icons.Default.Dock,
        label = "Valami"
    )
}