package com.example.wanderlist.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RandomDAO {
    @Upsert
    fun insert(userData: RandomEntity)

    @Query("SELECT data  FROM random")
    fun getData() : String

    @Query("DELETE FROM random")
    fun clear()
}