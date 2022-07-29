package cl.armin20.cryptolist2.data.model

data class CoinItem(
   val data: List<Data>,
   val timestamp: Long
)

data class Data(
val id: String,
val symbol: String,
val name: String,
val priceUsd: Float,
val supply:Float,
val marketCapUsd:Float,
)