package com.example.potfollio

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    lateinit var btnLog: Button
    lateinit var btnJoin: Button
    lateinit var edtLogId: EditText
    lateinit var autoLog: CheckBox
    lateinit var edtLogPass: EditText
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {

        var builder = NotificationCompat.Builder(this)
//            .setSmallIcon(R.drawable.)
            .setContentTitle("IT 관련자들을 위한 알림")
            .setContentText("IT와 관련해 국내 및 해외 소식을 빠르게 알고 싶은 경우 'Geek News'를 사용해보세요! 누구든지 AI.IT 업계와 기술의 흐름을 놓치지 않을 수 있습니다. ")


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //gif부분
        val imageView : ImageView = findViewById(R.id.logo_login);
        Glide.with(this).load(R.raw.redlogo).override(300, 300).into(imageView)

        btnLog = findViewById(R.id.btnLog)
        btnJoin = findViewById(R.id.btnJoin)
        edtLogId = findViewById(R.id.edtlogId)
        edtLogPass = findViewById(R.id.edtLogPass)

        // 로그인 후에 다시 들어왔을 때 로그인 내용 저장되어있도록 하는 부분
        loadData()

        btnLog.setOnClickListener {
            // 회원가입시 생성된 데이터베이스 groupTBL을 사용하기 위해 선언
            dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
            sqlDB = dbManager.readableDatabase

            // cursor을 이용해 groupTBL에서 원하는 열만 선택해서 데이터 베이스 사용
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT gID, gPass,gName FROM groupTBL", null)

            while (cursor.moveToNext()) {
                var strId = cursor.getString(0)
                var strPass = cursor.getString(1)
                var strName = cursor.getString(2)

                // EditText에 입력한 id와 password가 데이터 베이스에 존재하는 아이디인지 확인하는 과정
                if (strId == edtLogId.text.toString()&&strPass == edtLogPass.text.toString()) {
                    Toast.makeText(
                        applicationContext,
                        strName + "님" + " 로그인되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // 존재하는 아이디와 비밀번호이면 EditText에 입력된 내용 저장 > 자동 로그인에 사용
                    saveData(edtLogId.text.toString(), edtLogPass.text.toString())

                    // 메인 액티비티로 strName 정보 전달 > 마이페이지 프래그먼트에서 사용하기 위함
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", strName)
                    startActivity(intent)

                    val bigtext = "IT와 관련해 국내 및 해외 소식을 빠르게 알고 싶은 경우 'Geek News'를 사용해보세요! 누구든지 AI.IT 업계와 기술의 흐름을 놓치지 않을 수 있습니다. "

                    var builder = NotificationCompat.Builder(this, "MY_channel")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("IT 관련자들을 위한 알림")
                        .setContentText("IT와 관련해 국내 및 해외 소식을 빠르게 알고 싶은 경우 'Geek News'를 사용해보세요! 누구든지 AI.IT 업계와 기술의 흐름을 놓치지 않을 수 있습니다. ")
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(bigtext))

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
                        val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
                        val channel_name = "채널이름" // 채널 이름 설정
                        val descriptionText = "설명글" // 채널 설명글 설정
                        val importance = NotificationManager.IMPORTANCE_HIGH // 알림 우선순위 설정
                        val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                            description = descriptionText
                        }

                        // 만든 채널 정보를 시스템에 등록
                        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)

                        // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
                        notificationManager.notify(1002, builder.build())

                    }
                }
            }

            if (edtLogId.text.toString().isBlank() && edtLogPass.text.toString().isBlank()) {
                // 아이디와 비번은 필수 입력 사항
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                // 아이디와 비밀번호가 잘못입력되거나 빈 공간이 있는 경우 토스트 메세지 전달
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

        // (갤러리 접근)
        // 권한이 부여되었는지 확인
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )){
                // 이전에 이미 권한이 거부되었을 때 설명
                var dlg = AlertDialog.Builder(this)
                dlg.setTitle("권한이 필요한 이유")
                dlg.setMessage("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수로 필요합니다")
                dlg.setPositiveButton("확인") { dialog, which -> ActivityCompat.requestPermissions(
                    this@LoginActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )}
                dlg.show()
//
//                var intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
            } else {
                // 처음 권한 요청
                ActivityCompat.requestPermissions(
                    this@LoginActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
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
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        if(cursor != null){
            while(cursor.moveToNext()) {

                //  사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("LoginActivity", uri)
            }
            cursor.close()
        }
    }

    private fun saveData(id: String, pass: String){
        // 자동 로그인을 하기 위해서 첫 로그인 시에 사용한 아이디와 비번의 내용을 저장
        var pref = this.getPreferences(0)
        var editor = pref.edit()

        editor.putString("KEY_ID", edtLogId.text.toString()).apply()
        editor.putString("KEY_PASS", edtLogPass.text.toString()).apply()
    }

    private fun loadData(){
        // 첫 로그인 시에 사용한 아이디와 비번의 내용을 로그인 화면에 불러옴
        var pref = this.getPreferences(0)
        var id = pref.getString("KEY_ID", "")
        var pass = pref.getString("KEY_PASS", "")

        if(id != "" && pass != "" ){
            edtLogId.setText(id.toString())
            edtLogPass.setText(pass.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        var intent = Intent(this, StartActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "저장소 권한을 승인해야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}