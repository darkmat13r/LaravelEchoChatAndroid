package com.darkmat13r.samplechatdemo.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<out T> (
    @SerializedName("error")
    val error: Boolean = false,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("result")
    val result: T? = null
)
