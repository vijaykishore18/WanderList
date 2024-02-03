package com.example.wanderlist

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.wanderlist.database.RandomDatabase
import com.example.wanderlist.databinding.FragmentHomeBinding
import com.example.wanderlist.recyclerview.ItemAdapter
import com.example.wanderlist.viewmodel.RandomDataViewModel
import com.example.wanderlist.viewmodel.RandomViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel : DataViewModel
    private lateinit var dViewModel: RandomDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]

        val application = requireNotNull(this.activity).application
        val dataSource = RandomDatabase.getInstance(application).formDatabaseDAO
        val viewModelFactory = RandomViewModelFactory(dataSource,application)
        dViewModel = ViewModelProvider(requireActivity(),viewModelFactory)[RandomDataViewModel::class.java]

        if (isInternetAvailable()) {
            lifecycleScope.launch {
                viewModel.getUserData(dViewModel)
                viewModel.getWeatherData("chennai")
                updateUI()
            }
        } else {
            findNavController().navigate(R.id.action_homeFragment_to_networkErrorFragment)
        }

        return binding.root
    }

    private fun updateUI() {
        binding.tempHome.text = viewModel.temp
        binding.placeHome.text = viewModel.location
        binding.climateHome.text = viewModel.climate
        binding.weatherIcon.load(viewModel.icon)
        init()
    }

    private fun init() {

        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        itemAdapter = ItemAdapter(viewModel.displayUser,width)
        recyclerView.adapter = itemAdapter
        itemAdapter.onItemClick = {
            viewModel.userDetailsForFragment = it
            findNavController().navigate(R.id.action_homeFragment_to_userFragment)
        }

        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val a = viewModel.filterList(newText)
                itemAdapter.setFilteredList(a)
                return true
            }
        })

        itemAdapter.notifyDataSetChanged()
    }

    @SuppressLint("ServiceCast")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}