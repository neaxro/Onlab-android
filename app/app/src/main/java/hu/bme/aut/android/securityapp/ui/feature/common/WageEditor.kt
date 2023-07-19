package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WageEditor(
    name: String,
    onNameChange: (String) -> Unit = {},
    price: String,
    onPriceChange: (String) -> Unit = {},
    isError: (Boolean) -> Unit = {},
    isReadOnly: Boolean = false,
    modifier: Modifier = Modifier
){
    val maxNameLength = 30
    val isNameError = checkName(name)
    val isPriceError = price.isEmpty() || if(price.toDoubleOrNull() != null) price.toDouble() <= 0 else false

    isError(isNameError || isPriceError)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(10.dp)
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = {
                if(it.length <= maxNameLength) {
                    onNameChange(it)
                }
            },
            label = {
                Text(text = "Name")
            },
            isError = isNameError,
            singleLine = true,
            supportingText = {
                Text(
                    text = "${name.length}/$maxNameLength",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            trailingIcon = {
                if (isNameError) {
                    Icon(
                        imageVector = Icons.Rounded.Error,
                        contentDescription = "Invalid Title",
                        tint = Color.Red
                    )
                } else if (name.length > 0) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Valid Title",
                        tint = Color.Green
                    )
                }
            },
            readOnly = isReadOnly,
            enabled = !isReadOnly,
            modifier = Modifier.width(300.dp)
        )

        OutlinedTextField(
            value = price,
            onValueChange = {
                if(it.isEmpty()){
                    onPriceChange(it)
                }
                else{
                    val newPrice: String = when(it.toDoubleOrNull()){
                        null -> price
                        else -> it
                    }
                    onPriceChange(newPrice)
                }
            },
            singleLine = true,
            label = {
                Text(text = "Price")
            },
            isError = isPriceError,
            trailingIcon = {
                Text(text = "Ft/hour", modifier = Modifier.padding(end = 10.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            readOnly = isReadOnly,
            enabled = !isReadOnly,
            modifier = Modifier.width(300.dp)
        )

    }
}

private fun checkName(name: String): Boolean {
    val numberRegex = Regex(".*[0-9]+.*")
    val specialCharacterRegex = Regex(".*[!#\$%&'()*+,-./:;\\\\<=>?@[\\\\]^_`{|}~].*")
    val result = name.matches(numberRegex) || name.matches(specialCharacterRegex)

    return name.isEmpty() || result
}

@Composable
@Preview(showBackground = true)
fun WageEditorPreview(){
    var name by remember{ mutableStateOf("Default") }
    var price by remember{ mutableStateOf("1300.0") }
    var isError by remember{ mutableStateOf(false) }

    Column() {
        WageEditor(
            name = name,
            onNameChange = {
                name = it
            },
            price = price,
            onPriceChange = {
                price = it
            },
            isError = {
                isError = it
            }
        )

        OutlinedButton(
            onClick = { /*TODO*/ },
            enabled = !isError
        ) {
            Text(text = "Save")
        }
    }
}