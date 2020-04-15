package com.leomoraes.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class Hero(
    val id: Long,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: HeroImage
)