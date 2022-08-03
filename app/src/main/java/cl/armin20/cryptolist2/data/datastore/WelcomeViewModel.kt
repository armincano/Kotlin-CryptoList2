package cl.armin20.cryptolist2.data.datastore

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class WelcomeViewModel:ViewModel() {

    var userName =""

    fun saveUserName(name: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
              writeUserName(name, context )
        }
    }

    fun getName(name: String, context: Context){


        val getUserName = getUserName(name, context)

        viewModelScope.launch (Dispatchers.IO){
            userName = getUserName.first()
            if (userName.isEmpty()){
                saveUserName("a guest", context)
            }
            Log.d("userPrefGetNameVM", userName)

        }

    }

}