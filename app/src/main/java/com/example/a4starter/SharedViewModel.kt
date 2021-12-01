package com.example.a4starter

import android.graphics.Path
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.codebot.listview.Gesture
import java.util.*

class SharedViewModel : ViewModel() {
    val desc: MutableLiveData<String> = MutableLiveData()
    val strokeGestures: MutableLiveData<ArrayList<Path>> = MutableLiveData<ArrayList<Path>>()
    val arrayOfGestureNames = ArrayList<Gesture?>()

    init {
        desc.value = "Shared model"
        strokeGestures.value?.add(Path()) // empty path for illustration purposes
    }

    fun addStroke(path: Path) {
        strokeGestures.value?.add(path)
    }

    fun addName(string : String) {
        arrayOfGestureNames.add(Gesture(string))
    }

    // ... more methods added here
}