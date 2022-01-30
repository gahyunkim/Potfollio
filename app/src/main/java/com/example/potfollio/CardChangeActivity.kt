package com.example.potfollio

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class CardChangeActivity : AppCompatActivity() {
    lateinit var card_back : Button
    lateinit var card_save : Button
    //lateinit var change_name : EditText
    lateinit var change_nickname : EditText
    lateinit var change_info: EditText
    lateinit var change_sns : EditText
    lateinit var change_phone: EditText
    lateinit var change_mail: EditText
    lateinit var change_link: EditText

    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    private val fragmentManager = supportFragmentManager
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_change)

        card_back = findViewById(R.id.card_back)
        card_save = findViewById(R.id.card_save)
        change_nickname = findViewById(R.id.change_nickname)
        change_info = findViewById(R.id.change_info)
        change_sns = findViewById(R.id.change_sns)
        change_phone = findViewById(R.id.change_phone)
        change_mail = findViewById(R.id.change_mail)
        change_link = findViewById(R.id.change_link)

//        var intent2 = Intent(this, MainActivity::class.java)
//        intent2.putExtra("nickname","Game")
//        startActivity(intent2)

        card_back.setOnClickListener{
            // 마이페이지로 이동
            // 메인엑티비티로 전환 -> key값 구분을 통해 마이페이지로 바로 넘어간다.
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("MyPage",true)  // "MyPage"라는 key값을 보낸다.
            startActivity(intent)
        }

        card_save.setOnClickListener {
            if(change_nickname.text.toString().length > 10){
                Toast.makeText(applicationContext, "별명을 최대 10자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(change_info.text.toString().length > 30){
                Toast.makeText(applicationContext, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(change_nickname.text.toString().isNotBlank()){
                    var intent2 = Intent(this, MainActivity::class.java)
                    intent2.putExtra("nickname","Game")
                    startActivity(intent2)
                }
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("MyPage",true)  // "MyPage"라는 key값을 보낸다.
                startActivity(intent)
            }
        }
    }
}