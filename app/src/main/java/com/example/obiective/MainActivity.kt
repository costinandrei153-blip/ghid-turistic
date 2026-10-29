package com.example.obiective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.obiective.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialTheme { TourismApp() } }
    }
}

private data class Tab(val label: String, val route: Any, val icon: ImageVector)

private val tabs = listOf(
    Tab("Categorii", CategoriesGraph, Icons.AutoMirrored.Filled.List),
    Tab("Favorite", Favorites, Icons.Filled.Favorite),
    Tab("Despre", About, Icons.Filled.Info)
)

@Composable
fun TourismApp(vm: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    val showBar = destination != null && !destination.hasRoute<Login>()

    Scaffold(
        bottomBar = { if (showBar) BottomBar(navController, destination) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        // NavController apare DOAR aici și în BottomBar; ecranele primesc lambda-uri.
        NavHost(navController, startDestination = Login, modifier = Modifier.padding(padding)) {

            composable<Login> {
                LoginScreen(onLogin = {
                    navController.navigate(CategoriesGraph) {
                        popUpTo<Login> { inclusive = true }   // Back nu mai revine la Login
                    }
                })
            }

            navigation<CategoriesGraph>(startDestination = PlaceList) {
                composable<PlaceList> {
                    val selected by vm.selected.collectAsState()
                    PlaceListScreen(
                        places = PlacesRepository.places.filter { selected == null || it.category == selected },
                        selected = selected,
                        onSelect = vm::select,
                        onNavigateToDetail = { id -> navController.navigate(PlaceDetail(id)) }
                    )
                }
                composable<PlaceDetail> { entry ->
                    val id = entry.toRoute<PlaceDetail>().id       // doar id-ul e transmis
                    val place = PlacesRepository.byId(id) ?: return@composable
                    val favs by vm.favorites.collectAsState()
                    PlaceDetailScreen(
                        place = place,
                        isFavorite = id in favs,
                        onToggleFavorite = { vm.toggleFavorite(id) },
                        onBack = { navController.popBackStack() }  // la fel ca butonul Back de sistem
                    )
                }
            }

            composable<Favorites> {
                val favs by vm.favorites.collectAsState()
                FavoritesScreen(
                    places = PlacesRepository.places.filter { it.id in favs },
                    onRemove = vm::toggleFavorite
                )
            }
            composable<About> { AboutScreen() }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController, destination: NavDestination?) {
    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = destination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true,
                onClick = {
                    navController.navigate(tab.route) {
                        // baza stivei = începutul grafului Categorii (Login a fost deja scos)
                        popUpTo<PlaceList> { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}
