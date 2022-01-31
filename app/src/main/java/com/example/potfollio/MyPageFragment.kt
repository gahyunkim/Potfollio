package com.example.potfollio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

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

    override fun onCreate(savedInstanceState: Bundle?) {
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()

        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()

        //Log.e("data", "제발 보여줘 내 이름 " + text.toString())
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStop() {
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()
        super.onStop()
    }

    override fun onResume() {
        //var nickname = arguments?.getString("nickname","Game Director")
//        val card_name = view?.findViewById<TextView>(R.id.card_name)
//
//        var text = arguments?.getString("name")
//        var uptext= text?.toUpperCase()
//        card_name?.text = uptext.toString()
//
//        val card_nickname = view?.findViewById<TextView>(R.id.card_nickname)
//        card_nickname?.text = arguments?.getString("nickname","Game d")
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)
        val card_name = view.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name", "이름")
        var uptext= text?.toUpperCase()
        card_name.text = uptext.toString()

        btn_card_change.setOnClickListener{
            val intent = Intent(getActivity(), CardChangeActivity::class.java)
            intent.putExtra("cardName",arguments?.getString("name"))
            startActivityForResult(intent,101)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show()
        }
    }
}