package com.github.klemenswiyanto.gymcomp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GymDao {

    @Insert
    fun insert(gym: Gym)

    @Update
    fun update(gym: Gym)

    @Delete
    fun delete(gym: Gym)

    @Query("SELECT * FROM fitness_table")
    fun getAllDoes(): LiveData<List<Gym>>

}