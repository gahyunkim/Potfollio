package com.example.potfollio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PostActivity : AppCompatActivity() {
    lateinit var post_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        post_back = findViewById(R.id.post_back)

        post_back.setOnClickListener {
            // 액티비티가 바로 종료되도록 함
            finish()
        }


    }
}