package com.example.androidapp2024.Model

import android.media.audiofx.AudioEffect.Descriptor

class Model private constructor() {
    val users: MutableList<User> = ArrayList()

    companion object {
        val instance: Model = Model()
    }


}