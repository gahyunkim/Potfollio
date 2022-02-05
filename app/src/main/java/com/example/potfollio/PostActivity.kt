package com.example.potfollio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.fragment_change.*

@Suppress("DEPRECATION")

class PostActivity : AppCompatActivity() {
    // lateinit var post_back: Button
    lateinit var infor_sqlDB: SQLiteDatabase
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var text_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var text_dbManager: AddFragment.TextDBManager
    lateinit var infor_dbManager: SignUpActivity.DBManager
    lateinit var pro_dbManager : ChangeFragment.ProDBManager
    lateinit var pro_sqlDB : SQLiteDatabase

    lateinit var recyclerview: RecyclerView
    lateinit var name: TextView
    lateinit var topic: TextView
    lateinit var contents: TextView
    private lateinit var post_profile : ImageView

    var postId : Int =0
    lateinit var iname : String

    val items = ArrayList<RecyclerViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //post_back = findViewById(R.id.post_back)
        recyclerview = findViewById(R.id.recyclerview)
        topic = findViewById(R.id.topic)
        contents = findViewById(R.id.contents)
        name = findViewById(R.id.name)
        post_profile = findViewById(R.id.post_profile)

        postId = intent.getIntExtra("postId", 100)
        iname = intent.getStringExtra("name").toString()

        name.text = iname

        infor_dbManager = SignUpActivity.DBManager(this, "groupTBL", null, 1)
        infor_sqlDB = infor_dbManager.readableDatabase

        image_dbManager = AddFragment.ImageDBManager(this, "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.readableDatabase

        text_dbManager = AddFragment.TextDBManager(this, "textTBL", null, 1)
        text_sqlDB = text_dbManager.readableDatabase

        // 프로필 이미지를 바꾸기 위해 사용하는 데이터베이스 ProTBL
        pro_dbManager = ChangeFragment.ProDBManager(this, "ProTBL", null, 1)
        pro_sqlDB = pro_dbManager.readableDatabase

        var cursor_image: Cursor
        cursor_image = image_sqlDB.rawQuery("SELECT image , i_number FROM ImageTBL", null)

        var cursor_text: Cursor
        cursor_text = text_sqlDB.rawQuery("SELECT i_number , title , contents FROM textTBL", null)

        // ProTBL 데이터베이스에서 원하는 부분만 cursor를 이용해 접근
        var pro_cursor: Cursor
        pro_cursor = pro_sqlDB.rawQuery("SELECT profileImg FROM ProTBL", null)

        if(pro_cursor.count!=0){
            // 데이터베이스에 이미지가 존재하지 않는 경우 오류를 대비해서 사용
            pro_cursor.moveToLast()
            // 가장 마지막 이미지 uri를 사용해서 프로필에 이미지 띄우기
            var currentImageUri : Uri = pro_cursor.getString(0).toUri()

            // 프로필 이미지뷰에 이미지 띄우기(비트맵 활용)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    currentImageUri
                )
                post_profile.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // postId랑 같은 데베 i_number 찾아서 게시물에 뿌리기

        // 이미지 보이기
        while (cursor_image.moveToNext()) {
            var iImage = cursor_image.getString(0)
            var iNum = cursor_image.getInt(1)

            if(postId == iNum) {
                var currentImageUri : Uri = iImage.toUri()

                // 리사이클러뷰에 이미지 띄우기(비트맵 활용)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        currentImageUri
                    )

                    val adapter = RecyclerViewAdapter(items)
                    recyclerview.adapter = adapter

                    // 레이아웃 매니저
                    val llm = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false) //좌우 스크롤
                    recyclerview.layoutManager = llm

                    //recyclerview.setHasFixedSize(true)

                    items.add(RecyclerViewItem(bitmap))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        // 제목, 내용 보이기
        while (cursor_text.moveToNext()) {
            var iNum = cursor_text.getInt(0)
            var iTitle = cursor_text.getString(1)
            var iContent = cursor_text.getString(2)

            if(postId == iNum) {
                topic.text = iTitle
                contents.text = iContent
            }
        }

        val bigtext = "디자인 관련자들이 관심가질 공모전 정보 - 한화손해보험 캐릭터 디자인 공모전 \n한화손해보험에서 디지털 세상에서 우리 한화손해보험 장기상품을 알려 줄 캐릭터 개발을 위해 공모전을 실시합니다.\n" +
                "\n" +
                "캐릭터의 퀄리티 보다는 자유로움과 독창적인 세계관을 더 중요시하는 이번 공모전의 취지를 살리기 위해 대학생 공모전으로 기획을 하였고 대학생뿐 아니라 일반인도 만30세까지 참여가 가능합니다.\n"


        var builder = NotificationCompat.Builder(this, "MY_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("디자인 관련자들을 위한 알림")
            .setContentText("디자인 관련자들이 관심가질 공모전 정보 - 한화손해보험 캐릭터 디자인 공모전")
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setPriority(Notification.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
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
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
            notificationManager.notify(1002, builder.build())
        }

//        post_back.setOnClickListener {
//            // 액티비티가 바로 종료되도록 함
//            finish()
//        }
    }

}
