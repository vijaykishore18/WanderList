package com.example.wanderlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RandomEntity::class], version = 1, exportSchema = false)

abstract class RandomDatabase : RoomDatabase(){
    abstract val formDatabaseDAO: RandomDAO
    companion object{
        @Volatile
        private var INSTANCE: RandomDatabase? = null

        fun getInstance(context: Context): RandomDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RandomDatabase::class.java,
                    name = "user_ids"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}
