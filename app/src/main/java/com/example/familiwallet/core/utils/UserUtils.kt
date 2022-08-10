package com.example.familiwallet.core.utils

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth

object UserUtils {
    private val user = FirebaseAuth.getInstance().currentUser

    fun isUserSignIn(): Boolean = user != null
    fun getUsersUid(): String? = user?.uid
    fun getUserName(): String? = user?.displayName
    fun getUserPhoto(): Uri? = user?.photoUrl
    fun getUserCreateDate(): Long = user?.metadata!!.creationTimestamp
}