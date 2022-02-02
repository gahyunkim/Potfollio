package com.example.potfollio

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MyPageFragment : Fragment() {

    lateinit var image_sqlDB: SQLiteDatabase
    lateinit var image_dbManager: AddFragment.ImageDBManager
    lateinit var gridview : GridView

    private val items = mutableListOf<GridViewItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()

        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val card_name = view?.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name")
        var uptext= text?.toUpperCase()
        card_name?.text = uptext.toString()

        image_dbManager = AddFragment.ImageDBManager(activity!!, "ImageTBL", null, 1)
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

        // 메인액티비티로 부터 전달받은 데이터
        var name = view?.findViewById<TextView>(R.id.card_name)
        var nickname = view?.findViewById<TextView>(R.id.card_nickname)
        var info = view?.findViewById<TextView>(R.id.card_info)
        var sns = view?.findViewById<TextView>(R.id.card_sns)
        var phone = view?.findViewById<TextView>(R.id.card_phone)
        var mail = view?.findViewById<TextView>(R.id.card_mail)

        // 전달 받은 데이터를 editText들에 넣어주기
        name?.text = arguments?.getString("name")?.toUpperCase()
        nickname?.text =  arguments?.getString("nickname","Game Director")
        info?.text =  arguments?.getString("info","안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다.")
        sns?.text =  arguments?.getString("sns","pp734.k")
        phone?.text =  arguments?.getString("phone","010.6345.6284")
        mail?.text =  arguments?.getString("mail","ikeyun3301@gmail.com")

        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)
        val card_name = view.findViewById<TextView>(R.id.card_name)

        var text = arguments?.getString("name", "이름")
        var uptext= text?.toUpperCase()
        card_name.text = uptext.toString()

        gridview = view.findViewById(R.id.gridview)

        // 클릭시 명함 수정 액티비티로 화면 전환
        btn_card_change.setOnClickListener{
            val intent = Intent(getActivity(), CardChangeActivity::class.java)
            intent.putExtra("cardName", arguments?.getString("name"))
            startActivityForResult(intent, 101)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show()
        }
    }
}