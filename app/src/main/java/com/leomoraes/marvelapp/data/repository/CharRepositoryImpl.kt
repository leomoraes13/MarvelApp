package com.leomoraes.marvelapp.data.repository

import com.leomoraes.marvelapp.data.model.HeroDataWrapper
import com.leomoraes.marvelapp.network.MarvelApi
import io.reactivex.Single

interface CharRepository {
    fun getAllChars(page: Int, pageSize: Int): Single<HeroDataWrapper>
    fun getCharById(id: String): Single<HeroDataWrapper>
}

class CharRepositoryImpl(private val api: MarvelApi) : CharRepository {
    override fun getAllChars(page: Int, pageSize: Int): Single<HeroDataWrapper> {
        return api.getCharacters(pageSize, page * pageSize)
    }

    override fun getCharById(id: String): Single<HeroDataWrapper> {
        return api.getCharacterById(id)
    }
}