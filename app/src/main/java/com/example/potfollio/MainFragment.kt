package com.example.potfollio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainFragment : Fragment() {

    lateinit var btnhot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //gif
        val imageView : ImageView = view.findViewById(R.id.home_img)
        Glide.with(this).load(R.raw.home_animation).into(imageView)

        // 핫팟폴리오
        btnhot = view.findViewById(R.id.btnhot)
        btnhot.setOnClickListener{
            val intent = Intent(getActivity(),HotActivity::class.java)
            startActivity(intent)
        }
    }
}