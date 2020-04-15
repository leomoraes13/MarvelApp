package com.leomoraes.marvelapp

import java.io.File

const val BASE_URL = "http://localhost:8080/"
const val DEFAULT_PORT = 8080

abstract class BaseUnitTest {

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}