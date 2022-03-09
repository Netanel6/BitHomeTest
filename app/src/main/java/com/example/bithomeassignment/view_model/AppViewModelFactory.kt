package com.example.bithomeassignment.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.bithomeassignment.repository.IDataRepository
import com.example.bithomeassignment.repository.ISettingsRepository

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
class AppViewModelFactory(
    private val iDataRepository: IDataRepository,
    private val iSettingsRepository: ISettingsRepository,
) :
    NewInstanceFactory() {
    private val TAG = this::class.java.simpleName
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(iDataRepository, iSettingsRepository) as T
    }
}
