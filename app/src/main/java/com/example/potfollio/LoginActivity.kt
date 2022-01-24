package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        btnLog.setOnClickListener{
            // 홈화면으로 전환
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnJoin.setOnClickListener{
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE groupTBL ( gID CHAR(30) NOT NULL, gPass CHAR(30) NOT NULL);")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}