package cl.armin20.cryptolist2.data.remote


import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Coins
import retrofit2.http.GET
import retrofit2.http.Path

interface CoincapApi {
    @GET("assets/")
    suspend fun getAllCoinsPrices(): Coins

    @GET("assets/{id}/")
    suspend fun getSingleDetail(@Path("id") id: String): CoinDetailItem

}