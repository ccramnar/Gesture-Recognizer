package com.example.a4starter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Path
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.codebot.listview.Gesture
import net.codebot.listview.GesturesAdapter

class LibraryFragment : Fragment() {
    private var mViewModel: SharedViewModel? = null
    var adapter:GesturesAdapter? =null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_library, container, false)
      //  val textView = root.findViewById<TextView>(R.id.text_library)

        //mViewModel!!.desc.observe(viewLifecycleOwner, { s:String -> textView.text = "$s - Library" })
        //mViewModel!!.strokeGestures.observe(viewLifecycleOwner, { s:ArrayList<Path> -> textView.text = "stroke count: ${s.size}"})

        print(view?.context);
        adapter = GesturesAdapter(getActivity()?.getBaseContext(), mViewModel!!.arrayOfGesture)

        val listView: ListView = root.findViewById<View>(R.id.lvItems) as ListView
        listView.setAdapter(adapter)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as Gesture
            val itemName = selectedItem.name as String
            Log.d("DEBUG", itemName)
            //code to delete
            deletelistviewItemDialog(itemName)
            //code to delete
        }

        return root
    }

    fun deletelistviewItemDialog(itemName : String) {
        val deleteToast: AlertDialog.Builder = AlertDialog.Builder(getActivity())
        deleteToast.setMessage("Do you want to delete this gesture")
        deleteToast.setCancelable(true)
        Log.d("DEBUG", "here")


        deleteToast.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, id ->
                    mViewModel?.deleteGesture(itemName)
                    Toast.makeText(getActivity(),"Gesture Deleted", Toast.LENGTH_SHORT).show();
                    adapter!!.notifyDataSetChanged()
                    dialog.cancel() })

        deleteToast.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(getActivity()?.getBaseContext(),"Gesture was not deleted. Action Cancelled",
                    Toast.LENGTH_SHORT).show();
                dialog.cancel() })

        val alert11: AlertDialog = deleteToast.create()
        Log.d("DEBUG", "BEFORE SHOE")
        alert11.show()
    }
}