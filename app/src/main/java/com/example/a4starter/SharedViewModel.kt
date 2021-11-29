package com.example.a4starter

import android.graphics.Path
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {
    val desc: MutableLiveData<String> = MutableLiveData()
    val strokeGestures: MutableLiveData<ArrayList<Path>> = MutableLiveData<ArrayList<Path>>()

    init {
        desc.value = "Shared model"
        strokeGestures.value?.add(Path()) // empty path for illustration purposes
    }

    fun addStroke(path: Path) {
        strokeGestures.value?.add(path)
    }

    // ... more methods added here
}