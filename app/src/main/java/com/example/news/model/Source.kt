package com.example.news.model


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    var id: Any = Any(),
    @SerializedName("name")
    var name: String = ""
)