package com.bangkit.motravel.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.motravel.BuildConfig
import com.bangkit.motravel.R
import com.bangkit.motravel.ui.account.AccountScreen
import com.bangkit.motravel.ui.agenda.AgendaScreen
import com.bangkit.motravel.ui.home.HomePopularity
import com.bangkit.motravel.ui.home.HomeRecentlyAdded
import com.bangkit.motravel.ui.home.HomeRecommendation
import com.bangkit.motravel.ui.login.LoginActivity
import com.bangkit.motravel.ui.places.PlacesAutoCompleteActivity
import com.bangkit.motravel.ui.story.StoryScreen
import com.bangkit.motravel.ui.theme.MotravelTheme
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private val mainViewModel: MainViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
        auth.useEmulator("192.168.1.7", 9099)

        val apiKey = BuildConfig.MAPS_API_KEY
        if (apiKey.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show()
            return
        }
        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        setContent {
            MotravelTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopNavigationBar(title = "Home", firebaseAuth = auth, activity = this) },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            BottomNavigation(navController = navController, mainViewModel = mainViewModel) }
                        },
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
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BottomNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel
){
    val state = rememberScrollState()

    NavHost(navController = navController, startDestination = "home"){
         composable("home"){
             Column(modifier = Modifier
                 .verticalScroll(state = state)
                 .padding(top = 80.dp)
             ) {
                 HomeRecommendation()
                 HomePopularity()
                 HomeRecentlyAdded()
             }
         }
        composable("agenda"){
            Column(modifier = Modifier
                .padding(top = 80.dp)
            ) {
                AgendaScreen()
            }

        }
        composable("story"){
            Column(modifier = Modifier
                .padding(top = 80.dp)
            ) {
                StoryScreen()
            }
        }
        composable("account"){
            Column(modifier = Modifier
                .padding(top = 80.dp)
            ) {
                AccountScreen(mainViewModel)
            }
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

@ExperimentalMaterial3Api
@Composable
// Top AppBar
fun TopNavigationBar(
    title: String,
    firebaseAuth: FirebaseAuth,
    activity: MainActivity
){
    var expanded by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    SmallTopAppBar(
        title =  { Text(title) },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                Box(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Add New Location") },
                            onClick = {
                                val intent = Intent(mContext, PlacesAutoCompleteActivity::class.java)
                                mContext.startActivity(intent)},
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.LocationOn,
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = null
                                )
                            })
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text("Log out") },
                            onClick = {
                                firebaseAuth.signOut()
                                val intent = Intent(mContext, LoginActivity::class.java)
                                mContext.startActivity(intent)
                                activity.finish() },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = null
                                )
                            })
                    }
                }
            }
        }
    )
}


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)