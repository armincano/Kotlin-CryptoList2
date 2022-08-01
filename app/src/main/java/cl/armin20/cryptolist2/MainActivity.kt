package cl.armin20.cryptolist2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.armin20.cryptolist2.ui.CryptoDetailsScreen
import cl.armin20.cryptolist2.ui.CryptoScreen
import cl.armin20.cryptolist2.ui.theme.CryptoList2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoList2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CryptoListApp()
                }
            }
        }
    }
}


@Composable
private fun CryptoListApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "cryptocoins") {

        composable(route = "cryptocoins") {
            CryptoScreen { id ->
                navController.navigate("cryptocoins/$id")
            }
        }

        composable(
            route = "cryptocoins/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { CryptoDetailsScreen() }
    }
}