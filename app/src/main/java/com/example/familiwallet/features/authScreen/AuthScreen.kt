package com.example.familiwallet.features.authScreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.R
import com.example.familiwallet.components.TopScreenBlueHeader
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.backgroundColor
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
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topHeader) = createRefs()

            TopScreenBlueHeader(Modifier.constrainAs(topHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                text = resources.getString(R.string.enter)
            )
        }








        //for google button
        loginWithGoogleAccount {
            authViewModel.addUserInCloud {
                navigation?.navigate(Screen.MainScreen.route)
            }
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

private const val TAG = "ERROR"

