package com.example.a4starter

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    fun AddGesture(view: View?) {
        val canView = findViewById<View>(R.id.canvasView)
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
                var name: String = input.text.toString()
                Log.d("DEBUG",name)
                model.addName(name)

                dialog.cancel() })

        builder1.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun ClearGesture(view: View?) {
        debugger()
    }

    fun debugger() {
        model.printNames()
    }
}