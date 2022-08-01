package cl.armin20.cryptolist2.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist2.CryptoList2Application
import cl.armin20.cryptolist2.NetworkPing
import cl.armin20.cryptolist2.data.database.CoinsDb
import cl.armin20.cryptolist2.data.model.Coins
import cl.armin20.cryptolist2.data.remote.CoincapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

//stateHandle guarda el estado, mantien la posición de scrolling y evita el system initiated process death.
//The Navigation component, behind the scenes, saves the navigation arguments stored in NavStackEntry
class CryptoViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private var service = CoincapRetrofitClient.retrofitInstance()

    private var coinsDao = CoinsDb.getDaoInstance(CryptoList2Application.getAppContext())



    //value holder whose reads and writes are observed by Compose
    val cryptoList = mutableStateOf(Coins(1, emptyList(),1))

    fun getDateTime(s: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd MMMM, HH:mm:ss")//"dd MMMM yyyy, HH:mm:ss"
            val date = Date(s)
            sdf.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateCoins()
        }
    }

    suspend fun updateCoins(){
        try {
            if (NetworkPing.getStatus("https://www.google.com/")) {
                val coins = service.getAllCoinsPrices()
                Log.d("LISTCOINS", coins.toString())
                coinsDao.addAll(coins)
            }
            cryptoList.value = coinsDao.getAll()//To always return the coins from Room DB
//                cryptoList.value = coins//To always return the coins from Retrofit
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    Log.d("EXCEPTION", "there is an exception: $e")
                }
                else -> throw e
            }
        }
    }

}