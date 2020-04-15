package com.leomoraes.marvelapp.di

import android.content.Context
import com.leomoraes.marvelapp.data.repository.CharRepository
import com.leomoraes.marvelapp.data.repository.CharRepositoryImpl
import com.leomoraes.marvelapp.network.MarvelApi
import com.leomoraes.marvelapp.network.buildApi
import com.leomoraes.marvelapp.network.provideOkHttpClient
import com.leomoraes.marvelapp.presentation.viewmodels.DetailsViewModel
import com.leomoraes.marvelapp.presentation.viewmodels.MainViewModel
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {
    factory { buildApi(MarvelApi::class.java, get()) }
    single { provideOkHttpClient() }
}

val repositoryModule = module {
    single { CharRepositoryImpl(get()) as CharRepository }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), AndroidSchedulers.mainThread()) }
    viewModel { DetailsViewModel(get(), AndroidSchedulers.mainThread()) }
}

val imageModule = module {
    single { providePicasso(androidContext(), okHttp3Downloader(get())) }
}

fun providePicasso(context: Context, downloader: OkHttp3Downloader): Picasso =
    Picasso.Builder(context)
        .downloader(downloader)
        .build()

fun okHttp3Downloader(client: OkHttpClient) = OkHttp3Downloader(client)