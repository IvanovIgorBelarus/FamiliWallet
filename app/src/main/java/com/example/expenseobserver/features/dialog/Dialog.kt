package com.example.expenseobserver.features.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenseobserver.R
import com.example.expenseobserver.core.common.TimeRangeType

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
            buttons = {
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
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { openDialog.value = false }
                    ) {
                        Text(resources.getString(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke()
                            openDialog.value = false
                        }
                    ) {
                        Text(resources.getString(R.string.ok))
                    }
                }
            }
        )
    }
}

@Composable
fun ShowTimeDialog(
    text: String? = null,
    textResId: Int? = null,
    openDialog: MutableState<Boolean>,
    onClick: (TimeRangeType) -> Unit
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
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke(TimeRangeType.DAY)
                            openDialog.value = false
                        }
                    ) {
                        Text("День")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke(TimeRangeType.WEEK)
                            openDialog.value = false
                        }
                    ) {
                        Text("Неделя")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke(TimeRangeType.MONTH)
                            openDialog.value = false
                        }
                    ) {
                        Text("Месяц")
                    }
                }
            }
        )
    }
}

