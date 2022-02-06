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

        // 아이디와 비번 확인을 위해 회원가입 시 사용한 데이터베이스 groupTBL 선언
        dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
        sqlDB = dbManager.writableDatabase

        id_confirm = findViewById(R.id.id_confirm)
        pre_pass = findViewById(R.id.pre_pass)
        new_pass = findViewById(R.id.new_pass)
        pass_complete = findViewById(R.id.pass_complete)

        pass_complete.setOnClickListener {
            // cursor를 이용해 groupTBL에서 ID와 PASS를 따로 접근
            var cursor: Cursor
            cursor = sqlDB.rawQuery(
                "SELECT gID, gPass FROM groupTBL WHERE gID='" + id_confirm.text.toString() + "'",
                null
            )

            if (id_confirm.text.toString().isBlank() || pre_pass.text.toString()
                    .isBlank() || new_pass.text.toString().isBlank()) {
                // 아이디, 비번, 새로운 비번 모든 빈칸을 채워야 저장이 가능하도록 함
                Toast.makeText(applicationContext, "모든 빈칸을 채워주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(cursor.count==0){
                // cursor로 찾은 테이블이 행이 없는 경우는 아이디가 존재하지 않는 경우이므로 아이디가 없다고 토스트함
                Toast.makeText(applicationContext, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                cursor.moveToNext()
                if(cursor.getString(1)!=pre_pass.text.toString()){
                    // cursor로 찾은 테이블에서 비밀번호가 다른 경우
                    Toast.makeText(applicationContext, "기존의 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                }
                else if(new_pass.text.toString().length < 6){
                    // 새로운 비밀번호를 6자리 이상 입력하도록 함
                    Toast.makeText(applicationContext, "비밀번호를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    // 기존의 비밀번호에서 새로운 비밀번호로 데이터베이스에 저장(데이터베이스 수정 과정)
                    sqlDB.execSQL(
                        "UPDATE groupTBL SET gPass = " + "'" + new_pass.text.toString() + "';"
                    )

                    // 비밀번호 변경이 완료되면 StartActivity로 화면 이동
                    val intent = Intent(this, StartActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}