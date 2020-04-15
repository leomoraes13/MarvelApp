package com.leomoraes.marvelapp.data.model

data class HeroDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Hero>
)