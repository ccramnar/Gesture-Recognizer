package com.example.a4starter

import android.app.AlertDialog
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.LinearLayout
import net.codebot.drawing.CanvasView


class AdditionFragment : Fragment() {
    private var mViewModel: SharedViewModel? = null
    var pageView: CanvasView? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_addition, container, false)

       // val textView = root.findViewById<TextView>(R.id.text_addition)

      //  mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Addition" })
       // mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})

        return root
    }
}
