package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class PasswordActivity : AppCompatActivity() {
    lateinit var id_confirm: EditText
    lateinit var pre_pass: EditText
    lateinit var new_pass: EditText
    lateinit var pass_complete: Button

    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
        sqlDB = dbManager.writableDatabase

        id_confirm = findViewById(R.id.id_confirm)
        pre_pass = findViewById(R.id.pre_pass)
        new_pass = findViewById(R.id.new_pass)
        pass_complete = findViewById(R.id.pass_complete)

        pass_complete.setOnClickListener {
            var cursor: Cursor
            cursor = sqlDB.rawQuery(
                "SELECT gID, gPass FROM groupTBL WHERE gID='" + id_confirm.text.toString() + "'",
                null
            )

            if (id_confirm.text.toString().isBlank() || pre_pass.text.toString()
                    .isBlank() || new_pass.text.toString().isBlank()) {
                Toast.makeText(applicationContext, "모든 빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(cursor.count==0){
                Toast.makeText(applicationContext, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                cursor.moveToNext()
                if(cursor.getString(1)!=pre_pass.text.toString()){
                    Toast.makeText(applicationContext, "기존의 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                }
                else if(new_pass.text.toString().length < 6){
                    Toast.makeText(applicationContext, "비밀번호를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    // 기존의 비밀번호에서 새로운 비밀번호로 데이터베이스에 저장
                    sqlDB.execSQL(
                        "UPDATE groupTBL SET gPass = " + "'" + new_pass.text.toString() + "';"
                    )

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}