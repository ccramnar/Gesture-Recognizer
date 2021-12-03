package com.example.a4starter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import net.codebot.drawing.CanvasView
import net.codebot.listview.Gesture

class MainActivity : AppCompatActivity() {

    val model: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        // Passing each menu ID as a set of IDs because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_library, R.id.navigation_addition
        ).build()

        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    fun getBitmapFromView(view: View): Bitmap? {
        Log.d("DEBUG", view.toString())
        Log.d("DEBUG", "height: " + view.measuredHeight.toString() + " " + view.measuredHeight.toString())
        //view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        Log.d("DEBUG", "height: " + view.measuredHeight.toString() + " " + view.measuredHeight.toString())
        val bitmap = Bitmap.createBitmap(
            1080, 1200,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    fun AddGesture(view: View?) {
        val canView = findViewById<CanvasView>(R.id.canvasView)
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage("Enter the name of this gesture")
        builder1.setCancelable(true)
        val input = EditText(this)
        input.setHint("Enter Text")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder1.setView(input)

        builder1.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                if (canView.path == null ) {
                    Toast.makeText(getApplicationContext(),"There is not gesture to Add",Toast.LENGTH_SHORT).show();
                    dialog.cancel()
                } else {
                val canviewBitmap = getBitmapFromView(canView)
                var name: String = input.text.toString()
                Log.d("DEBUG",name)
                if (canviewBitmap != null) {
                    model.addStroke(canView.path!!)
                    model.addGesture(name, canviewBitmap)
                }
                Toast.makeText(getApplicationContext(),"Gesture Added",Toast.LENGTH_SHORT).show();
                dialog.cancel() }})

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(getApplicationContext(),"Gesture was not added. Action Cancelled",Toast.LENGTH_SHORT).show();
                dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun ClearGesture(view: View?) {
        Log.d("Debug", "clearing")
        Toast.makeText(getApplicationContext(),"Canvas Cleared",Toast.LENGTH_SHORT).show();
        val canView = findViewById<CanvasView>(R.id.canvasView)
        canView.clear()
    }

    fun RecognizeGesture(view: View?) {
        Log.d("Debug", "recognizing")
        Toast.makeText(getApplicationContext(),"Getsure Recognized",Toast.LENGTH_SHORT).show();
        val canView = findViewById<CanvasView>(R.id.canvasView)
        canView.clear()
    }

    fun debugger() {
        model.printNames()
    }

}