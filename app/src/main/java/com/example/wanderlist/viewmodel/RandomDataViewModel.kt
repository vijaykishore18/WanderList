package com.example.wanderlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.wanderlist.database.RandomDAO
import com.example.wanderlist.database.RandomEntity

class RandomDataViewModel(val database: RandomDAO, application: Application): ViewModel(){

    fun clearData(){
        database.clear()
    }

    fun insertData(result: String){
        database.insert(RandomEntity(data = result))
    }
}

