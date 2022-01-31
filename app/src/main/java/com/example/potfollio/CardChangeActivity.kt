package com.example.potfollio

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val myFragment = MyPageFragment()

        val bundle: Bundle = Bundle() // 파라미터의 숫자는 전달하려는 값의 갯수

        bundle.putString("key", "value")
        myFragment.arguments =bundle

        card_back.setOnClickListener{
            // 액티비티가 바로 종료되도록 함
            finish()
        }

        card_save.setOnClickListener {
            if(change_nickname.text.toString().length > 10){
                Toast.makeText(applicationContext, "별명을 최대 10자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(change_info.text.toString().length > 30){
                Toast.makeText(applicationContext, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
//                if(change_nickname.text.toString().isNotBlank()){
//
//                }
//                bundle.putString("nickname",change_nickname.text.toString())
//                myFragment.arguments =bundle

                // 액티비티가 바로 종료되도록 함
                finish()

//                var intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("MyPage",true)  // "MyPage"라는 key값을 보낸다.
//                startActivity(intent)
            }
        }
    }
}