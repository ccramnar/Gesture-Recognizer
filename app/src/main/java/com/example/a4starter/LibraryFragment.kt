package com.example.a4starter

import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.codebot.listview.Gesture
import net.codebot.listview.GesturesAdapter

class LibraryFragment : Fragment() {
    private var mViewModel: SharedViewModel? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_library, container, false)
      //  val textView = root.findViewById<TextView>(R.id.text_library)

        //mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Library" })
        //mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})

        print(view?.context);
        val adapter = GesturesAdapter(getActivity()?.getBaseContext(), mViewModel!!.arrayOfGesture)

        val listView: ListView = root.findViewById<View>(R.id.lvItems) as ListView
        listView.setAdapter(adapter)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.d("DEBUG", selectedItem)
        }

        return root
    }
}