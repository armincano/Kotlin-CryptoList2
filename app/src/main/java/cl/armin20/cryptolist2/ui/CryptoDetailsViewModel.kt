package cl.armin20.cryptolist2.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Data
import cl.armin20.cryptolist2.data.remote.CoincapRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CryptoDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {

    private val service = CoincapRetrofitClient.retrofitInstance()
    //    var ejemplo:CoinItem? = null
    //value holder whose reads and writes are observed by Compose
    val cryptoDetail = mutableStateOf(CoinDetailItem(Data("","","",1f,1f,1f),1))

    fun getDateTime(s: Long): String {
        return try {
            val sdf = SimpleDateFormat("HH:mm:ss")//"dd MMMM yyyy, HH:mm:ss"
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
            cryptoDetail.value = service.getSingleDetail(id)
            Log.e("LIST1",cryptoDetail.toString())
        }
    }
}