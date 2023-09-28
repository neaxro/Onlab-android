package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import hu.bme.aut.android.securityapp.ui.feature.mainmenu.more.pendingshifts.ShiftDetailAlertDialogState

@Composable
fun ShiftDetailAcceptAlertDialog(
    state: ShiftDetailAlertDialogState,
    accept: () -> Unit,
    deny: () -> Unit,
    dismiss: () -> Unit,
){
    if(state == ShiftDetailAlertDialogState.AcceptIntent) {
        AlertDialog(
            title = {
                Text(text = "Accept Shift")
            },
            text = {
                Text(text = "Do you want to accept the shift?")
            },
            onDismissRequest = {
                dismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        accept()
                    }
                ) {
                    Text("Accept")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    else if (state == ShiftDetailAlertDialogState.DenyIntent){
        AlertDialog(
            title = {
                Text(text = "Deny Shift")
            },
            text = {
                Text(text = "Do you want to deny the shift?")
            },
            onDismissRequest = {
                dismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        deny()
                    }
                ) {
                    Text("Deny")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}