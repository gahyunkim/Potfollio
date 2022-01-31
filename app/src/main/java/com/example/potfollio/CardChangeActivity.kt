package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
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
    lateinit var dbManager: DBManager

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

        dbManager = DBManager(this, "CardTBL", null, 1)
        sqlDB = dbManager.writableDatabase

        var cardName= intent.getStringExtra("cardName")

        var cursor: Cursor
        cursor = sqlDB.rawQuery(
            "SELECT Name FROM CardTBL WHERE Name='" + intent.getStringExtra("cardName") + "'",
            null
        )

        if(cursor.count==0){
            sqlDB.execSQL(
                    "INSERT INTO CardTBL VALUES ( '"
                            + intent.getStringExtra("cardName").toString()+ "' , '"
                            + "Game Director" + "' , '"
                            + "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다." + "' , '"
                            + "pp734.k" + "' , '"
                            + "010.6345.6284" + "' , '"
                            + "Ikeyun3301@gmail.com" + "' , '"
                            + " " + "');"
                )
        }

//        val myFragment = MyPageFragment()
//
//        val bundle: Bundle = Bundle() // 파라미터의 숫자는 전달하려는 값의 갯수
//
//        bundle.putString("key", "value")
//        myFragment.arguments =bundle

        card_back.setOnClickListener{
            // 액티비티가 바로 종료되도록 함
            finish()
        }

        card_save.setOnClickListener {
            if(change_nickname.text.toString().length > 20){
                Toast.makeText(applicationContext, "별명을 최대 20자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(change_info.text.toString().length > 30){
                Toast.makeText(applicationContext, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                finish()

            }
        }
    }

    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE CardTBL ( Name CHAR(30) , NickName CHAR(30) , Info CHAR(30), Sns CHAR(30) , Phone CHAR(30) , Mail CHAR(40) , Link CHAR(40));")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
//            fun update(
//                Name: String, NickName: String, Info: String, Sns: String, Phone: String,
//                Mail: String, Link: String
//            ) {
//                var db: SQLiteDatabase = writableDatabase
//
//                db.execSQL(
//                    "UPDATE CardTBL SET Name = " + "'" + Name + "'" + ", Nickname = '" + NickName + "'" + ", PHONE = '" + phone + "'"
//                            + ", EMAIL = '" + email + "'" + ", ADDRESS = '" + address + "'" + ", LEVEL = '" + level + "'" +
//                            "WHERE NAME = '" + name + "';"
//                )
//
//                db.close()
            }

        }
    }
