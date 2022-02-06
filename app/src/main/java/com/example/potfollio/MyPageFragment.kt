package com.example.potfollio

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MyPageFragment : Fragment() {
    lateinit var activity : MainActivity
    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var gridview : GridView
    private val OPEN_GALLERY = 200
    private val items = mutableListOf<GridViewItem>()

    lateinit var pro_dbManager : ChangeFragment.ProDBManager
    lateinit var pro_sqlDB : SQLiteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        image_dbManager = AddFragment.ImageDBManager(activity, "ImageTBL", null, 1)
        image_sqlDB = image_dbManager.readableDatabase

        var cursor: Cursor
        cursor = image_sqlDB.rawQuery("SELECT image , i_number FROM ImageTBL", null)

        var first_image = 1

        while (cursor.moveToNext()) {
            var iNum = cursor.getInt(1)

            // 같은 게시글의 첫 사진만 보이도록 한다.
            if (iNum == first_image) {
                var currentImageUri : Uri = cursor.getString(0).toUri()
                first_image += 1
                // 그리드뷰에 이미지 띄우기(비트맵 활용)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        currentImageUri
                    )
                    items.add(GridViewItem(bitmap))

                    val adapter = GridViewAdapter(items)
                    gridview.adapter = adapter

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        // 명함에 들어갈 내용들을 담을 데이터 베이스 CardTBL 사용을 위해 선언
        var card_dbManager : ChangeFragment.DBManager =
            ChangeFragment.DBManager(requireActivity(), "CardTBL", null, 1)
        var card_sqlDB : SQLiteDatabase = card_dbManager.writableDatabase

        // 명함에 들어갈 프로필 사진(이미지)을 담을 데이터 베이스 ProTBL 사용을 위해 선언
        pro_dbManager = ChangeFragment.ProDBManager(requireActivity(), "ProTBL", null, 1)
        pro_sqlDB = pro_dbManager.writableDatabase
        var profile : ImageView? = view?.findViewById(R.id.profile)

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
                    activity?.contentResolver,
                    currentImageUri
                )
                profile?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 프래그먼트에서 사용되는 요소들 findViewById로 연결
        var name = view?.findViewById<TextView>(R.id.card_name)
        var nickname = view?.findViewById<TextView>(R.id.card_nickname)
        var info = view?.findViewById<TextView>(R.id.card_info)
        var sns = view?.findViewById<TextView>(R.id.card_sns)
        var phone = view?.findViewById<TextView>(R.id.card_phone)
        var mail = view?.findViewById<TextView>(R.id.card_mail)
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        // 메인으로부터 받아온 사용자 이름을 대문자로 바꿔주는 과정
        var text = arguments?.getString("name", "이름")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()

        // 동일한 이름을 가지는 테이블을 찾도록 하는 부분 = 명함 수정 부분에서 사용한 CardTBL 데이터 열기
        var card_cursor: Cursor
        // 동일한 이름을 가지는 테이블을 찾도록 하는 부분
        card_cursor = card_sqlDB.rawQuery(
            "SELECT Name,NickName,Info,Sns,Phone,Mail FROM CardTBL WHERE Name='" + text.toString() + "'",
            null
        )

        if(card_cursor.count==0){
            // 동일한 이름을을 가지는 테이이 없는 경우 새롭게 테이블을 생성함 + 기본 데이터를 넣어줌
            name?.text = arguments?.getString("name")?.toUpperCase()
            nickname?.text =  "Game Director"
            info?.text =  "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다."
            sns?.text =  "d_dimi_"
            phone?.text =  "010.1234.5678"
            mail?.text =  "gameDirector@naver.com"
        }
        else if(card_cursor.count==1){
            // 동일한 이름을 가지는 테이블이 있는 경우 데이터베이스로 부터 각각의 데이터를 얻어서 명함 페이지 구성
            card_cursor.moveToNext()
            name?.text = card_cursor.getString(0).toUpperCase()
            nickname?.text =  card_cursor.getString(1)
            info?.text =  card_cursor.getString(2)
            sns?.text =  card_cursor.getString(3)
            phone?.text =  card_cursor.getString(4)
            mail?.text =  card_cursor.getString(5)
        }
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // MainActivity와 연결해서 사용함
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)
        val card_name = view.findViewById<TextView>(R.id.card_name)
        val settingButton: ImageButton = view.findViewById(R.id.settingButton)
        val btn_card_big = view.findViewById<Button>(R.id.btn_card_big)

        // 메인으로부터 받아온 사용자 이름을 대문자로 바꿔주는 과정
        var text = arguments?.getString("name", "이름")
        var uptext= text?.toUpperCase()
        card_name.text = uptext.toString()

        // 그리드뷰 이미지 클릭시 해당 게시글로 이동
        gridview = view.findViewById(R.id.gridview)
        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            var pos: Int = position + 1

            val intent = Intent(getActivity(), PostActivity::class.java)
            // 클릭한 게시글 번호 보내기
            intent.putExtra("name", text)
            intent.putExtra("postId", pos)
            startActivity(intent)
        }

        btn_card_change.setOnClickListener{
            // 명함 수정 버튼 클릭 시 명함 수정 프래그먼트로 화면 전환 - 메인 액티비티와 연결
            activity.FragmentChange(0)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show()
        }

        settingButton.setOnClickListener{
            // 설정 버튼 클릭 시 설정 프래그먼트로 이동 - 메인 액티비티와 연결
            activity.FragmentChange(1)
        }

        btn_card_big.setOnClickListener {
            // 명함 확인 버튼 클릭 시 명함 부분을 크게 보여주는 액티비티로 이동
            val intent = Intent(getActivity(),CardTransActivity::class.java)
            intent.putExtra("cname",arguments?.getString("name", "이름"))
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var profile : ImageView? = view?.findViewById(R.id.profile)
        pro_dbManager = ChangeFragment.ProDBManager(requireActivity(), "ProTBL", null, 1)
        pro_sqlDB = pro_dbManager.writableDatabase

        var pro_cursor: Cursor
        pro_cursor = pro_sqlDB.rawQuery("SELECT profileImg FROM ProTBL", null)

        if(resultCode == Activity.RESULT_OK ) {
            if(requestCode == OPEN_GALLERY) {
                if(pro_cursor.count!=0){
                    pro_cursor.moveToLast()
                    var currentImageUri : Uri = pro_cursor.getString(0).toUri()

                    // 프로필 이미지 뷰에 이미지 띄우기(비트맵 활용)
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            currentImageUri
                        )
                        profile?.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}