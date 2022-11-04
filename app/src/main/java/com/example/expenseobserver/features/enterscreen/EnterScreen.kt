package com.example.expenseobserver.features.enterscreen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.example.expenseobserver.MainActivity
import com.example.expenseobserver.R
import com.example.expenseobserver.components.EnterButton
import com.example.expenseobserver.components.TopScreenBlueHeader
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.core.ui.UiState
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.features.dialog.ShowUpdateDialog
import com.example.expenseobserver.features.loading.LoadingScreen
import com.example.expenseobserver.features.updateversion.utils.UpdateAppUtils
import com.example.expenseobserver.navigation.Screen
import com.example.expenseobserver.ui.theme.enterTextColor

@Composable
fun EnterScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null,
    enterViewModel: EnterViewModel = hiltViewModel()
) {
    val uiState by enterViewModel.getUiState()
    val showUpdateDialog = remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        val resources = LocalContext.current.resources

        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (topHeader, image, text, button) = createRefs()

            TopScreenBlueHeader(
                Modifier.constrainAs(topHeader) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = resources.getString(R.string.money)
            )
            Image(
                modifier = modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = 130.dp)
                        start.linkTo(parent.start, margin = 36.dp)
                        end.linkTo(parent.end, margin = 36.dp)
                        width = Dimension.fillToConstraints
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
                        top.linkTo(image.bottom, margin = 48.dp)
                    },
                fontSize = 30.sp,
                color = enterTextColor,
                fontWeight = FontWeight.Medium
            )
            EnterButton(Modifier
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 60.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                val nextDestination = if (UserUtils.isUserSignIn()) {
                    Screen.MainScreen.route
                } else {
                    Screen.AuthScreen.route
                }
                navigation?.navigate(nextDestination)
            }
        }
    }

    //check new version

    when (uiState) {
        is UiState.Success -> {
            val updateModel = (uiState as UiState.Success<UIModel.UpdateModel>).data
            val currentVersionCode = BuildConfig.VERSION_CODE.toLong()
            if (currentVersionCode < (updateModel.versionCode ?: 0)) {
                showUpdateDialog.value = true
                ShowUpdateDialog(
                    textResId = R.string.update_title_description,
                    openDialog = showUpdateDialog
                ) {
                    //update
                }
            }
        }
        is UiState.Error -> {}
        is UiState.Loading -> {
            LoadingScreen()
        }
    }

    LaunchedEffect(Unit) {
        enterViewModel.getData()
    }
}
//
//@Preview(showBackground = true)
//@Composable
//private fun EnterScreenPreview() {
//    EnterScreen()
//}