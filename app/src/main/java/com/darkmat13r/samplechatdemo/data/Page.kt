package com.darkmat13r.samplechatdemo.data

import com.google.gson.annotations.SerializedName

data class Page<T>(
        @field:SerializedName("next_page_url")
        val nextPageUrl  : String? = null,
        @field:SerializedName("data")
        var data : List<T>?  = arrayListOf()

)
