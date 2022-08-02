package cl.armin20.cryptolist2

import android.annotation.SuppressLint
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
import cl.armin20.cryptolist2.data.datastore.WelcomeScreen
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


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun CryptoListApp() {
    val navController = rememberNavController()

    //Ocurre un problema al condicionar startDestination al momento de rotar pantalla
//    //Recordar es el estado de esta variable, de lo contrario se recompone a su valor base ""
//    val userName= remember { mutableStateOf("") }
//
//    val getUserName = getUserName("user_name", CryptoList2Application.getAppContext())
//    MainScope().launch (Dispatchers.Main){
//        userName.value = getUserName.first()
//        Log.d("test2", userName.value)
//
//    }
//
//    fun checkPrefs(): String{
//        Log.d("test22", userName.value)
//        return if (userName.value.isEmpty()){
//            "addUser"
//        } else "cryptocoins"
//    }

    NavHost(navController, startDestination = "cryptocoins" ) {

        composable(route = "addUser"){
            WelcomeScreen {
                navController.navigate("cryptocoins")
            }
        }

        composable(route = "cryptocoins") {
            CryptoScreen {
                navController.navigate(it)
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
