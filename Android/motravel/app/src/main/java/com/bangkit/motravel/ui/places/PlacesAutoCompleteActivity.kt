package com.bangkit.motravel.ui.places

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.bangkit.motravel.BuildConfig
import com.bangkit.motravel.ui.ViewModelFactory
import com.bangkit.motravel.ui.theme.MotravelTheme
import com.bangkit.motravel.ui.theme.TextFieldWithIcons
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@ExperimentalMaterial3Api
class PlacesAutoCompleteActivity : ComponentActivity() {

    private val placesViewModel: PlacesViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Places SDK
        val api = BuildConfig.MAPS_API_KEY
        Places.initialize(this,api)

        setContent{
            val data by placesViewModel.data.observeAsState()
            MotravelTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {startAutocompleteActivity()},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Search Location")
                    }
                    TextFieldWithIcons(
                        text = data?.name.toString(),
                        imageVector = Icons.Default.Home,
                        iconDescription = "Icon Home",
                        label = "Nama",
                        placeholder = "Masukkan nama lokasi"
                    )
                    TextFieldWithIcons(
                        text = data?.address.toString(),
                        imageVector = Icons.Default.Home,
                        iconDescription = "Icon Home",
                        label = "Alamat",
                        placeholder = "Masukkan alamat lokasi"
                    )
                    TextFieldWithIcons(
                        text = data?.latLng?.latitude.toString(),
                        imageVector = Icons.Default.Home,
                        iconDescription = "Icon Home",
                        label = "Latitude",
                        placeholder = "Masukkan Latitude"
                    )
                    TextFieldWithIcons(
                        text = data?.latLng?.longitude.toString(),
                        imageVector = Icons.Default.Home,
                        iconDescription = "Icon Home",
                        label = "Longitude",
                        placeholder = "Masukkan Longitude"
                    )
                }
            }
        }
    }

    private fun startAutocompleteActivity() {

        val placeField = listOf(
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596)
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeField)
            .setCountry("ID")
            .setLocationBias(bounds)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        placesViewModel.updatePlaceResult(
                            name = place.name,
                            address = place.address,
                            latLng = place.latLng
                        )
                        Toast.makeText(this, place.address, Toast.LENGTH_SHORT).show()
                        Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        private const val TAG = "PlacesAutoComplete"
        private const val AUTOCOMPLETE_REQUEST_CODE = 23487
    }
}


