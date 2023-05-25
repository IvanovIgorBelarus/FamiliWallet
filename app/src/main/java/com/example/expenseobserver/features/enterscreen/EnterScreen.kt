package com.example.expenseobserver.features.enterscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.BuildConfig
import com.example.expenseobserver.R
import com.example.components.EnterButton
import com.example.components.TopScreenBlueHeader
import com.example.data.theme.UiState
import com.example.components.ShowErrorDialog
import com.example.components.ShowUpdateDialog
import com.example.components.LoadingScreen
import com.example.expenseobserver.features.updateversion.utils.UpdateAppUtils
import com.example.data.theme.textColor
import com.example.navigation.Screen

@Composable
fun EnterScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null,
    enterViewModel: EnterViewModel = hiltViewModel()
) {
    val uiState by enterViewModel.getUiState()
    val showUpdateDialog = remember { mutableStateOf(false) }
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        val resources = LocalContext.current.resources
        val topHeaderHeight = LocalConfiguration.current.screenHeightDp.dp / 3
        val imageWidth = LocalConfiguration.current.screenWidthDp.dp - LocalConfiguration.current.screenWidthDp.dp / 4
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (topHeader, image, text, button) = createRefs()

            TopScreenBlueHeader(
                text = resources.getString(R.string.money),
                modifier = modifier
                    .constrainAs(topHeader) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

//            Box(
//                modifier
//                    .height(topHeaderHeight)
//                    .constrainAs(topHeader) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                        width = Dimension.fillToConstraints
//                    }
//                    .background(color = buttonColor, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
//            )
//            Text(
//                text = resources.getString(R.string.money),
//                Modifier
//                    .constrainAs(title) {
//                        top.linkTo(topHeader.top,topHeaderHeight/4)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    },
//                fontSize = 36.sp,
//                color = backgroundColor,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Center
//            )
            Image(
                modifier = modifier
                    .width(imageWidth)
                    .constrainAs(image) {
                        top.linkTo(topHeader.top, topHeaderHeight / 3)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_cards),
                contentDescription = null
            )
            Text(
                text = resources.getString(R.string.greetings),
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .constrainAs(text) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(button.top)
                    },
                fontSize = 30.sp,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
            EnterButton(Modifier
                .constrainAs(button) {
                    top.linkTo(text.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                val nextDestination = if (com.example.common.utils.UserUtils.isUserSignIn()) {
                    Screen.MainScreen.route
                } else {
                    Screen.AuthScreen.route
                }
                navigation?.navigate(nextDestination)
            }
        }
    }

    //check new version
    val error = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    when (uiState) {
        is UiState.Success -> {
            val updateModel = (uiState as UiState.Success<com.example.data.UIModel.UpdateModel>).data
            val currentVersionCode = BuildConfig.VERSION_CODE.toLong()
            if (currentVersionCode < (updateModel.versionCode ?: 0)) {
                showUpdateDialog.value = true
                ShowUpdateDialog(
                    text = updateModel.description ?: LocalContext.current.resources.getString(R.string.update_title_description),
                    openDialog = showUpdateDialog
                ) {
                    Log.e("MYNAME", LocalContext.current.applicationContext.packageName + ".provider")
                    val up = UpdateAppUtils(
                        appUrl = updateModel.url.orEmpty(),
                        showErrorDialog = { exception ->
                            error.value = true
                            errorMessage.value = exception.message.orEmpty()
                        }
                    )
                    up.installApp()
                }
            }
        }
        is UiState.Error -> {}
        is UiState.Loading -> {
            LoadingScreen()
        }
    }

    if (error.value) {
        ShowErrorDialog(text = errorMessage.value)
    }

    LaunchedEffect(Unit) {
        enterViewModel.getData()
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterScreenPreview() {
    EnterScreen()
}