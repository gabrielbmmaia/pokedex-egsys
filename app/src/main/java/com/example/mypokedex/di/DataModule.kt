package com.example.mypokedex.di

import android.util.Log
import com.example.mypokedex.core.BASE_URL
import com.example.mypokedex.core.OK_HTTP
import com.example.mypokedex.data.networking.PokemonServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun load() {
        loadKoinModules(networkModule())
    }

    private fun networkModule(): Module {
        return module {

            single<PokemonServices> { createService(client = get()) }

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
        }
    }

    private fun createService(
        client: OkHttpClient
    ): PokemonServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonServices::class.java)
    }
}