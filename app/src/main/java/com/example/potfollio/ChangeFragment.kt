package com.example.potfollio

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

@Suppress("DEPRECATION", "UNUSED_LAMBDA_EXPRESSION")

class ChangeFragment : Fragment() {
    private val OPEN_GALLERY = 200
    lateinit var sqlDB: SQLiteDatabase
    lateinit var dbManager: SignUpActivity.DBManager

    lateinit var card_dbManager : DBManager
    lateinit var card_sqlDB : SQLiteDatabase

    lateinit var pro_dbManager : ProDBManager
    lateinit var pro_sqlDB : SQLiteDatabase

    lateinit var activity : MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pro_dbManager = ProDBManager(requireActivity(), "ProTBL", null, 1)
        pro_sqlDB = pro_dbManager.writableDatabase
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // MainActivity와 연결해서 사용함
        activity = getActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ChangeFragment에서 사용하고자 하는 요소들 선언 및 xml과 연결
        var profile_camera : ImageView = view.findViewById(R.id.profile_camera)
        var card_save :Button = view.findViewById(R.id.card_save)
        var change_nickname : EditText = view.findViewById(R.id.change_nickname)
        var change_info: EditText = view.findViewById(R.id.change_info)
        var change_sns: EditText = view.findViewById(R.id.change_sns)
        var change_phone: EditText = view.findViewById(R.id.change_phone)
        var change_mail : EditText = view.findViewById(R.id.change_mail)
        var change_name : EditText = view.findViewById(R.id.change_name)

        profile_camera.setOnClickListener {
            // 이미지뷰 클릭 시 갤러리도 이동함
            openGallery()
            Toast.makeText(activity, "이미지를 선택해 프로필 사진을 바꿔보세요", Toast.LENGTH_SHORT).show()
        }

        // 회원가입 시 생성된 groupTBL 사용을 위해 선언
        dbManager = SignUpActivity.DBManager(requireActivity(), "groupTBL", null, 1)
        sqlDB = dbManager.readableDatabase

        // 명함 수정을 위해 생성된 CardTBL 사용을 위해 선언
        card_dbManager = DBManager(requireActivity(), "CardTBL", null, 1)
        card_sqlDB = card_dbManager.writableDatabase

        card_save.setOnClickListener {
            // 사용자와 동일한 이름을 가지는 테이블을 찾도록 하는 부분
            var cursor: Cursor
            cursor = sqlDB.rawQuery(
                "SELECT gName FROM groupTBL WHERE gName='" +change_name.text.toString() + "'",
                null
            )

            if(change_name.text.toString().isBlank()||change_nickname.text.toString().isBlank()||change_sns.text.toString().isBlank()||change_phone.text.toString().isBlank()||
                change_mail.text.toString().isBlank()||change_info.text.toString().isBlank()){
                // 입력 가능한 하나라도 비어있는 경우 토스트 메세지 전달
                Toast.makeText(activity, "입력할 내용이 없는 경우 None을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else if(cursor.count==0){
                // 회원가입 시에 사용된 이름과 동일한 이름을 가지는 데이터 베이스가 존재하지 않으면 토스트 메세지 전달
                Toast.makeText(activity, "존재하지 않는 이름입니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                //  동일한 이름을 가지는 테이블을 찾도록 하는 부분
                var card_cursor: Cursor
                card_cursor = card_sqlDB.rawQuery(
                    "SELECT Name FROM CardTBL WHERE Name='" +change_name.text.toString() + "'",
                    null
                )
                if(card_cursor.count==0){
                    // 명함 수정 데이터베이스에 동일한 이름을 가지는 테이블이 없으면 이름을 토대로 새로운 행 생성
                    card_sqlDB.execSQL(
                        "INSERT INTO CardTBL VALUES ( '"
                                + change_name.text.toString() + "' , '"
                                + "Game Director" + "' , '"
                                + "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다." + "' , '"
                                + "pp734.k" + "' , '"
                                + "010.6345.6284" + "' , '"
                                + "Ikeyun3301@gmail.com" + "');")
                }
                else if (change_nickname.text.toString().length > 20) {
                    // 수정 시에 입력한 직업이 20자를 넘지 못하도록 함
                    Toast.makeText(activity, "직업을 최대 20자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else if (change_info.text.toString().length > 30) {
                    // 수정 시에 입력한 소개 정보가 30자를 넘지 못하도록 함
                    Toast.makeText(activity, "소개를 최대 30자로 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    // 사용자의 이름과 동일한 테이블에서 명함 수정 페이지에서 새로 적은 내용으로 데이터베이스를 수정하는 과정
                    card_sqlDB.execSQL(
                        "UPDATE CardTBL SET NickName = " + "'" + change_nickname.text.toString() + "'" + ", Info = '" + change_info.text.toString() + "'" + ", Sns = '" + change_sns.text.toString() + "'"
                                + ", Phone = '" + change_phone.text.toString() + "'" + ", Mail = '" + change_mail.text.toString() + "'"  +
                                "WHERE NAME = '" +change_name.text.toString() + "';"
                    )

                    Toast.makeText(activity, "                저장되었습니다 \n마이페이지로 돌아가서 확인하세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        var profile_camera : ImageView = requireView().findViewById(R.id.profile_camera)

        // 갤러리 오픈 후에 이미지를 선택하고 선택된 이미지를 데베에 저장하는 과정
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == OPEN_GALLERY) {
                var clipData: ClipData = data!!.clipData!!
                var currentImageUrl : Uri? = clipData.getItemAt(0).uri

                // ProTBL 데이터 베이스에 저장
                pro_sqlDB.execSQL(
                    "INSERT INTO ProTBL VALUES ( '"
                            + currentImageUrl.toString() + "');"
                )

                // 이미지 뷰에 이미지 띄우기(비트맵 활용)
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        currentImageUrl
                    )

                    profile_camera.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }

    // 갤러리 접근
    private fun openGallery(){
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, OPEN_GALLERY)
    }

    // 명함 수정 페이지에서 수정되는 명함의 내용을 저장하기 위한 데이터 베이스 CardTBL
    class DBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE CardTBL ( Name CHAR(30) , NickName CHAR(30) , Info CHAR(30), Sns CHAR(30) , Phone CHAR(30) , Mail CHAR(40));")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS CardTBL")
            onCreate(db)
        }
    }

    // 명함 수정 페이지에서 수정되는 프로필 사진을 저장하기 위한 데이터 베이스 ProTBL
    class ProDBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE ProTBL ( profileImg TEXT);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS ProTBL")
            onCreate(db)
        }
    }
}