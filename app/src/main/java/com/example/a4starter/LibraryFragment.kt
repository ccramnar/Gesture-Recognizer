package com.example.a4starter

import android.graphics.Path
import android.os.Bundle
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
        val textView = root.findViewById<TextView>(R.id.text_library)

        mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Library" })
        mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})


        print("help")
        print(view?.context);
        print("help")
        val adapter = GesturesAdapter(getActivity()?.getBaseContext(), mViewModel!!.arrayOfGestureNames)

        val listView: ListView = root.findViewById<View>(R.id.lvItems) as ListView
        listView.setAdapter(adapter)


        adapter.add(Gesture("Triangle"))
        adapter.add(Gesture("Square"))

        return root
    }
}