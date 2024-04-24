package com.example.androidapp2024

import android.os.Bundle
//import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class FirstFragment : Fragment() {
    private var textView: TextView? = null
    private var title: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_first, container, false)

        val firstTitle = arguments?.let {
            FirstFragmentArgs.fromBundle(it).TITLE
        }

        textView = view.findViewById(R.id.tvFragmentTitle)
        textView?.text = firstTitle?: "Please assign title"
        // textView.text = "Some new title"

        val backButton: Button = view.findViewById(R.id.btnFirstFragment)
        backButton.setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }
        return view
    }

}

//companion object {
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FirstFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    @JvmStatic
//    fun newInstance(param1: String, param2: String) =
//        FirstFragment().apply {
//            arguments = Bundle().apply {
//                putString(ARG_PARAM1, param1)
//                putString(ARG_PARAM2, param2)
//            }
//        }
//}