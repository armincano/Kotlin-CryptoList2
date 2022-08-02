package cl.armin20.cryptolist2.data.repository

import android.app.Application
import android.util.Log
import cl.armin20.cryptolist2.CryptoList2Application
import cl.armin20.cryptolist2.NetworkPing
import cl.armin20.cryptolist2.data.database.CoinsDb
import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Coins
import cl.armin20.cryptolist2.data.model.Data
import cl.armin20.cryptolist2.data.remote.CoincapRetrofitClient
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class CryptoListRepository(application: Application):CryptoListRepositoryInterface {

    private var coincapApiService = CoincapRetrofitClient.retrofitInstance()
    private var coinsDao = CoinsDb.getDaoInstance(CryptoList2Application.getAppContext())

    override suspend fun getAll(): Coins {
        try {
            if (NetworkPing.getStatus("https://www.google.com/")) {
                val coins = coincapApiService.getAllCoinsPrices()
                Log.d("LISTCOINS", coins.toString())
                coinsDao.addAll(coins)
                return coinsDao.getAll()
            }
        } catch (e: Exception) {
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
        return coinsDao.getAll()
    }



    override suspend fun getSingle(id: String): CoinDetailItem {
        try {
            if (NetworkPing.getStatus("https://www.google.com/")) {
                val detailCoins = coincapApiService.getSingleDetail(id)
                detailCoins.coinId = id
                Log.d("LISTDETAILS", detailCoins.toString())
                coinsDao.addSingle(detailCoins)
                return coinsDao.getSingle(id)//To always return the coins from Room DB
            }
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
        if (coinsDao.getSingle(id)!=null){return coinsDao.getSingle(id)}else
        return CoinDetailItem("0", Data("offline","OFFLINE","Connect to the internet",0f,0f,0f,"offline"),0)
    }

    companion object{
        private var INSTANCE: CryptoListRepository? = null

        fun get(application: Application) : CryptoListRepository {
            if (INSTANCE == null){
                INSTANCE = CryptoListRepository(application)
            }
            return INSTANCE!!
        }
    }

}