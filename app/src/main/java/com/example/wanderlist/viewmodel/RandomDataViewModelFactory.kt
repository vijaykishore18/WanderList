package com.example.wanderlist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanderlist.database.RandomDAO

class RandomViewModelFactory(private val dataSource: RandomDAO, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RandomDataViewModel::class.java)){
            return RandomDataViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown viewmodel")
    }
}