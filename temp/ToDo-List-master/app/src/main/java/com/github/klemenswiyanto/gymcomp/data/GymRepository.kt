package com.github.klemenswiyanto.gymcomp.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class GymRepository(application: Application) {

    private var gymDao: GymDao

    private var allGym: LiveData<List<Gym>>

    init {
        val database: GymDatabase = GymDatabase.getInstance(
            application.applicationContext
        )!!
        gymDao = database.doesDao()
        allGym = gymDao.getAllDoes()
    }

    fun insert(gym: Gym) {
        InsertDoesAsyncTask(gymDao).execute(gym)
    }

    fun update(gym: Gym) {
        UpdateDoesAsyncTask(gymDao).execute(gym)
    }


    fun delete(gym: Gym) {
        DeleteDoesAsyncTask(gymDao).execute(gym)
    }

    fun getAllDoes(): LiveData<List<Gym>> {
        return allGym
    }

    companion object {
        private class InsertDoesAsyncTask(val gymDao: GymDao) : AsyncTask<Gym, Unit, Unit>() {

            override fun doInBackground(vararg p0: Gym?) {
                gymDao.insert(p0[0]!!)
            }
        }

        private class UpdateDoesAsyncTask(val gymDao: GymDao) : AsyncTask<Gym, Unit, Unit>() {

            override fun doInBackground(vararg p0: Gym?) {
                gymDao.update(p0[0]!!)
            }
        }

        private class DeleteDoesAsyncTask(val gymDao: GymDao) : AsyncTask<Gym, Unit, Unit>() {

            override fun doInBackground(vararg p0: Gym?) {
                gymDao.delete(p0[0]!!)
            }
        }
    }
}