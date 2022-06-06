package com.bangkit.motravel.ui.places

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


data class PlacesResult(
    val name: String?,
    val address: String?,
    val latLng: LatLng?,
    val description: String?  = null,
    val keywords: List<String>?  = null,
    val budgetMin: Double?  = null,
    val budgetMax: Double?  = null,
    val submittedBy: String?  = null,
    val createdAt: String?,
    val updatedAt: String?
)

class PlacesViewModel: ViewModel() {

    private val _data = MutableLiveData<PlacesResult>()
    val data: LiveData<PlacesResult> = _data

    @SuppressLint("SimpleDateFormat")
    fun updatePlaceResult(
        name: String?,
        address: String?,
        latLng: LatLng?
    ){
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val dateTime: String = df.format(Calendar.getInstance().time)
        _data.value = PlacesResult(
            name = name,
            address = address,
            latLng = latLng,
            createdAt = dateTime,
            updatedAt = dateTime
        )
    }
}