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
import cl.armin20.cryptolist2.data.model.Coins
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

//stateHandle guarda el estado, mantien la posición de scrolling y evita el system initiated process death.
//The Navigation component, behind the scenes, saves the navigation arguments stored in NavStackEntry
class CryptoViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private val cryptoListRepository: CryptoListRepositoryInterface = CryptoListRepository.get(
        CryptoList2Application.getAppContext() as Application
    )

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
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            val fromRepositoryAll = cryptoListRepository.getAll()
            withContext(Dispatchers.Main){//Recuerda que la UI se trabaja en Main
                cryptoList.value = fromRepositoryAll
            }
        }
    }

}