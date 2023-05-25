package com.example.components

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
                Text(text = resources.getString(com.example.data.R.string.dialog_error_title))
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
                        Text(resources.getString(com.example.data.R.string.ok))
                    }
                }
            }
        )
    }
}

@Composable
fun ShowDeleteDialog(
    title: String? = null,
    titleResId: Int? = null,
    text: String? = null,
    textResId: Int? = null,
    openDialog: MutableState<Boolean>,
    onClick: () -> Unit
) {
    val resources = LocalContext.current.resources

    val titleString = if (titleResId != null) {
        resources.getString(titleResId)
    } else {
        title.orEmpty()
    }

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
                Text(text = titleString)
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
                        Text(resources.getString(com.example.data.R.string.cancel))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke()
                            openDialog.value = false
                        }
                    ) {
                        Text(resources.getString(com.example.data.R.string.ok))
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
    onClick: (com.example.common.TimeRangeType) -> Unit
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
                            onClick.invoke(com.example.common.TimeRangeType.DAY)
                            openDialog.value = false
                        }
                    ) {
                        Text("День")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke(com.example.common.TimeRangeType.WEEK)
                            openDialog.value = false
                        }
                    ) {
                        Text("Неделя")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClick.invoke(com.example.common.TimeRangeType.MONTH)
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
                Text(text = resources.getString(com.example.data.R.string.update_title))
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
                        Text(resources.getString(com.example.data.R.string.cancel))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            click.value = true
                            openDialog.value = false
                        }
                    ) {
                        Text(resources.getString(com.example.data.R.string.ok))
                    }
                }
            }
        )
    }

    if (click.value) {
        onClick.invoke()
    }
}

