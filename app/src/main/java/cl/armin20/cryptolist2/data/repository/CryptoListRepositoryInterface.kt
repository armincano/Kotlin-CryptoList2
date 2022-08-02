package cl.armin20.cryptolist2.data.repository

import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Coins

interface CryptoListRepositoryInterface {
    suspend fun getAll():Coins
    suspend fun getSingle(id: String): CoinDetailItem
}