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
import com.example.potfollio.databinding.ActivityMainBinding


class CardChangeActivity : AppCompatActivity() {
    //private lateinit var binding : ActivityMainBinding  // 뷰 바인딩

    lateinit var card_back : Button
    lateinit var card_save : Button
    lateinit var change_name : EditText
    lateinit var change_nickname : EditText
    lateinit var change_info: EditText
    lateinit var change_sns : EditText
    lateinit var change_phone: EditText
    lateinit var change_mail: EditText
    lateinit var change_link: EditText

    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_card_change)

        card_save = findViewById(R.id.card_save)
        change_name = findViewById(R.id.change_name)
        change_nickname = findViewById(R.id.change_nickname)
        change_info = findViewById(R.id.change_info)
        change_sns = findViewById(R.id.change_sns)
        change_phone = findViewById(R.id.change_phone)
        change_mail = findViewById(R.id.change_mail)
        change_link = findViewById(R.id.change_link)
        //card_name = findViewById(R.id.card_name)

        //val fragment: MyPageFragment = supportFragmentManager.findFragmentById(R.id.my_page) as MyPageFragment
        //supportFragmentManager.beginTransaction().add(R.id.linearLayout, MyPageFragment()).commit()

        card_save.setOnClickListener {
            dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
            sqlDB = dbManager.writableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery(
                "SELECT gName FROM groupTBL WHERE gName='" + change_name.text.toString() + "'",
                null
            )
            if(cursor.count == 1){
                Toast.makeText(applicationContext, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
            }
            else if(change_name.text.toString().length > 10){
                Toast.makeText(applicationContext, "이름을 최대 10자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                // 데이터에서 닉네임 받아와서 데이터베이스 수정해야함
            }
            else if(change_nickname.text.toString().length > 10){
                Toast.makeText(applicationContext, "별명을 최대 10자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(change_info.text.toString().length > 30){
                Toast.makeText(applicationContext, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                //supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MyPageFragment()).commitAllowingStateLoss()
//                return true

//                sqlDB = dbManager.writableDatabase
//                sqlDB.execSQL("UPDATE groupTBL SET gName =" + change_name.text+" WHERE gName =" +   )
//                // 비어있는 에디트 텍스트가 존재하는 경우에는 어떤 곳이 존재하는 지 확인하고 값을 넣어야 함
//                if(change_name.text.toString().isNotBlank()){
//                    //card_name?.text = change_name.text.toString()
//                }
//                val intent = Intent(applicationContext, MyPageFragment::class.java)
//                startActivity(intent)
            }
        }
    }
}