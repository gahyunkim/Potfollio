package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    lateinit var btnLog : Button
    lateinit var btnJoin : Button
    lateinit var edtLogId : EditText
    lateinit var edtLogPass : EditText
    lateinit var sqlDB: SQLiteDatabase
    lateinit var myHelper: SignUpActivity.myDBHelper

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLog = findViewById(R.id.btnLog)
        btnJoin = findViewById(R.id.btnJoin)
        edtLogId = findViewById(R.id.edtlogId)
        edtLogPass = findViewById(R.id.edtLogPass)

        btnLog.setOnClickListener{
            sqlDB = myHelper.readableDatabase
            //var cursor: Cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null)

            if(edtLogId.text.toString().isBlank() || edtLogPass.text.toString().isBlank()){
                // 아이디와 비번은 필수 입력 사항
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                // 로그인 후에 홈화면으로 이동
                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        btnJoin.setOnClickListener{
            // 회원가입 화면으로 이동
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}