package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    lateinit var edtName : EditText
    lateinit var edtId: EditText
    lateinit var edtPass: EditText
    lateinit var btnSign: Button
    lateinit var sqlDB: SQLiteDatabase
    lateinit var myHelper:myDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        edtPass = findViewById(R.id.edtPass)
        btnSign = findViewById(R.id.btnSign)

        myHelper = myDBHelper(this)

        btnSign.setOnClickListener {
            if(edtName.text.toString().isBlank()||edtId.text.toString().isBlank()|| edtPass.text.toString().isBlank()){
                // 닉네임, 아이디, 비번은 필수 입력 사항
                // (수정 완료) 수정필요: 이름,id,pw 모두 입력해야 넘어가는 걸로..
                Toast.makeText(applicationContext,"닉네임, 아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                // 회원가입시에 DB에 내용 저장
                sqlDB = myHelper.writableDatabase
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '"
                        + edtName.text.toString()+ "' , '"
                        + edtId.text.toString() + "' , '"
                        + edtPass.text.toString()+"');")
                sqlDB.close()

                // 회원가입시에 토스트 메시지 전달
                Toast.makeText(applicationContext,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()

                // 로그인화면으로 전환
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) PRIMARY KEY, gID CHAR(30) NOT NULL, gPass CHAR(30) NOT NULL);")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}