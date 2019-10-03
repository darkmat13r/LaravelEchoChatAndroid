package com.darkmat13r.samplechatdemo.data

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("from_id")
    var fromId:Int?=null,
    @SerializedName("to_id")
    var toId:Int?=null,
    @SerializedName("id")
    var id:Int?=null
)