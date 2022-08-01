package cl.armin20.cryptolist2.ui

import android.app.Application
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
import cl.armin20.cryptolist2.data.repository.CryptoListRepository
import cl.armin20.cryptolist2.data.repository.CryptoListRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )
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
        getDetailCoin(id)
    }

    fun getDetailCoin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fromRepositorySingle = cryptoListRepository.getSingle(id)
            withContext(Dispatchers.Main){//Recuerda que la UI se trabaja en Main
                cryptoDetail.value = fromRepositorySingle
            }
        }
    }
}

