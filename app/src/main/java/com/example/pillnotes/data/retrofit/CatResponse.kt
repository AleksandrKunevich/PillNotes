package com.example.pillnotes.data.retrofit

import com.google.gson.annotations.SerializedName

class CatResponse : ArrayList<CatItem>()

data class CatItem(
    @SerializedName("breeds")
    val breeds: List<Any>,

    @SerializedName("height")
    val height: Int,

    @SerializedName("id")
    val id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: Int
)