package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import hu.bme.aut.android.securityapp.R

@Composable
fun MaxLengthOutlinedTextField(
    value: String,
    label: @Composable() (() -> Unit),
    onValueChange: (String) -> Unit,
    isError: Boolean,
    singleLine: Boolean,
    maxLength: Int,
    readOnly: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            label()
        },
        isError = isError,
        singleLine = singleLine,
        supportingText = {
            Text(
                text = stringResource(
                    R.string.composable_min_max_length_indicator,
                    value.length,
                    maxLength
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Rounded.Error,
                    contentDescription = stringResource(R.string.composable_invalid_value),
                    tint = Color.Red
                )
            } else if (value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(R.string.composable_valid_value),
                    tint = Color.Green
                )
            }
        },
        readOnly = readOnly,
        enabled = enabled,
        modifier = modifier
    )
}