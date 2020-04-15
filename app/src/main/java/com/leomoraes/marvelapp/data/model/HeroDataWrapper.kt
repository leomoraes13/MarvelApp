package com.leomoraes.marvelapp.data.model

data class HeroDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: HeroDataContainer,
    val etag: String
)