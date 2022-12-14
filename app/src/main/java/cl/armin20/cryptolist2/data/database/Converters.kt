package cl.armin20.cryptolist2.data.database

import androidx.room.TypeConverter
import cl.armin20.cryptolist2.data.model.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toStringList(data: List<Data>?): String =  Gson().toJson(data)

    @TypeConverter
    fun toDataList(data: String): List<Data> {
        val listType = object : TypeToken<List<Data>>() {}.type
        return Gson().fromJson(data, listType)
    }

    ///////////////////////

    @TypeConverter
    fun toDetailStringList(data: Data?): String =  Gson().toJson(data)

    @TypeConverter
    fun toDetailDataList(data: String): Data {
        val listType = object : TypeToken<Data>() {}.type
        return Gson().fromJson(data, listType)
    }

}




