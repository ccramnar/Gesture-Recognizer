package com.example.a4starter

import android.graphics.Bitmap
import android.graphics.Path
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.codebot.listview.Gesture
import java.util.*

class SharedViewModel : ViewModel() {
    val desc: MutableLiveData<String> = MutableLiveData()
    val strokeGestures: MutableLiveData<ArrayList<Path>> = MutableLiveData<ArrayList<Path>>()
    val arrayOfGesture = ArrayList<Gesture?>()

    init {
        desc.value = "Shared model"
        strokeGestures.value?.add(Path()) // empty path for illustration purposes
    }

    fun addStroke(path: Path) {
        strokeGestures.value?.add(path)
    }

    fun addGesture(string : String, image: Bitmap) {
        for (item in arrayOfGesture) {
            if (item?.name == string) {
                arrayOfGesture.remove(item)
            }
        }
        arrayOfGesture.add(Gesture(string, image))
    }

    fun printNames() {
        for (item in arrayOfGesture) {
            item?.name?.let { Log.d("DEBUG", it) }
        }
    }

    // ... more methods added here
}