package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SwitchWithIcon(
    icon: ImageVector,
    checked: Boolean,
    checkedChanged: (Boolean) -> Unit,
) {
    Switch(
        checked = checked,
        onCheckedChange = {
            checkedChanged(!checked)
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}

@Preview(showBackground = false)
@Composable
fun SwitchWithIconPreview(){
    Row {
        SwitchWithIcon(
            icon = Icons.Filled.Check,
            checked = false,
            checkedChanged = {}
        )

        SwitchWithIcon(
            icon = Icons.Filled.Check,
            checked = true,
            checkedChanged = {}
        )
    }
}