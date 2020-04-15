package com.leomoraes.marvelapp.network

import com.leomoraes.marvelapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

const val TIMESTAMP = "ts"
const val APIKEY = "apikey"
const val HASH = "hash"

class MarvelInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val ts = System.currentTimeMillis().toString()
        val publicKey = BuildConfig.PUBLIC_KEY
        val privatekey = BuildConfig.PRIVATE_KEY
        val hash = "$ts$privatekey$publicKey"

        val httpUrl = request.url().newBuilder()
            .addQueryParameter(TIMESTAMP, ts)
            .addQueryParameter(APIKEY, publicKey)
            .addQueryParameter(HASH, md5(hash)).build()

        return chain.proceed(
            request.newBuilder().url(httpUrl).build()
        )
    }

    private fun md5(value: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(value.toByteArray())).toString(16).padStart(32, '0')
    }
}