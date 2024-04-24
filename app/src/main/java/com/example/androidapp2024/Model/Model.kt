package com.example.androidapp2024.Model

import android.media.audiofx.AudioEffect.Descriptor

class Model private constructor() {
    val users: MutableList<User> = ArrayList()

    companion object {
        val instance: Model = Model()
    }

    init {
        for (i in 0..20){
            val user = User("Name: $i", "ID: $i", "https://me.com/avatar.jpg", false)
        users.add(user)
        }
    }

}