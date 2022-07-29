package cl.armin20.cryptolist2.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist2.data.model.CoinItem
import cl.armin20.cryptolist2.data.remote.CoincapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//stateHandle guarda el estado, mantien la posición de scrolling y evita el system initiated process death.
//The Navigation component, behind the scenes, saves the navigation arguments stored in NavStackEntry
class CryptoViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private val service = CoincapRetrofitClient.retrofitInstance()
//    var ejemplo:CoinItem? = null
    //value holder whose reads and writes are observed by Compose
    val cryptoList = mutableStateOf(CoinItem(emptyList(),1))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoList.value = service.getAllCoinsPrices()
            Log.e("LIST1",cryptoList.toString())
        }
    }
}