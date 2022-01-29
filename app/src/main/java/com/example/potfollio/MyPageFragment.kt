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
    var bundle = arguments
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var bundle = arguments

        val nameData = arguments?.getString("name")
        val card_name = view?.findViewById<TextView>(R.id.card_name)
        card_name?.text = nameData.toString()
        Log.e("tag", "제발 보여줘 내 이름" + nameData.toString())
//
//        var bundle = this.arguments
//
//        val nameData = arguments?.getString("name")
//        val card_name = view?.findViewById<TextView>(R.id.card_name)
//        card_name?.text = nameData.toString()
//        Log.e("tag", "제발 보여줘 내 이름" + nameData.toString())
//        var bundle = arguments
//
//        val nameData = arguments?.getString("name")
//        val card_name = view?.findViewById<TextView>(R.id.card_name)
//        card_name?.text = nameData.toString()
//        Log.e("tag", "뭐야 "+ nameData.toString())

//        val data = arguments?.getString("nickname")
//        val card_nickname = view?.findViewById<TextView>(R.id.card_nickname)
//        card_nickname!!.text = data.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)
        val card_name = view.findViewById<TextView>(R.id.card_name)

//        val data = arguments?.getString("nickname")
//        val card_nickname = view.findViewById<TextView>(R.id.card_nickname)
//
//        if()
//        card_nickname?.text = data.toString()
//
//        val nameData = arguments?.getString("name")
//        val card_name = view?.findViewById<TextView>(R.id.card_name)
//        card_name?.text = nameData.toString()

        btn_card_change.setOnClickListener{
            val intent = Intent(getActivity(), CardChangeActivity::class.java)
            startActivity(intent)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show();
        }

    }
}