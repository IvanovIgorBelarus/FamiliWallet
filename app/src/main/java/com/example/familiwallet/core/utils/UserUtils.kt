package com.example.familiwallet.core.utils

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UserUtils {
    private fun getUser() = FirebaseAuth.getInstance().currentUser
    fun isUserSignIn(): Boolean = getUser() != null
    fun getUsersUid(): String? = getUser()?.uid
    fun getUserName(): String? = getUser()?.displayName
    fun getUserPhoto(): Uri? = getUser()?.photoUrl
    fun getUserCreateDate(): Long = getUser()?.metadata!!.creationTimestamp
}