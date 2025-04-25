package com.mezzyservices.countriesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val paternalSurname: String,
    val maternalSurname: String,
    val birthDate: String,
    val country: String
)
