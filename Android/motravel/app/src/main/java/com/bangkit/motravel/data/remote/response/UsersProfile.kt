package com.bangkit.motravel.data.remote.response

import android.net.Uri

data class UsersProfile(
    val uid: String?,
    val name: String?,
    val email: String?,
    val photoUrl: Uri?,
    val emailVerified: Boolean,
)



