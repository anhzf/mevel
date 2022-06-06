package com.bangkit.motravel.ui.account

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bangkit.motravel.ui.MainViewModel
import com.bangkit.motravel.ui.theme.Neutral50
import com.bangkit.motravel.ui.theme.ProfileAvatar
import com.bangkit.motravel.ui.uploadstory.UploadStoryActivity

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    mainViewModel: MainViewModel
){
    val mContext = LocalContext.current
    // User Profile
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        ){
            mainViewModel.getUserProfile()?.let{ user ->
                // Profile Avatar
                Column(
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    user.photoUrl?.let { ProfileAvatar(
                        painter = rememberImagePainter(it),
                        contentDescription = "Profile Avatar"
                    ) }
                }

                // Profile Name & Email
                Column{
                    user.name?.let{ Text(text = it, fontWeight = FontWeight.Bold, fontSize = 20.sp) }
                    user.email?.let{ Text(text = it, fontSize = 16.sp ) }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ){

                        // User Story, Follower, Following
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Story", fontSize = 12.sp, color = Neutral50)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Follower", fontSize = 12.sp, color = Neutral50)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Following", fontSize = 12.sp, color = Neutral50)
                        }
                    }
                }
            }
        }

        // Todo Button add story
        Button(
            onClick =
            {
                val intent = Intent(mContext, UploadStoryActivity::class.java)
                mContext.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Add My Story", textAlign = TextAlign.Center)
        }
        // Todo User Story
    }
}