package com.example.wanderlist

import WeatherAPIInstance
import WeatherAPIService
import androidx.lifecycle.ViewModel
import com.example.wanderlist.retrofit.user.Result
import com.example.wanderlist.retrofit.user.UserAPIInstance
import com.example.wanderlist.retrofit.user.UserServiceAPI
import com.example.wanderlist.retrofit.weather.AirQuality
import com.example.wanderlist.viewmodel.RandomDataViewModel
import com.google.gson.Gson
import java.io.IOException

class DataViewModel : ViewModel() {

    var displayUser = ArrayList<Result>()
    lateinit var userDetailsForFragment: Result
    var temp : String = ""
    var location : String = ""
    var climate : String = ""
    var icon : String = ""
    lateinit var airQuality : AirQuality


    suspend fun getUserData(dViewModel: RandomDataViewModel) {

        var response =try{
            UserAPIInstance.getRetrofitInstance().create(UserServiceAPI::class.java).getData()}
        catch (e: IOException) {
            val allDatas = dViewModel.database.getData()
            print(allDatas)
            val st = Gson().fromJson(allDatas, DataBaseList::class.java)!!
            for (i in st.data){
                displayUser.add(i)
            }
            print(st::class.java)
            println("display user list $displayUser")
            return
        }

        if (response.isSuccessful) {
            println("data received")
            var data = response.body()
            println(data)
            if (data != null) {
                println("Successful")
                val a = response.body()!!

                a.results.forEach {
                    displayUser.add(it)
                }
                dViewModel.insertData(Gson().toJson(DataBaseList(displayUser)))
            }
        } else {
            println("Not Successful")
        }
    }

    suspend fun getWeatherData( s :  String) {
        var response =
            WeatherAPIInstance.getRetrofitInstance().create(WeatherAPIService::class.java).getWeather(s)

        if (response.isSuccessful) {
            println("data received")
            var data = response.body()
            println(data)
            if (data != null) {
                println("Successful")
                println(response.body())
                val a = response.body()!!
                location = response.body()!!.location.name
                temp = response.body()!!.current.tempC.toString() + " °"
                climate = response.body()!!.current.condition.text
                icon = "https:"+response.body()!!.current.condition.icon
                airQuality = response.body()!!.current.airQuality.copy()
            }
        } else {
            println("Not Successful")
        }
    }

    fun filterList(query : String?): ArrayList<Result> {
        val filteredList = ArrayList<Result>()
        print(displayUser)
        if(query!=null){
            for(i in displayUser){
                if ((i.name.first+i.name.last).contains(query,ignoreCase = true)) {
                    if (!filteredList.contains(i)){
                        filteredList.add(i)
                    }
                }
            }
        }
        if(query == "") return displayUser
        return filteredList
    }
}
