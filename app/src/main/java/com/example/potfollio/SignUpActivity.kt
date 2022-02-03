package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class SignUpActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtId: EditText
    lateinit var edtPass: EditText
    lateinit var edtRePass: EditText
    lateinit var btnSign: Button
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //gif부분
        val imageView : ImageView = findViewById(R.id.logo_signup);
        Glide.with(this).load(R.raw.redlogo).override(300,300).into(imageView)


        edtName = findViewById(R.id.edtName)
        edtId = findViewById(R.id.edtId)
        edtPass = findViewById(R.id.edtPass)
        edtRePass = findViewById(R.id.edtRePass)
        btnSign = findViewById(R.id.btnSign)

        dbManager = DBManager(this, "groupTBL", null, 1)
        sqlDB = dbManager.writableDatabase

        btnSign.setOnClickListener {
            sqlDB = dbManager.writableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT gName,gID FROM groupTBL", null)

            if (cursor.count == 0) {
                // 데이터베이스에 아무런 행도 없는 경우 = 아직 값이 들어오지 않은 경우
                if (edtName.text.toString().isBlank() || edtId.text.toString().isBlank() || edtPass.text.toString().isBlank() || edtRePass.text.toString().isBlank()) {
                    // 닉네임, 아이디, 비번은 필수 입력 사항
                    // (수정 완료) 수정필요: 이름,id,pw 모두 입력해야 넘어가는 걸로..
                    Toast.makeText(applicationContext, "닉네임, 아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else if (edtName.text.toString().length < 2) {
                    Toast.makeText(applicationContext, "닉네임을 2자리 이상 입력하세요", Toast.LENGTH_SHORT).show()
                }
                else if (edtId.text.toString().length < 6) {
                    Toast.makeText(applicationContext, "아이디를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else if (edtPass.text.toString().length < 6) {
                    Toast.makeText(applicationContext, "비밀번호를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else if (edtRePass.text.toString() != edtPass.text.toString()) {
                    Toast.makeText(applicationContext, "비밀번호가 다릅니다. 다시 입력하세요", Toast.LENGTH_SHORT).show()
                }
                else {
                    // 위의 조건을 모두 충족하면 테이블에 데이터 저장
                    sqlDB.execSQL(
                        "INSERT INTO groupTBL VALUES ( '"
                                + edtName.text.toString() + "' , '"
                                + edtId.text.toString() + "' , '"
                                + edtPass.text.toString() + "');"
                    )
                    sqlDB.close()
                    // 회원가입시에 토스트 메시지 전달
                    Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    // 로그인화면으로 전환
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            else {
                // 데이터베이스에 값이 들어와져 있는 경우에 해당하는 부분
                cursor = sqlDB.rawQuery(
                    "SELECT gName FROM groupTBL WHERE gName='" + edtName.text.toString() + "'",
                    null
                )
                if (edtName.text.toString().isBlank() || edtId.text.toString().isBlank() || edtPass.text.toString().isBlank() || edtRePass.text.toString().isBlank()) {
                    // 닉네임, 아이디, 비번은 필수 입력 사항
                    // (수정 완료) 수정필요: 이름,id,pw 모두 입력해야 넘어가는 걸로..
                    Toast.makeText(applicationContext, "닉네임, 아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else if (cursor.count == 1) {
                    Toast.makeText(applicationContext, "이미 사용 중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
                else if (edtName.text.toString().length < 2) {
                    Toast.makeText(applicationContext, "닉네임을 2자리 이상 입력하세요", Toast.LENGTH_SHORT).show()
                }
                else if (cursor.count != 1) {
                    cursor = sqlDB.rawQuery("SELECT gID FROM groupTBL WHERE gID='" + edtId.text.toString() + "'", null)
                    if (cursor.count == 1) {
                        Toast.makeText(applicationContext, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else if (edtId.text.toString().length < 6) {
                        Toast.makeText(applicationContext, "아이디를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                    else if (edtPass.text.toString().length < 6) {
                        Toast.makeText(applicationContext, "비밀번호를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                    }
                    else if (edtRePass.text.toString() != edtPass.text.toString()) {
                        Toast.makeText(applicationContext, "비밀번호가 다릅니다. 다시 입력하세요", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        sqlDB.execSQL(
                            "INSERT INTO groupTBL VALUES ( '"
                                    + edtName.text.toString() + "' , '"
                                    + edtId.text.toString() + "' , '"
                                    + edtPass.text.toString() + "');"
                        )
                        sqlDB.close()
                        // 회원가입시에 토스트 메시지 전달
                        Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        // 로그인화면으로 전환
                        var intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    cursor.close()
                }
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
            db!!.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) NOT NULL, gID CHAR(30) NOT NULL, gPass CHAR(30) NOT NULL);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {

        }
    }
}

