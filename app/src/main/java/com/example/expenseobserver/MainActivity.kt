package com.example.expenseobserver

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.FragmentActivity
import com.alseda.splashscreen_api.SplashScreenApi
import com.example.expenseobserver.core.di.DependencyFeatureProvider
import com.example.expenseobserver.core.theme.FamiliWalletTheme
import com.example.expenseobserver.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var dependencyFeatureProvider: DependencyFeatureProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getFacebookHash()
        setContent {
            FamiliWalletTheme {
                AppNavigation(dependencyFeatureProvider = dependencyFeatureProvider)
            }
        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun getFacebookHash(){
        try {
            val info = packageManager.getPackageInfo(
                "com.example.familiwallet", PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.i(
                    "KeyHash:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }
}


