package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CardChangeActivity : AppCompatActivity() {
    lateinit var card_back: Button
    lateinit var card_save: Button

    lateinit var change_nickname: EditText
    lateinit var change_info: EditText
    lateinit var change_sns: EditText
    lateinit var change_phone: EditText
    lateinit var change_mail: EditText
//    lateinit var change_link: EditText

    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_change)

        card_save = findViewById(R.id.card_save)
        change_nickname = findViewById(R.id.change_nickname)
        change_info = findViewById(R.id.change_info)
        change_sns = findViewById(R.id.change_sns)
        change_phone = findViewById(R.id.change_phone)
        change_mail = findViewById(R.id.change_mail)

        dbManager = DBManager(this, "CardTBL", null, 1)
        sqlDB = dbManager.writableDatabase

        var cardName = intent.getStringExtra("cardName")

        var cursor: Cursor
        // 동일한 이름을 가지는 테이블을 찾도록 하는 부분
        cursor = sqlDB.rawQuery(
            "SELECT Name FROM CardTBL WHERE Name='" + intent.getStringExtra("cardName") + "'",
            null
        )

        // cursor에서 행이 0개인 경우에 값을 넣을 수 있도록 함
        if (cursor.count == 0) {
            sqlDB.execSQL(
                "INSERT INTO CardTBL VALUES ( '"
                        + intent.getStringExtra("cardName").toString() + "' , '"
                        + "Game Director" + "' , '"
                        + "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다." + "' , '"
                        + "pp734.k" + "' , '"
                        + "010.6345.6284" + "' , '"
                        + "Ikeyun3301@gmail.com" + "');"
            )
        }

        card_back.setOnClickListener {
            // 액티비티가 바로 종료되도록 함
            finish()
        }

        card_save.setOnClickListener {
            if (change_nickname.text.toString().isBlank() || change_info.text.toString()
                    .isBlank() || change_sns.text.toString()
                    .isBlank() || change_phone.text.toString()
                    .isBlank() || change_mail.text.toString().isBlank()
            ) {
                Toast.makeText(applicationContext, "입력할 내용이 없는 경우 None을 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else if (change_nickname.text.toString().length > 20) {
                Toast.makeText(applicationContext, "별명을 최대 20자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (change_info.text.toString().length > 30) {
                Toast.makeText(applicationContext, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 액티비티가 바로 종료되고 프래그먼트가 보이도록 함
                finish()

                // 명함 수정 페이지에서 받은 내용을 바탕으로 데이터 베이스 수정
                sqlDB = dbManager.writableDatabase
                sqlDB.execSQL(
                    "UPDATE CardTBL SET NickName = " + "'" + change_nickname.text.toString() + "'" + ", Info = '" + change_info.text.toString() + "'" + ", Sns = '" + change_sns.text.toString() + "'"
                            + ", Phone = '" + change_phone.text.toString() + "'" + ", Mail = '" + change_mail.text.toString() + "'"  +
                            "WHERE NAME = '" + intent.getStringExtra("cardName") + "';"
                )

                var cursor: Cursor
                cursor = sqlDB.rawQuery(
                    "SELECT Name,NickName,Info,Sns,Phone,Mail FROM CardTBL WHERE Name='" + intent.getStringExtra(
                        "cardName"
                    ).toString() + "'", null
                )

                var intent = Intent(this, MainActivity::class.java)

                if(cursor.count==1){
                    cursor.moveToNext()
                    intent.putExtra("name", cursor.getString(0))
                    intent.putExtra("nickname", cursor.getString(1))
                    intent.putExtra("info", cursor.getString(2))
                    intent.putExtra("sns", cursor.getString(3))
                    intent.putExtra("phone", cursor.getString(4))
                    intent.putExtra("mail", cursor.getString(5))
                    startActivity(intent)
                }

//                startActivity(intent)
//
//                var nickname.text = cursor.getString(1)
//                var info.text = cursor.getString(2)
//                var sns.text = cursor.getString(3)
//                var phone.text = cursor.getString(4)
//                var mail.text = cursor.getString(5)

                sqlDB.close()
                cursor.close()
            }
        }
    }

    class DBManager(
        context: CardChangeActivity,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE CardTBL ( Name CHAR(30) , NickName CHAR(30) , Info CHAR(30), Sns CHAR(30) , Phone CHAR(30) , Mail CHAR(40));")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS CardTBL")
            onCreate(db)
        }
    }
}


