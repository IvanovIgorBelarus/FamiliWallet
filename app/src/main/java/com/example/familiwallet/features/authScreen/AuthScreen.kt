package com.example.familiwallet.features.authScreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.R
import com.example.familiwallet.components.EnterButton
import com.example.familiwallet.components.TopScreenBlueHeader
import com.example.familiwallet.core.common.EnterType
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.backgroundColor
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AuthScreen(
    navigation: NavHostController? = null,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val resources = LocalContext.current.resources

    Scaffold(
        backgroundColor = backgroundColor
    ) {
        var onGoogleClick by remember { mutableStateOf(EnterType.UNKNOWN) }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topHeader, googleButton, facebookButton) = createRefs()

            TopScreenBlueHeader(
                Modifier.constrainAs(topHeader) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = resources.getString(R.string.enter)
            )

            EnterButton(
                text = R.string.google,
                modifier = Modifier
                    .constrainAs(googleButton) {
                        top.linkTo(topHeader.bottom, margin = 48.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }) {
                onGoogleClick = EnterType.GOOGLE
            }

            EnterButton(
                text = R.string.facebook,
                modifier = Modifier
                    .constrainAs(facebookButton) {
                        top.linkTo(googleButton.bottom, margin = 24.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }) {
                onGoogleClick = EnterType.FACEBOOK
            }
        }


        //for google button
        when (onGoogleClick) {
            EnterType.GOOGLE ->
                loginWithGoogleAccount {
                    authViewModel.addUserInCloud {
                        navigation?.navigate(Screen.MainScreen.route)
                    }
                }
            EnterType.EMAIL ->
                loginWithEmailAndPassword(email = "", password = "") {
                    navigation?.navigate(Screen.MainScreen.route)
                }
            EnterType.FACEBOOK -> loginWithFacebook {
                navigation?.navigate(Screen.MainScreen.route)
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    AuthScreen()
}

@Composable
private fun loginWithGoogleAccount(
    onSuccess: () -> Unit
) {
    val resources = LocalContext.current.resources
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(resources.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)

    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                Firebase.auth.signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            onSuccess.invoke()
                        } else {
                            Log.w("ERROR", "signInWithCredential:failure", authResult.exception)
                        }
                    }
            } catch (e: ApiException) {
                Log.w("ERROR", "Google signin failed", e)

            }
        }
    }
    SideEffect {
        startForResult.launch(googleSignInClient.signInIntent)
    }
}

@Composable
private fun loginWithEmailAndPassword(
    email: String,
    password: String,
    onSuccess: () -> Unit
) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(LocalContext.current as Activity) { task ->
            if (task.isSuccessful) {
                onSuccess.invoke()
            } else {
                Log.w(TAG, "signInWithEmailAndPassword: failure", task.exception)
            }
        }
}

@Composable
private fun loginWithFacebook(onSuccess: () -> Unit) {
    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()

    loginManager.logIn(LocalContext.current as ActivityResultRegistryOwner, callbackManager, listOf())

    loginManager.registerCallback(
        callbackManager,
        object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
                Log.w(TAG, "loginWithFacebook: failure", error)
            }

            override fun onSuccess(result: LoginResult) {
                onSuccess.invoke()
            }
        })
}

private const val TAG = "ERROR"

