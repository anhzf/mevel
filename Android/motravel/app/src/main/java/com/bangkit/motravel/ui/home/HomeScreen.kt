package com.bangkit.motravel.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.motravel.R
import com.bangkit.motravel.ui.detail.DetailActivity
import com.bangkit.motravel.ui.theme.*
import kotlinx.parcelize.Parcelize


@ExperimentalMaterial3Api
@Composable
fun HomeRecommendation(){
    val largeImage = dummyImageLarge()
    val smallImage = dummyImageSmall()
    val buttonState = rememberScrollState()
    val mContext = LocalContext.current

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp
        )
    ) {
        // Title
        TitleText(text = "Rekomendasi yang kamu banget")
        LocationText(text = "Nusa Tenggara Timur")

        // Large Image
        ImageCardWithText(
            painter = painterResource(id = largeImage.idImage),
            title = largeImage.title,
            titleFontSize = 18,
            subTitle = largeImage.subTitle,
            subTitleFontSize = 16,
            contentDescription = largeImage.contentDescription,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // Small Image
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {

            smallImage.forEachIndexed { index, it ->
                if (index > 2) return@forEachIndexed
                ImageCardWithText(
                    painter =  painterResource(id = it.idImage),
                    title = it.title,
                    subTitle = it.subTitle,
                    contentDescription = it.contentDescription,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .weight(1f)
                        .clickable {
                            val intent = Intent(mContext,DetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("Data",DataImage(
                                it.idImage,
                                it.title,
                                it.subTitle,
                                it.contentDescription
                            ))
                            intent.putExtra("DataBundle", bundle)
                            mContext.startActivity(intent)
                        }
                )
            }
        }
        // Button With Horizontal Scroll
        Row( modifier = Modifier
            .horizontalScroll(state = buttonState)
            .padding(vertical = 8.dp)
        ){
            Button(
                icon = Icons.Default.Home,
                text = "Lihat Lebih Banyak",
                contentDescription = "Button Description",
                modifier = Modifier
                    .width(180.dp)
                    .height(40.dp)
                    .padding(end = 5.dp)
            )
            Button(
                icon = Icons.Default.Home,
                text = "Saya Kurang Cocok",
                contentDescription = "Saya Kurang Cocok",
                modifier = Modifier
                    .width(180.dp)
                    .height(40.dp)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomePopularity(){
    val smallImage = dummyImageSmall()
    val state = rememberLazyListState()
    val mContext = LocalContext.current

    Column(modifier = Modifier
        .padding(
            start = 16.dp,
            top = 16.dp    
        )
    ) {
        // Title
        TitleText(text = "Paling Populer se-Indonesia")
        SubTitleText(text = "Bulan ini")

        // Horizontal Scroll Image
        LazyRow(
            userScrollEnabled = true,
            state = state,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            item {
                smallImage.forEach {
                    ImageCardWithText(
                        painter =  painterResource(id = it.idImage),
                        title = it.title,
                        subTitle = it.subTitle,
                        contentDescription = it.contentDescription,
                        modifier = Modifier
                            .width(136.dp)
                            .height(192.dp)
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clickable {
                                val intent = Intent(mContext,DetailActivity::class.java)
                                val bundle = Bundle()
                                bundle.putParcelable("Data",DataImage(
                                    it.idImage,
                                    it.title,
                                    it.subTitle,
                                    it.contentDescription
                                ))
                                intent.putExtra("DataBundle", bundle)
                                mContext.startActivity(intent)
                            }
                    )
                }
            }
        }

        // Button
        Button(
            icon = Icons.Default.Home,
            text = "Lihat lebih Banyak",
            contentDescription = "Lihat lebih banyak",
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeRecentlyAdded(){

    val smallImage = dummyImageSmall()
    val mContext = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)
    ){
        // Title
        TitleText(text = "Baru Ditambahkan")
        SubTitleText(text = "Sekitar kamu")

        // Card List
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = spacedBy(8.dp)
        ){
            smallImage.forEach{
                SmallCard(
                    painter =  painterResource(id = it.idImage),
                    title = it.title,
                    subTitle = it.subTitle,
                    contentDescription = it.contentDescription,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            val intent = Intent(mContext,DetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("Data",DataImage(
                                it.idImage,
                                it.title,
                                it.subTitle,
                                it.contentDescription
                            ))
                            intent.putExtra("DataBundle", bundle)
                            mContext.startActivity(intent)
                        }
                )
            }
        }
        // Button
        Button(
            icon = Icons.Default.Home,
            text = "Lihat lebih Banyak",
            contentDescription = "Lihat lebih banyak",
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
        )
    }
}

@Parcelize
data class DataImage(
    val idImage: Int,
    val title: String,
    val subTitle: String,
    val contentDescription : String
): Parcelable

fun dummyImageLarge(): DataImage{
    return DataImage(
        R.drawable.dummy_img,
        "Labuhan Bajo",
        "Manggarai Barat",
        "Labuhan Bajo",
    )
}

fun dummyImageSmall(): List<DataImage>{
    return listOf(
        DataImage(
            R.drawable.dummy_img,
            "Pulau Komodo",
            "Manggarai Barat",
            "Pulau Komodo",
        ),
        DataImage(
            R.drawable.dummy_img,
            "Pulau Kanawa",
            "Manggarai Barat",
            "Pulau Kanawa",
        ),
        DataImage(
            R.drawable.dummy_img,
            "Taman Nasional Manupeu Tanah",
            "Sumba",
            "Taman Nasional Manupeu Tanah",
        ),
        DataImage(
            R.drawable.dummy_img,
            "Taman Nasional Manupeu Tanah",
            "Sumba",
            "Taman Nasional Manupeu Tanah",
        ),
        DataImage(
            R.drawable.dummy_img,
            "Taman Nasional Manupeu Tanah",
            "Sumba",
            "Taman Nasional Manupeu Tanah",
        )
    )
}