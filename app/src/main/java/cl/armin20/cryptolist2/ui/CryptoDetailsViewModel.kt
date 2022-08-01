package cl.armin20.cryptolist2.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist2.CryptoList2Application
import cl.armin20.cryptolist2.NetworkPing
import cl.armin20.cryptolist2.data.database.CoinsDb
import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Data
import cl.armin20.cryptolist2.data.remote.CoincapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private var service = CoincapRetrofitClient.retrofitInstance()

    private var coinsDao = CoinsDb.getDaoInstance(CryptoList2Application.getAppContext())

    //value holder whose reads and writes are observed by Compose
    val cryptoDetail = mutableStateOf(CoinDetailItem("0",Data("offline","OFFLINE","Connect to the internet",0f,0f,0f,"offline"),0))

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
        val id = stateHandle.get<String>("id") ?: ""
        Log.e("LIST1",id)//Llega
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (NetworkPing.getStatus("https://www.google.com/")) {
                    val detailCoins = service.getSingleDetail(id)
                    detailCoins.coinId = id
                    Log.d("LISTDETAILS", detailCoins.toString())
                    coinsDao.addSingle(detailCoins)
                    cryptoDetail.value = coinsDao.getSingle(id)//To always return the coins from Room DB
                }
//                cryptoDetail.value = service.getSingleDetail(id)//To always return the detailcoins from Room DB
            }catch (e: Exception){
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        Log.d("EXCEPTION", "there is an exception: $e")
//                            Toast.makeText(,"No hay datos",Toast.LENGTH_SHORT).show()
                    }
                    else -> throw e
                }
            }

        }
    }
}

