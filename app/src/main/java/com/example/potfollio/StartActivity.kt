package com.example.potfollio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity(){
    lateinit var btnStart : Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener{
            // 로그인화면으로 전환
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}