package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

//@Suppress("DEPRECATION")

class MyPageFragment : Fragment() {

//    lateinit var profile_camera : ImageButton
//    private val OPEN_GALLERY = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name","이름")
        var uptext = text?.toUpperCase()
        card_name?.text = uptext.toString()
//        Log.e("data", "제발 보여줘 내 이름 " + text.toString())
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)

        btn_card_change.setOnClickListener{
            val intent = Intent(getActivity(), CardChangeActivity::class.java)
            startActivity(intent)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show();
        }

    }
}