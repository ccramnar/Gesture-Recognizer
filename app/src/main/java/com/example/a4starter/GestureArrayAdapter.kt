package net.codebot.listview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.a4starter.R

class Gesture(var name: String,  var image: Bitmap)

class GesturesAdapter(context: Context?, gestures: ArrayList<Gesture?>?) :
    ArrayAdapter<Gesture?>(context!!, 0, gestures!!) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        var view: View? = convertView
        val gesture = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gesture, parent, false)
        }

        // Lookup view for data population
        val gestureName = view?.findViewById(R.id.gestureName) as TextView
        val gestureImage = view?.findViewById(R.id.gestureImage) as ImageView

        // Populate the data into the template view using the data object
        gestureName.text = gesture!!.name
        gestureImage.setImageBitmap(gesture!!.image)

        // Return the completed view to render on screen


        return view
    }
}