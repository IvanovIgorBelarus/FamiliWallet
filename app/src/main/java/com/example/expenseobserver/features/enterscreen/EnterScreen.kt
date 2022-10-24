package com.example.expenseobserver.features.enterscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.example.expenseobserver.R
import com.example.expenseobserver.components.EnterButton
import com.example.expenseobserver.components.TopScreenBlueHeader
import com.example.expenseobserver.core.utils.UserUtils
import com.example.expenseobserver.navigation.Screen
import com.example.expenseobserver.ui.theme.enterTextColor

@Composable
fun EnterScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null
) {
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
                fontWeight = FontWeight.W500
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
}

@Preview(showBackground = true)
@Composable
private fun EnterScreenPreview() {
    EnterScreen()
}