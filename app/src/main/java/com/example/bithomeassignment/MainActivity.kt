package com.example.bithomeassignment

import NetworkManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bithomeassignment.repository.DataRepository
import com.example.bithomeassignment.repository.SettingsRepository
import com.example.bithomeassignment.view_model.AppViewModelFactory
import com.example.bithomeassignment.view_model.MovieListViewModel


/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var _dataRepository: DataRepository
    private lateinit var _settingsRepository: SettingsRepository
    private lateinit var _movieListViewModel: MovieListViewModel
    private lateinit var _navigationController: NavController
    private lateinit var _connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAppSettings()

    }

    // Initialization of the Application settings
    private fun initAppSettings() {
        _settingsRepository = SettingsRepository(this)
        _dataRepository = DataRepository(this, _settingsRepository, NetworkManager())
        val factory = AppViewModelFactory(_dataRepository, _settingsRepository)
        _movieListViewModel = ViewModelProvider(this,
            (factory as ViewModelProvider.Factory))[MovieListViewModel::class.java]
        _connectionLiveData = ConnectionLiveData(this)
        _connectionLiveData.observe(this) { hasInternet ->
            _movieListViewModel.setNetworkState(hasInternet)
        }
        initNavController()
    }

    // Initialization of the navController
    private fun initNavController() {
        _navigationController =
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
    }

    // Function to get viewModel to whole over the app with one instance
    fun getMovieListViewModel(): MovieListViewModel {
        return _movieListViewModel
    }

    // Function to get settingsRepository to whole over the app with one instance
    fun getSettingsRepository(): SettingsRepository {
        return _settingsRepository
    }

    // Function to add fragment with or without bundle
    fun addFragment(navResId: Int, bundle: Bundle?) {
        try {
            _navigationController.navigate(navResId, bundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}