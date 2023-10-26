package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hu.bme.aut.android.securityapp.R
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
                Text(text = stringResource(R.string.composable_accept_shift))
            },
            text = {
                Text(text = stringResource(R.string.composable_do_you_want_to_accept_the_shift))
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
                    Text(stringResource(R.string.composable_accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text(stringResource(R.string.composable_cancel))
                }
            }
        )
    }
    else if (state == ShiftDetailAlertDialogState.DenyIntent){
        AlertDialog(
            title = {
                Text(text = stringResource(R.string.composable_deny_shift))
            },
            text = {
                Text(text = stringResource(R.string.composable_do_you_want_to_deny_the_shift))
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
                    Text(stringResource(R.string.composable_deny))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text(stringResource(R.string.composable_cancel))
                }
            }
        )
    }
}