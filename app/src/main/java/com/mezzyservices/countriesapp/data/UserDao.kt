package com.mezzyservices.countriesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<User>

    @Insert
    suspend fun saveUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}