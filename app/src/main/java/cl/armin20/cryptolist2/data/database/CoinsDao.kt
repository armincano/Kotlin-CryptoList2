package cl.armin20.cryptolist2.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.armin20.cryptolist2.data.model.CoinDetailItem
import cl.armin20.cryptolist2.data.model.Coins

@Dao
interface CoinsDao {
    @Query("SELECT * FROM coins")
    suspend fun getAll(): Coins

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(coins: Coins)

    @Query("SELECT * FROM coin_detail_item where coinId = :coinId")
    suspend fun getSingle(coinId: String): CoinDetailItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSingle(coin_detail: CoinDetailItem)
}