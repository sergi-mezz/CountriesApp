package com.mezzyservices.countriesapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object CountryApiClient {

    private var retrofit: Retrofit = getClient()

    private fun getClient(): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val countryService = retrofit.create(CountryApiInterface::class.java)
}