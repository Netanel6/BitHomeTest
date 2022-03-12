package com.example.bithomeassignment

import NetworkManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.bithomeassignment.databinding.ActivityMainBinding
import com.example.bithomeassignment.repository.DataRepository
import com.example.bithomeassignment.repository.SettingsRepository
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.view_model.AppViewModelFactory
import com.example.bithomeassignment.view_model.MovieListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val TAG = this::class.java.simpleName
    private lateinit var _binding: ActivityMainBinding
    private var onNavItemSelected: OnNavItemSelected? = null
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var _navigationController: NavController
    private lateinit var _dataRepository: DataRepository
    private lateinit var _settingsRepository: SettingsRepository
    private lateinit var _movieListViewModel: MovieListViewModel
    private lateinit var _connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
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
        bottomNavView = _binding.bottomNavView
        _navigationController =
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        bottomNavView.setupWithNavController(_navigationController)
        bottomNavView.setOnItemSelectedListener(this)
    }

    fun hideBottomView() {
        bottomNavView.visibility = View.GONE
        AppUtils.slideDown(bottomNavView)
    }

    private fun showBottomView() {
        bottomNavView.selectedItemId = R.id.nav_home
        bottomNavView.visibility = View.VISIBLE
        AppUtils.slideUp(bottomNavView)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                onNavItemSelected?.screenNum(1)
                return true
            }
            R.id.nav_top_rated -> {
                onNavItemSelected?.screenNum(2)
                return true
            }
            R.id.nav_upcoming -> {
                onNavItemSelected?.screenNum(3)
                return true
            }
            R.id.nav_now_playing -> {
                onNavItemSelected?.screenNum(4)
                return true
            }
            R.id.nav_saved -> {
                onNavItemSelected?.screenNum(5)
                return true
            }
        }
        return false
    }

    // Sets the interface listener to the designated fragment which is implemented OnNavItemSelected
    fun setOnNavItemSelected(onNavItemSelected: OnNavItemSelected?) {
        this.onNavItemSelected = onNavItemSelected
    }

    // Interface which returns to the listener int as screen num
    interface OnNavItemSelected {
        fun screenNum(screenNum: Int)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showBottomView()
    }


}