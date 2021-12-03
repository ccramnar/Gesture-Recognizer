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
import net.codebot.listview.GesturesAdapter

class HomeFragment : Fragment() {
    private var mViewModel: SharedViewModel? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {

        mViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
       // val textView = root.findViewById<TextView>(R.id.text_home)

       // mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Recognition" })
        //mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})

        val adapter = GesturesAdapter(getActivity()?.getBaseContext(), mViewModel!!.arrayOfGesture)

        val listView: ListView = root.findViewById<View>(R.id.lvItems) as ListView
        listView.setAdapter(adapter)

        return root
    }
}