package com.bangkit.motravel.ui.uploadstory

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.SettingsSlicesContract.KEY_LOCATION
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.bangkit.motravel.ui.detail.DetailImagePreview
import com.bangkit.motravel.ui.theme.MotravelTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.motravel.BuildConfig
import com.bangkit.motravel.R
import com.bangkit.motravel.ui.theme.MyGoogleMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

@ExperimentalMaterial3Api
class UploadStoryActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val fireStore = FirebaseFirestore.getInstance()
        fireStore.useEmulator("192.168.1.7", 8080)

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
        fireStore.firestoreSettings = settings

        setContent{
            MotravelTheme{

            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun OutlineTextFieldWithIcon(
    label: String
){
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(text = label) },
        //leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = "homeIcon") },
    )
}

@ExperimentalMaterial3Api
@Composable
fun UploadStoryInputForm(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlineTextFieldWithIcon("Title")

        Text(text = "Address", modifier = Modifier.padding(top = 16.dp))
        OutlineTextFieldWithIcon("Province")
        OutlineTextFieldWithIcon("City")
        OutlineTextFieldWithIcon("Street")

        Text(text = "Budget Range", modifier = Modifier.padding(top = 16.dp))
        OutlineTextFieldWithIcon("Min Budget")
        OutlineTextFieldWithIcon("Max Budget")

        Text(text = "Keyword", modifier = Modifier.padding(top = 16.dp))
        OutlineTextFieldWithIcon("Keyword")
    }
}