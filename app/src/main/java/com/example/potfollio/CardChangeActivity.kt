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
import com.example.potfollio.databinding.ActivityMainBinding


class CardChangeActivity : AppCompatActivity() {
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

//    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_change)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)

        card_back = findViewById(R.id.card_back)
        card_save = findViewById(R.id.card_save)
        change_name = findViewById(R.id.change_name)
        change_nickname = findViewById(R.id.change_nickname)
        change_info = findViewById(R.id.change_info)
        change_sns = findViewById(R.id.change_sns)
        change_phone = findViewById(R.id.change_phone)
        change_mail = findViewById(R.id.change_mail)
        change_link = findViewById(R.id.change_link)

        card_back.setOnClickListener{
            // 마이페이지로 이동
            // 메인엑티비티로 전환 -> key값 구분을 통해 마이페이지로 바로 넘어간다.
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("MyPage",true)  // "MyPage"라는 key값을 보낸다.
            startActivity(intent)
        }

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
                if(change_nickname.text.isNotBlank()){
                    val myfragment : MyPageFragment = MyPageFragment()
                    val bundle = Bundle(1)
                    myfragment.arguments = bundle

                    bundle.putString("nickname",change_nickname.text.toString())
                }

                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("MyPage",true)  // "MyPage"라는 key값을 보낸다.
                startActivity(intent)
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