package com.bangkit.motravel.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.motravel.ui.account.AccountScreen
import com.bangkit.motravel.ui.agenda.AgendaScreen
import com.bangkit.motravel.ui.home.HomePopularity
import com.bangkit.motravel.ui.home.HomeRecentlyAdded
import com.bangkit.motravel.ui.home.HomeRecommendation
import com.bangkit.motravel.ui.story.StoryScreen
import com.bangkit.motravel.ui.theme.MotravelTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotravelTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Agenda",
                                    route = "agenda",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Story",
                                    route = "story",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Account",
                                    route = "account",
                                    icon = Icons.Default.Home
                                )

                            ),
                            navController = navController ,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    BottomNavigation(navController = navController)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavigation(navController: NavHostController){
    val state = rememberScrollState()

    NavHost(navController = navController, startDestination = "home"){
         composable("home"){
             Column(modifier = Modifier
                 .verticalScroll(state = state)
                 .padding(bottom = 100.dp)
             ) {
                 HomeRecommendation()
                 HomePopularity()
                 HomeRecentlyAdded()
             }
         }
        composable("agenda"){
            AgendaScreen()
        }
        composable("story"){
            StoryScreen()
        }
        composable("account"){
            AccountScreen()
        }
    }
}

@Composable
// main bottom navigation bar
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStack = navController.currentBackStackEntryAsState()
    BottomAppBar(
        modifier = modifier
    ){
        items.forEach{ navItem ->
            val selected = navItem.route == backStack.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                alwaysShowLabel = true,
                onClick = { onItemClick(navItem) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.name
                        )
                        Text(
                            text = navItem.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)