package com.example.familiwallet.features.enterscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.familiwallet.R
import com.example.familiwallet.components.EnterButton
import com.example.familiwallet.components.TopScreenBlueHeader
import com.example.familiwallet.core.utils.UserUtils
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.backgroundColor
import com.example.familiwallet.ui.theme.enterTextColor

@Composable
fun EnterScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = backgroundColor
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = backgroundColor),
                ) {
                    if (UserUtils.isUserSignIn()) {
                        navigation?.navigate(Screen.MainScreen.route)
                    } else {
                        navigation?.navigate(Screen.AuthScreen.route)
                    }
                }
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 60.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EnterScreenPreview() {
    EnterScreen()
}