package com.bangkit.motravel.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.motravel.ui.home.dummyImageLarge
import com.bangkit.motravel.ui.home.dummyImageSmall
import com.bangkit.motravel.ui.theme.ImageCard
import com.bangkit.motravel.ui.theme.MotravelTheme
import com.bangkit.motravel.ui.theme.RatingCard

@ExperimentalMaterial3Api
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MotravelTheme{
                DetailImagePreview()
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailImagePreview(){

    val dataImageLg = dummyImageLarge()
    val dataImageSm = dummyImageSmall()
    val state = rememberLazyListState()
    val verticalScrollState = rememberScrollState()

    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .verticalScroll(verticalScrollState)
    ){
        ImageCard(
            painter = painterResource(id = dataImageLg.idImage),
            contentDescription = dataImageLg.contentDescription,
            modifier = Modifier
                .height(330.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        LazyRow (
            userScrollEnabled = true,
            state = state,
            modifier = Modifier.padding(start = 16.dp,top = 8.dp)
        ){
            item {
                dataImageSm.forEach {
                    ImageCard(
                        painter = painterResource(id = it.idImage),
                        contentDescription = it.contentDescription,
                        modifier = Modifier
                            .width(100.dp)
                            .height(120.dp)
                            .padding(end = 8.dp)
                            .weight(1f)
                    )
                }
            }
        }
        RatingCard(
            4.8f,
            1231,
            17000,
            Modifier
                .padding(horizontal = 16.dp,vertical = 8.dp)
        )
        DetailNavigationBar(
            items = listOf(
                DetailNavItems(
                    name = "Ikhtisar",
                    route = "ikhtisar",
                ),
                DetailNavItems(
                    name = "Barengan",
                    route = "barengan",
                ),
                DetailNavItems(
                    name = "Pemandu Wisata",
                    route = "pemandu wisata",
                ),
                DetailNavItems(
                    name = "Cerita",
                    route = "cerita",
                )
            ),
            navController = navController,
            onItemClick = {navController.navigate(it.route){popUpTo(0)}},
            modifier = Modifier.padding(start = 16.dp)
        )
        DetailNavigation(navController = navController)
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailNavigationBar(
    items: List<DetailNavItems>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (DetailNavItems) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    val state = rememberScrollState()
    Row (
        modifier = modifier.horizontalScroll(state),
        horizontalArrangement = Arrangement.Start
    ) {
        items.forEach { navItem ->
            val selected = navItem.route == backStackEntry.value?.destination?.route
            FilterChip(
                selected = selected,
                onClick = { onItemClick(navItem)},
                label = { Text(text = navItem.name)},
                shape = RoundedCornerShape(15.dp),
                border = FilterChipDefaults.filterChipBorder(Color.Transparent),
                modifier = modifier.padding(end = 8.dp)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailNavigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "ikhtisar"){
        composable("ikhtisar"){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Ikhtisar Page")
            }
        }
        composable("barengan"){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "barengan Page")
            }
        }
        composable("pemandu wisata"){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "pemandu wisata Page")
            }
        }
        composable("cerita"){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "cerita Page")
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DetailDescription(
    detailItems: DetailNavItems,
){
    val dataDummy = dummyDetailData()
    Column {
        Text(text = "Lokasi")
        Text(text = dataDummy.address)
        Text(text = "Wajib Dicoba")
    }
}

// Testing Component
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun RatingPreview(){
    MotravelTheme {
        Column {
            DetailImagePreview()
        }
    }
}

data class DetailNavItems(
    val name: String,
    val route: String
)

data class DetailItems(
    val address: String,
    val activity: List<String>,
    val priceMin: Int,
    val priceMax: Int,
    val description: String,
    val keyword: List<String>
)

fun dummyDetailData(): DetailItems{
    return DetailItems(
        address = "Manggarai Barat, Nusa Tenggara Timur",
        activity = listOf("Berfoto dengan Komodo","Peach Beach","Kain Songke"),
        priceMin = 2000,
        priceMax = 100000,
        description = "Salah satu 11 tempat wisata paling bagus di Nusa Tenggara Timur sudah banyak diperbincangkan oleh public semenjak lokasi ini dijadikan sebagai lokasi syuting salah satu produk minuman asal Indonesia. Asyiknya lagi, kamu bisa menyelam bersama dengan keluarga Manta di manta Point.\n",
        keyword = listOf("Pantai","Taman Nasional","Oleh-oleh kain")
    )
}
