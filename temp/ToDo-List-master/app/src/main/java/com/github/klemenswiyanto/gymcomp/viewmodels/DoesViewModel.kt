package com.github.klemenswiyanto.gymcomp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.klemenswiyanto.gymcomp.data.GymRepository
import com.github.klemenswiyanto.gymcomp.data.Gym

class DoesViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: GymRepository =
        GymRepository(application)
    private var allGym: LiveData<List<Gym>> = repository.getAllDoes()

    fun insert(gym: Gym) {
        repository.insert(gym)
    }

    fun update(gym: Gym) {
        repository.update(gym)
    }

    fun delete(gym: Gym) {
        repository.delete(gym)
    }

    fun getAllDoes(): LiveData<List<Gym>> {
        return allGym
    }
}