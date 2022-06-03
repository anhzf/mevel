package com.bangkit.motravel.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonElevation
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import com.bangkit.motravel.ui.detail.DetailImagePreview
import com.bangkit.motravel.ui.home.dummyImageLarge

@Composable
fun TitleText(text: String){
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = Bold
    )
}

@Composable
fun SubTitleText(text: String){
    Text(
        text = text,
        fontSize = 14.sp,
    )
}

@Composable
fun LocationText(text: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.padding(end = 4.dp),
        )
        SubTitleText(text = text)
    }
}

@ExperimentalMaterial3Api
@Composable
fun RatingCard(
    starRating: Float,
    reviewCount: Int,
    totalVisit: Int,
    modifier: Modifier
){
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(Primary90)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Rating
                Text(
                    text = starRating.toString(),
                    fontSize = 36.sp,
                    color = Primary40
                )
                Row {
                    for (i in 1..starRating.toInt()){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Primary40
                        )
                    }
                }
                Text(
                    text = reviewCount.toString() + "ulasan",
                    fontSize = 12.sp,
                    color = Neutral30
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Total Visit
                Text(
                    text = totalVisit.toString(),
                    fontSize = 36.sp,
                    color = Primary40
                )
                Text(
                    text = "Kunjungan",
                    fontSize = 16.sp,
                    color = Primary40
                )
                Text(
                    text = "/hari",
                    fontSize = 12.sp,
                    color = Neutral30
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ){
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(1f)
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun ImageCardWithText(
    painter: Painter,
    contentDescription: String,
    title: String,
    titleFontSize: Int = 14,
    subTitle: String,
    subTitleFontSize: Int = 10,
    modifier: Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            modifier = Modifier.height(330.dp),
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Neutral10
                            ),
                            startY = 100f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ){
                Column {
                    Text(
                        text = title,
                        style = TextStyle(color = Neutral100),
                        fontSize = titleFontSize.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subTitle,
                        style = TextStyle(color = Neutral100),
                        fontSize = subTitleFontSize.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SmallCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    titleFontSize: Int = 14,
    subTitle: String,
    subTitleFontSize: Int = 10,
    modifier: Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Box(modifier = modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(130.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = titleFontSize.sp,
                        style = TextStyle(color = Primary10)
                    )
                    Text(
                        text = subTitle,
                        fontSize = subTitleFontSize.sp
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Button(
    icon: ImageVector,
    text: String,
    fontSize: Int = 12,
    contentDescription: String,
    modifier: Modifier,
){
    Button(
        onClick = {  },
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        elevation = buttonElevation(defaultElevation = 3.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(1f)
        ){
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.padding(start = 16.dp, end = 4.dp))

            Text(text = text, fontSize = fontSize.sp)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ButtonWithPainter(
    iconId: Int,
    text: String,
    fontSize: Int = 12,
    contentDescription: String,
    modifier: Modifier,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        elevation = buttonElevation(defaultElevation = 3.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(1f)
        ){
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = contentDescription,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                tint = LocalContentColor.current
            )

            Text(text = text, fontSize = fontSize.sp)
        }
    }
}



