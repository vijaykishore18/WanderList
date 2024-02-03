package com.example.wanderlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.wanderlist.databinding.FragmentUserBinding
import kotlinx.coroutines.launch
import kotlin.random.Random


class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding
    lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val random = Random.nextInt(1,11)
        when(random){
            1 -> binding.coverImage.setImageResource(R.drawable.dpbg1)
            2 -> binding.coverImage.setImageResource(R.drawable.dpbg2)
            3 -> binding.coverImage.setImageResource(R.drawable.dpbg3)
            4 -> binding.coverImage.setImageResource(R.drawable.dpbg4)
            5 -> binding.coverImage.setImageResource(R.drawable.dpbg5)
            6 -> binding.coverImage.setImageResource(R.drawable.dpbg6)
            7 -> binding.coverImage.setImageResource(R.drawable.dpbg7)
            8 -> binding.coverImage.setImageResource(R.drawable.dpbg8)
            9 -> binding.coverImage.setImageResource(R.drawable.dpbg9)
            10 -> binding.coverImage.setImageResource(R.drawable.dpbg10)
        }
        var data = viewModel.userDetailsForFragment
        binding.userDp.load(data.picture.large)
        binding.userName.text = data.name.title +". " + data.name.first + " " + data.name.last
        binding.userDob.text = "(" + data.dob.date.substring(0,10) + (")")
        binding.userEmail.text = data.email
        binding.userNumber.text = data.cell
        binding.userCountry.text = data.location.city + ", " + data.location.city + ", " + data.location.country
        binding.weatherTemp.text = viewModel.temp
        binding.userCity.text = data.location.city + ", " + data.location.country

        lifecycleScope.launch {
            viewModel.getWeatherData(data.location.city)
            binding.userClimate.text = viewModel.climate
            binding.userweatherImg.load(viewModel.icon)
            binding.airQuality.text = "Air Quality is " + when (viewModel.airQuality.usEpaIndex) {
                1 -> "Good"
                2 -> "Moderate"
                3 -> "Unhealthy for sensitive people"
                4 -> "Unhealthy"
                5 -> "Very Unhealthy"
                6 -> "Hazardous"
                else -> {
                    "undefined"
                }
            }
        }

        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_userFragment_to_homeFragment)
        }
    }

}