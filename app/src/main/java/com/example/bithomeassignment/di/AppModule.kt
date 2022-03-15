package com.example.bithomeassignment.di

import NetworkManager
import android.content.Context
import com.example.bithomeassignment.network.ApiService
import com.example.bithomeassignment.network.RetrofitInstance
import com.example.bithomeassignment.repository.DataRepository
import com.example.bithomeassignment.repository.local_db.AppDatabase
import com.example.bithomeassignment.repository.local_db.DataDao
import com.example.bithomeassignment.view_model.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Netanel Amar on 15/03/2022.
 * NetanelCA2@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ) = AppDatabase.getDatabase(context)
    
    @Singleton
    @Provides
    fun provideMoviesDao(database: AppDatabase) =
        database.getMovieDao()

    @Singleton
    @Provides
    fun provideDataRepository(
        apiService: ApiService,
        dataDao: DataDao,
    ) = DataRepository(apiService, dataDao)

    @Singleton
    @Provides
    fun provideMovieListViewModel(
        dataRepository: DataRepository,
    ) = MovieListViewModel(dataRepository)

    @Singleton
    @Provides
    fun provideRetrofitInstance() = RetrofitInstance

    @Singleton
    @Provides
    fun provideNetworkManager(retrofitInstance: RetrofitInstance) =
        retrofitInstance.create(ApiService::class.java)
}