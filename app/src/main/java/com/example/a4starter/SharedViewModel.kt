package com.example.a4starter

import android.graphics.Bitmap
import android.graphics.Path
import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.codebot.listview.Gesture
import java.util.*
import android.graphics.PathMeasure
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import java.lang.Math.pow
import java.lang.Math.sqrt
import java.lang.reflect.Array.get
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue


class SharedViewModel : ViewModel() {
    val desc: MutableLiveData<String> = MutableLiveData()
    val strokeGestures: ArrayList<Path> = ArrayList<Path>()
    val strokePoints: ArrayList<Array<Point?>> = ArrayList<Array<Point?>>()
    val arrayOfGesture = ArrayList<Gesture?>()
    val arrayOfTopThree = ArrayList<Gesture?>()

    init {
        desc.value = "Shared model"
        strokeGestures.add(Path()) // empty path for illustration purposes
        val patharray: LiveData<ArrayList<Path>>
    }

    fun addStroke(path: Path) {
        Log.d("ADDING", "ADDIGN GESTUYRE")
        strokeGestures.add(path)
        strokePoints.add(changeGestureForComparison(path))
        Log.d("HELO", strokeGestures.toString())
    }

    fun addGesture(name : String, image: Bitmap) {
        for (item in arrayOfGesture) {
            if (item?.name == name) {
                val index  = arrayOfGesture.indexOf(item)
                arrayOfGesture.remove(item)
                strokeGestures.removeAt(index)
                strokePoints.removeAt(index)
                break
            }
        }
        arrayOfGesture.add(Gesture(name, image))
    }

    fun printNames() {
        for (item in arrayOfGesture) {
            item?.name?.let { Log.d("DEBUG", it) }
        }
    }

    fun deleteGesture(name: String) {
        for (item in arrayOfGesture) {
            if (item?.name == name) {
                val index  = arrayOfGesture.indexOf(item)
                arrayOfGesture.remove(item)
               strokeGestures.removeAt(index)
               strokePoints.removeAt(index)
                break
            }
        }
    }

    fun convertPathToPoints(path: Path) : Array<Point?> {
        /* source: piazza post: 783
        "Calculate the step size that you want to use i.e. step_size = path_length / number_of_points
        For every point in the path
        - determine the direction to the next point
        - move step_size in the direction
        - store your new location
        Repeat for all points, and you will have a regularly formed path containing number_of_points points."
        (Jeff Avery, 2021)
        https://developer.android.com/reference/android/graphics/PathMeasure
        https://developer.android.com/reference/android/graphics/Path
         */
        val N = 128
        val pointArray: Array<Point?> = arrayOfNulls<Point>(N)
        var counter = 0
        val pathmMeasureAttrib = PathMeasure(path, false)
        Log.d("MEASUREMENTS", pathmMeasureAttrib.toString())
        val length = pathmMeasureAttrib.length
        Log.d("MEASUREMENTS", length.toString())
        var currentdist = 0f
        val speed = length / N
        Log.d("MEASUREMENTS", speed.toString())
        val pointCoords = FloatArray(2)
        Log.d("MEASUREMENTS", length.toString())

        while ((currentdist < length) and (counter < N)) {
            // get point from the path
            pathmMeasureAttrib.getPosTan(currentdist, pointCoords, null)
            pointArray[counter] = Point(
                pointCoords[0].toInt(),
                pointCoords[1].toInt()
            )
            counter++
            currentdist += speed
        }

        return pointArray
    }

    fun calculateCentroid(points: Array<Point?>):Point {
        //source: piazza post @725
        var centroid_x = 0
        var centroid_y = 0
        var sum_x = 0;
        var sum_y = 0;
        var n = 0;
        for (point in points) {
            if (point == null) continue
            ++n;
            sum_x += point.x
            sum_y += point.y
        }
        centroid_x = (sum_x / n).toInt()
        centroid_y = (sum_y / n).toInt()
        return Point(centroid_x, centroid_y)
    }

    fun rotate(centroid: Point,pointArray: Array<Point?>): Array<Point?> {
        //source: https://stackoverflow.com/questions/22491178/how-to-rotate-a-point-around-another-point
        // permission given by instructor in piazza post
        val angle = 0.0;
        for (point in pointArray) {
            if (point != null) {
                val x_1 = point.x - centroid.x;
                val y_1 = point.y - centroid.y;
                val x_2 = x_1 * Math.cos(angle) - y_1 * Math.sin(angle)
                val y_2 = x_1 * Math.sin(angle) + y_1 * Math.cos(angle)
                point.x = (x_2 + centroid.x).toInt();
                point.y = (y_2 + centroid.y).toInt();
            }
        }
        return pointArray
    }

    fun scale(centroid: Point, pointArray: Array<Point?>): Array<Point?> {
        //source: https://stackoverflow.com/questions/27496920/how-can-i-scale-points-to-fit-a-fixed-size-graph
        // permission given by instructor in piazza post, code not copy and pasted
        val translate_x = centroid.x
        val translate_y = centroid.y
        var minYneg = 0;
        var minXneg = 0;
        var maxYpos = 0;
        var maxXpos = 0;
        for (point in pointArray) {
            if (point == null) continue
            point.x = point.x - translate_x
            point.y = point.y - translate_y
            if (point.x < minXneg) {
                minXneg = point.x
            }
            if (point.x > maxXpos) {
                maxXpos = point.x
            }
            if (point.y < minYneg) {
                minYneg = point.y
            }
            if (point.x < maxYpos) {
                maxYpos = point.y
            }
        }
        val absX = maxOf(maxXpos.absoluteValue, minXneg.absoluteValue)
        val absY = maxOf(maxYpos.absoluteValue, minYneg.absoluteValue)
        val size = 100;
        for (point in pointArray) {
            if (point == null) continue
            point.x = point.x - translate_x
            point.y = point.y - translate_y
        }
        return pointArray
    }

    fun changeGestureForComparison(path: Path) : Array<Point?> {
        val pointArray = convertPathToPoints(path)
        for (item in pointArray) {
            Log.d("POINT IN PATH", item.toString())
        }
        val centroid:Point = calculateCentroid(pointArray)
        Log.d("CENTROID", centroid.toString())
        rotate(centroid, pointArray)
        for (item in pointArray) {
            Log.d("POINT IN PATH ROTATED", item.toString())
        }
        scale(centroid, pointArray)
        return pointArray
    }

    fun doMath(given:Array<Point?>, library:Array<Point?>):Int {
        //source: lecture note website with the formula idk where it is
        var n = 0;
        var sum = 0.0;
        for (index in 0 until given.size) {
            if ((given[index] != null) and (library[index] != null)) {
                ++n;
                var given_x = given[index]!!.x
                var given_y = given[index]!!.y
                var library_x = library[index]!!.x
                var library_y = library[index]!!.y
                var xminusx = pow((given_x-library_x).toDouble(), 2.0)
                var yminusy = pow((given_y-library_y).toDouble(), 2.0)
                var top = sqrt(xminusx+yminusy)
                sum += top;
            }
        }
        var retval = sum / n
        return retval.toInt()
        //get the index of the top 3 i guess
    }

    fun recognizeGesture(path: Path){
        Log.d("Banger", "BANGER")
        //get an array of an array of points
        // for each array, calculkate the thing then compare with the value with the path given
        var comparison: MutableList<Array<Point?>>? = ArrayList()
        val givenPathPoints = changeGestureForComparison(path)
        var d: MutableList<Int> = ArrayList()
        Log.d("Banger", strokeGestures[0].toString())
           // var comparethis = changeGestureForComparison(strokeGestures[0])
        for (item in strokePoints) {
            d?.add(doMath(givenPathPoints, item))
        }
        var first = -1;
        var firstval = 10000;
        var second = -1;
        var secondval = 10000;
        var third = -1;
        var thirdval = 10000;
        for (index in 0 until d.size) {
            if (d[index] < firstval) {
                firstval = d[index]
                first = index;
            }
        }
        for (index in 0 until d.size) {
            if ((d[index] > firstval) and (d[index] < secondval)){
                secondval = d[index]
                second = index;
            }
        }
        for (index in 0 until d.size) {
            if ((d[index] > secondval) and (d[index] < thirdval)){
                thirdval = d[index]
                third = index;
            }
        }
        Log.d("Banger", first.toString())

        showTopThree(first, second, third)

    }

    fun showTopThree(first:Int, second:Int, third:Int) {
        arrayOfTopThree.clear()
        if (first == -1) {

        } else {
            arrayOfTopThree.add(arrayOfGesture[first])
        }
        if (second == -1) {

        } else {
            arrayOfTopThree.add(arrayOfGesture[second])
        }
        if (third == -1) {

        } else {
            arrayOfTopThree.add(arrayOfGesture[third])
        }
    }

    // ... more methods added here
}