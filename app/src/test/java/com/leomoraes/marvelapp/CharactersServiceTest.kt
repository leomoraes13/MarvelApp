package com.leomoraes.marvelapp

import com.google.gson.GsonBuilder
import com.leomoraes.marvelapp.data.repository.CharRepositoryImpl
import com.leomoraes.marvelapp.network.MarvelApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharactersServiceTest : BaseUnitTest() {

    private lateinit var charService: CharRepositoryImpl

    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(DEFAULT_PORT)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create(gson)
            )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        val service = retrofit.create(MarvelApi::class.java)
        charService = CharRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun shouldReturnSuccessWhenCallGetAllChars() {
        initServer()
        val testObserver = charService.getAllChars(0, 20).test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun shouldReturnSuccessWhenCallGetCharById() {
        initServer()
        val id: String = "1011334"
        val testObserver = charService.getCharById(id).test()

        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    private fun initServer() {
        val mockResponse =
            MockResponse().setResponseCode(200).setBody(getJson("mock_json/getChars.json"))
        mockServer.enqueue(mockResponse)
    }
}