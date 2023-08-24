package com.minionjerry.android.rijksgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minionjerry.android.rijksgallery.presentation.artobject.detail.ArtObjectDetailScreen
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectListScreen
import com.minionjerry.android.rijksgallery.presentation.navigation.NavRoutes
import com.minionjerry.android.rijksgallery.ui.theme.RijksGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.ArtObjects.route) {
        composable(route = NavRoutes.ArtObjects.route) {
            ArtObjectListScreen(viewModel = hiltViewModel(), navController)
        }
        composable(route = NavRoutes.ArtObject.route, arguments = NavRoutes.ArtObject.arguments) {
            ArtObjectDetailScreen(viewModel = hiltViewModel(), objectNumber = NavRoutes.ArtObject.fromEntry(it))
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RijksGalleryTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    RijksGalleryTheme {
        val navController = rememberNavController()
        App(navController = navController)

    }
}