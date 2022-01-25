package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    lateinit var btnLog: Button
    lateinit var btnJoin: Button
    lateinit var edtLogId: EditText
    lateinit var edtLogPass: EditText
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLog = findViewById(R.id.btnLog)
        btnJoin = findViewById(R.id.btnJoin)
        edtLogId = findViewById(R.id.edtlogId)
        edtLogPass = findViewById(R.id.edtLogPass)

        btnLog.setOnClickListener {
            dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
            sqlDB = dbManager.readableDatabase

            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT gID, gPass FROM groupTBL", null)

            while (cursor.moveToNext()) {
                var strId = cursor.getString(0)
                var strPass = cursor.getString(1)
                if (strId == edtLogId.text.toString()&&strPass == edtLogPass.text.toString()) {
                    Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            if (edtLogId.text.toString().isBlank() && edtLogPass.text.toString().isBlank()) {
                // 아이디와 비번은 필수 입력 사항
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            sqlDB.close()
        }
        btnJoin.setOnClickListener {
            // 회원가입 화면으로 이동
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}