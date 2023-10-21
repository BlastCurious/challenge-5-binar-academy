package com.example.challenge_4_ilyasa_adam_naufal.dataClass


import com.google.gson.annotations.SerializedName

data class CategoryMenu(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataCategory>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)