package com.example.potfollio

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.potfollio.MainFragment.Companion.newInstance
import com.example.potfollio.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding  // 뷰 바인딩
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sdbManager: SearchActivity.SearchDBManager

    private val fragmentManager = supportFragmentManager
//    val fragmentTransaction = fragmentManager.beginTransaction()
    private lateinit var transaction: FragmentTransaction

//    lateinit var settingButton : ImageButton

    //lateinit var adddbManager: AddFragment.DBManager

    override fun onCreate(savedInstanceState: Bundle?) {

//        settingButton = findViewById(R.id.settingButton)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        // 하단 네비게이션바
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.itemIconTintList = null

        // 리니어레이아웃 부분을 변경할 것임
        supportFragmentManager.beginTransaction().add(R.id.linearLayout, MainFragment()).commit()
//
//        fun setting(){
//            val fragment = SettingFragment()
//            fragmentTransaction.add(R.id.linearLayout, fragment)
//            fragmentTransaction.commit()
//        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

//        var data: String = intent.getStringExtra("name")!!
//        var data: String = intent.getStringExtra("name")!!
        // 하단 네비게이션바 메뉴 클릭시 이벤트
        when(item.itemId) {

            R.id.tab_main -> { // 메인화면(홈화면)
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MainFragment()).addToBackStack(null).commitAllowingStateLoss()
                transaction.addToBackStack(null)

                return true
            }
            R.id.tab_search ->{ // 검색창
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , SearchFragment()).addToBackStack(null).commitAllowingStateLoss()
                transaction.addToBackStack(null)

                sdbManager = SearchActivity.SearchDBManager(this, "searchList", null, 2)
                sqlDB = sdbManager.writableDatabase
                return true
            }
            R.id.tab_add -> { // 게시글 추가
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , AddFragment()).addToBackStack(null).commitAllowingStateLoss()
//                adddbManager = AddFragment.DBManager(this, "imageTBL", null, 2)
//                sqlDB = sdbManager.writableDatabase
                return true
            }
            R.id.tab_my -> { // 마이페이지
//                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MyPageFragment()).commitAllowingStateLoss()
                transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.linearLayout, MyPageFragment())
                transaction.addToBackStack(null)
                transaction.commit()

                // CardChangeActivity 로부터 얻은 값을 bundle을 이용해 MyPageFragment로 보내기
                val bundle = Bundle()
                bundle.putString("name", intent.getStringExtra("name"))
                bundle.putString("nickname", intent.getStringExtra("nickname"))
                bundle.putString("info", intent.getStringExtra("info"))
                bundle.putString("sns", intent.getStringExtra("sns"))
                bundle.putString("phone", intent.getStringExtra("phone"))
                bundle.putString("mail", intent.getStringExtra("mail"))
                transaction.replace(R.id.linearLayout, MyPageFragment().apply { arguments = bundle })

                return true
            }
        }
        return false
    }

    // MyPageFragment에서 SettingFragment로 이동하기 위한 부분
    // addToBackStack을 이용해 백스택을 저장함(전으로 돌아갈 수 있도록)
    fun onFragmentChange(){
        supportFragmentManager.beginTransaction().replace(R.id.linearLayout,SettingFragment()).addToBackStack(null).commit()
    }

    // SettingFragment에서 띄우는 이용약관 메세지
    fun showDialog(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        val dialogexplain = dialogView.findViewById<TextView>(R.id.dialog_explain)
        val dialogtext = dialogView.findViewById<TextView>(R.id.dialog_text)

        // 확인 버튼을 이용해서 이용약관에서 빠져나오기기
       builder.setView(dialogView).setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->

        }.show()
    }

}

