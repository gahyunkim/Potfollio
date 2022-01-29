package com.example.potfollio

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction

@Suppress("DEPRECATION")
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

        val myfragment = MyPageFragment()
        val bundle = Bundle()
        myfragment.arguments = bundle

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLog = findViewById(R.id.btnLog)
        btnJoin = findViewById(R.id.btnJoin)
        edtLogId = findViewById(R.id.edtlogId)
        edtLogPass = findViewById(R.id.edtLogPass)
//        autoLog = findViewById(R.id.autoLog)
        //card_name = findViewById(R.id.card_name)

        // 로그인 후에 다시 들어왔을 때 로그인 내용 저장되어있도록 하는 부분
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
                    Toast.makeText(applicationContext, strName+"님"+" 로그인되었습니다.", Toast.LENGTH_SHORT).show()

                    val myfragment = MyPageFragment()
                    val bundle = Bundle()
                    bundle.putString("name",strName)
                    myfragment.arguments = bundle

                    var intent = Intent(this, MainActivity::class.java)
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

        // 자동 로그인 구현
//        autoLog.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked){
//                saveData(edtLogId.text.toString(), edtLogPass.text.toString())
//                //var intent = Intetnt(this,)
//                intent.putExtra("id",edtLogId.text.toString())
//                intent.putExtra("pass",edtLogPass.text.toString())
//            }
//        }

        btnJoin.setOnClickListener {
            // 회원가입 화면으로 이동
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

/*          // (갤러리 접근)
            // 권한이 부여되었는지 확인
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                // 이전에 이미 권한이 거부되었을 때 설명
                var dlg = AlertDialog.Builder(this)
                dlg.setTitle("권한이 필요한 이유")
                dlg.setMessage("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수로 필요합니다")
                dlg.setPositiveButton("확인") {dialog, which -> ActivityCompat.requestPermissions(this@LoginActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)}
                dlg.setNegativeButton("취소", null)
                dlg.show()
            } else {
                // 처음 권한 요청
                ActivityCompat.requestPermissions(this@LoginActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else{
            //권한이 이미 허용됨
            getAllPhotos()
        }
    }

    private fun getAllPhotos(){
        // 모든 사진 정보 가져오기
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null,
            MediaStore.Images.ImageColumns.DATE_TAKEN+ " DESC")

        if(cursor != null){
            while(cursor.moveToNext()) {

                //  사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("LoginActivity",uri)
            }
            cursor.close()
        } */
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