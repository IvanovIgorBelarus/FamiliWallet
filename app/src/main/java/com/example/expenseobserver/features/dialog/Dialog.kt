package com.example.expenseobserver.features.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenseobserver.R

@Composable
fun ShowErrorDialog(text: String?) {
    val resources = LocalContext.current.resources
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = resources.getString(R.string.dialog_error_title))
            },
            text = {
                Text(text = text.orEmpty())
            },
            confirmButton = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog.value = false }
                    ) {
                        Text(resources.getString(R.string.ok))
                    }
                }
            }
        )
    }
}

@Composable
fun ShowDeleteDialog(
    text: String? = null,
    textResId: Int? = null,
    openDialog: MutableState<Boolean>,
    onClick: () -> Unit
) {
    val resources = LocalContext.current.resources

    val description = if (textResId != null) {
        resources.getString(textResId)
    } else {
        text.orEmpty()
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = resources.getString(R.string.delete_title))
            },
            text = {
                Text(text = description)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onClick.invoke()
                        openDialog.value = false
                    }
                ) {
                    Text(resources.getString(R.string.ok))
                }
            },
            dismissButton = {
                Button(
                    onClick = { openDialog.value = false }
                ) {
                    Text(resources.getString(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun ShowUpdateDialog(
    text: String? = null,
    textResId: Int? = null,
    openDialog: MutableState<Boolean>,
    onClick: @Composable () -> Unit
) {
    val resources = LocalContext.current.resources
    val click = remember { mutableStateOf(false) }

    val description = if (textResId != null) {
        resources.getString(textResId)
    } else {
        text.orEmpty()
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = resources.getString(R.string.update_title))
            },
            text = {
                Text(text = description)
            },
            confirmButton = {
                Button(
                    onClick = {
                        click.value = true
                        openDialog.value = false
                    }
                ) {
                    Text(resources.getString(R.string.ok))
                }
            },
            dismissButton = {
                Button(
                    onClick = { openDialog.value = false }
                ) {
                    Text(resources.getString(R.string.cancel))
                }
            }
        )
    }
    if (click.value) {
        onClick.invoke()
    }
}

