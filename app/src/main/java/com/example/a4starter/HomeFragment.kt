package com.example.a4starter

import android.graphics.Path
import android.os.Bundle
import android.service.media.MediaBrowserService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.codebot.drawing.CanvasView
import net.codebot.listview.Gesture
import net.codebot.listview.GesturesAdapter

class HomeFragment : Fragment() {
    private var mViewModel: SharedViewModel? = null
    var adapter:GesturesAdapter? =null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {

        mViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
       // val textView = root.findViewById<TextView>(R.id.text_home)

       // mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Recognition" })
        //mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})

         adapter = GesturesAdapter(getActivity()?.getBaseContext(), mViewModel!!.arrayOfTopThree)

        val listView: ListView = root.findViewById<View>(R.id.lvItems) as ListView
        listView.setAdapter(adapter)

        val recogbutton: Button = root.findViewById<Button>(R.id.button3) as Button
        recogbutton.setOnClickListener {
            RecognizeGesture(root)
            adapter!!.notifyDataSetChanged() }

        return root
    }

    fun RecognizeGesture(root: View) {
        Log.d("Debug", "recognizing")
        Toast.makeText(getActivity(),"Getsure Recognized", Toast.LENGTH_SHORT).show();
        val canView = root.findViewById<CanvasView>(R.id.canvasView)
        val path  = canView.path
        if (path != null ) {
            mViewModel!!.recognizeGesture(path)
        }

    }




}