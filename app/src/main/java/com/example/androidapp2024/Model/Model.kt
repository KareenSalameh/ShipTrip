package com.example.androidapp2024.Model

import android.media.audiofx.AudioEffect.Descriptor
import com.example.androidapp2024.dao.AppLocalDatabase

class Model private constructor() {
    private val database = AppLocalDatabase.db
    companion object {
        val instance: Model = Model()
    }

    fun getAllUsers(): List<User> = database.userDao().getAll()


}