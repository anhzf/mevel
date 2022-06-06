package com.bangkit.motravel.data

import com.bangkit.motravel.data.remote.response.UsersProfile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Repository {
    fun getUserProfile(): UsersProfile?{
        val user = Firebase.auth.currentUser
        return user?.let {
            UsersProfile(
                uid = it.uid,
                name = it.displayName,
                email = it.email,
                photoUrl = it.photoUrl,
                emailVerified = it.isEmailVerified
            )
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository()
            }.also { instance = it }
    }
}