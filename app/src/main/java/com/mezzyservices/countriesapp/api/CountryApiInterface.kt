package com.mezzyservices.countriesapp.api

import com.mezzyservices.countriesapp.data.Country
import retrofit2.http.GET
import retrofit2.http.Path


interface CountryApiInterface {

    @GET("all")
    suspend fun getCountryList(): List<Country>

    @GET("name/{name}")
    suspend fun getCountry(@Path("name") name: String): List<Country>
}