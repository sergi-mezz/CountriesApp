package com.mezzyservices.countriesapp.data

data class Country(
    val capital: List<String>,
    val flags: Flags,
    val name: Name,
    val population: Int,
    val region: String,
    val status: String,
)