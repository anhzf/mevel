package com.bangkit.motravel.di

import android.content.Context
import com.bangkit.motravel.data.Repository

object Injection {
    fun provideRepository(context: Context): Repository{
        return Repository.getInstance()
    }
}