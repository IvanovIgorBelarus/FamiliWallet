package com.example.common.utils

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth

object UserUtils {
    private fun getUser() = FirebaseAuth.getInstance().currentUser
    fun isUserSignIn(): Boolean = getUser() != null
    fun getUsersUid(): String? = getUser()?.uid
    fun getUserName(): String? = getUser()?.displayName
    fun getUserPhoto(): Uri? = getUser()?.photoUrl
    fun getUserCreateDate(): Long = getUser()?.metadata!!.creationTimestamp
}