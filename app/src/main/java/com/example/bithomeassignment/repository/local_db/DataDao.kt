package com.example.bithomeassignment.repository.local_db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bithomeassignment.models.Movie

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//dao interface to communicate with the room local db
@Dao
interface DataDao {

    // Queries the data by ascending title name
    @Query("SELECT * FROM movie ORDER BY title ASC")
    fun getData():List<Movie>

    // Insers the data to local db - also using onConflict if there
    // is already the same data inside the db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Movie)
}