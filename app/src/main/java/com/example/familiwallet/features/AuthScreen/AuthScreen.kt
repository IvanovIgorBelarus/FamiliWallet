package com.example.familiwallet.features.AuthScreen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.navigation.Screen
import com.example.familiwallet.ui.theme.backgroundColor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthScreen(
    navigation: NavHostController,
    googleSignInClient: GoogleSignInClient,
    auth: FirebaseAuth,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold(
        backgroundColor = backgroundColor
    ) {
        val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { authResult ->
                            if (authResult.isSuccessful) {
                                authViewModel.addUserInCloud {
                                    navigation.navigate(Screen.MainScreen.route)
                                }
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
}

