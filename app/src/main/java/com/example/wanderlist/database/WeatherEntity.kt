package com.example.wanderlist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(true)
    var id : Int  = 0,
    @ColumnInfo("image")
    var image : String = "",
    @ColumnInfo("temp")
    var temp : String = "",
    @ColumnInfo("city")
    var city : String,
    @ColumnInfo("airQuality")
    var airQuality: String = ""
)
