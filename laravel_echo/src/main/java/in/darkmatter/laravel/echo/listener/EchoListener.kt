package `in`.darkmatter.laravel.echo.listener

import `in`.darkmatter.laravel.echo.data.EchoData
import `in`.darkmatter.laravel.echo.util.toJson
import `in`.darkmatter.laravel.echo.util.toObject
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

abstract class EchoListener<T>() {
    companion object{
        private val TAG = EchoListener::class.java.simpleName
    }
    abstract fun onDataReceived(data: T?)
    fun submitData(it: Any) {
        val json = it.toJson()
        val data: EchoData<T> = json.toObject()
        Log.e(TAG, "Echo Data :$data")
        onDataReceived(data.data)
    }

}


inline fun <reified T> genericType() =  object:TypeToken<T>(){}.type