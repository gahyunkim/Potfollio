package com.example.potfollio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '"
                    + edtName+ "' , '"
                    + edtId  + "' , '"
                + edtPass+"');")
            sqlDB.close()
            Toast.makeText(applicationContext,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
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