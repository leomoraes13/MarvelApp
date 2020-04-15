package com.leomoraes.marvelapp.network

import com.leomoraes.marvelapp.data.model.HeroDataWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    fun getCharacters(@Query("limit") pageSize: Int, @Query("offset") firstItem: Int): Single<HeroDataWrapper>

    @GET("/v1/public/characters/{charId}")
    fun getCharacterById(@Path("charId") id: String): Single<HeroDataWrapper>
}