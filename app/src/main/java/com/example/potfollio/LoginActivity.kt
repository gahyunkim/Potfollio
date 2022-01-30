package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class LoginActivity : AppCompatActivity() {
    lateinit var btnLog: Button
    lateinit var btnJoin: Button
    lateinit var edtLogId: EditText
    lateinit var autoLog: CheckBox
    lateinit var edtLogPass: EditText
    lateinit var card_name : TextView
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

//    private val fragmentManager = supportFragmentManager
//    private lateinit var transaction: FragmentTransaction
//    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLog = findViewById(R.id.btnLog)
        btnJoin = findViewById(R.id.btnJoin)
        edtLogId = findViewById(R.id.edtlogId)
        edtLogPass = findViewById(R.id.edtLogPass)
//        autoLog = findViewById(R.id.autoLog)
        //card_name = findViewById(R.id.card_name)

        loadData()

//        transaction = fragmentManager.beginTransaction()
//        transaction.add(R.id.card_frame,MyPageFragment())
//        transaction.commit()

        btnLog.setOnClickListener {
            dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
            sqlDB = dbManager.readableDatabase

//            transaction = fragmentManager.beginTransaction()

            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT gID, gPass,gName FROM groupTBL", null)

            while (cursor.moveToNext()) {
                var strId = cursor.getString(0)
                var strPass = cursor.getString(1)
                var strName = cursor.getString(2)
//                transaction = fragmentManager.beginTransaction()

                if (strId == edtLogId.text.toString()&&strPass == edtLogPass.text.toString()) {
                    Toast.makeText(applicationContext, "로그인되었습니다.", Toast.LENGTH_SHORT).show()

                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name",strName)

                    startActivity(intent)
                    //card_name!!.setText(strName.toString())
//                    transaction = fragmentManager.beginTransaction()
//                    name = strName
                }
//                transaction.commit()
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
/*
        // 자동 로그인 구현
        autoLog.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                saveData(edtLogId.text.toString(), edtLogPass.text.toString())
                //var intent = Intetnt(this,)
                intent.putExtra("id",edtLogId.text.toString())
                intent.putExtra("pass",edtLogPass.text.toString())
            }
        }
*/
        btnJoin.setOnClickListener {
            // 회원가입 화면으로 이동
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveData(id: String, pass: String){
        var pref = this.getPreferences(0)
        var editor = pref.edit()

        editor.putString("KEY_ID", edtLogId.text.toString()).apply()
        editor.putString("KEY_PASS", edtLogPass.text.toString()).apply()
    }

    private fun loadData(){
        var pref = this.getPreferences(0)
        var id = pref.getString("KEY_ID","")
        var pass = pref.getString("KEY_PASS","")

        if(id != "" && pass != "" ){
            edtLogId.setText(id.toString())
            edtLogPass.setText(pass.toString())
        }
    }

//    fun getname(): String {
//        return name
//    }
}