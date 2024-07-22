package ru.easycode.zerotoheroandroidtdd.data.cloud

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("text")
    val text : String
)