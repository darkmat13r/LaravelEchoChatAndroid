package `in`.darkmatter.laravel.echo.data

import com.google.gson.annotations.SerializedName

data class EchoData<T>(
    @SerializedName("nameValuePairs")
    val data: T? = null
)