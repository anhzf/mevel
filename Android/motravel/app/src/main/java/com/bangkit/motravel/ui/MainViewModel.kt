package com.bangkit.motravel.ui

import androidx.lifecycle.ViewModel
import com.bangkit.motravel.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getUserProfile() = repository.getUserProfile()
}