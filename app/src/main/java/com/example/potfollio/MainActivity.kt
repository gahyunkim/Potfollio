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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.potfollio.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding  // 뷰 바인딩
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sdbManager: SearchFragment.SearchDBManager

    private val fragmentManager = supportFragmentManager
//    val fragmentTransaction = fragmentManager.beginTransaction()
    private lateinit var transaction: FragmentTransaction

//    lateinit var settingButton : ImageButton

    //lateinit var adddbManager: AddFragment.DBManager

    override fun onCreate(savedInstanceState: Bundle?) {

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
//        transaction = fragmentManager.beginTransaction()
//        transaction.detach(MyPageFragment()).attach(MyPageFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
      //  val imageView : ImageView = findViewById(R.id.home_img);

//        var data: String = intent.getStringExtra("name")!!
//        var data: String = intent.getStringExtra("name")!!
        // 하단 네비게이션바 메뉴 클릭시 이벤트
        when(item.itemId) {

            R.id.tab_main -> { // 메인화면(홈화면)
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MainFragment()).addToBackStack(null).commitAllowingStateLoss()
               // transaction.addToBackStack(null)
                return true
            }
            R.id.tab_search ->{ // 검색창
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , SearchFragment()).addToBackStack(null).commitAllowingStateLoss()

                sdbManager = SearchFragment.SearchDBManager(this, "searchList", null, 2)
                sqlDB = sdbManager.writableDatabase

                return true
            }
            R.id.tab_add -> { // 게시글 추가
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , AddFragment()).addToBackStack(null).commitAllowingStateLoss()
//                val bundle = Bundle()
//                bundle.putString("name", intent.getStringExtra("name"))
//                adddbManager = AddFragment.DBManager(this, "imageTBL", null, 2)
//                sqlDB = sdbManager.writableDatabase

                return true
            }
            R.id.tab_my -> { // 마이페이지
//                val intent = Intent(this,CardTransActivity::class.java)
//                startActivity(intent)
//                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MyPageFragment()).commitAllowingStateLoss()
                transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.linearLayout, MyPageFragment())
                transaction.addToBackStack(null)

                transaction.commit()
//                transaction.detach(MyPageFragment()).attach(MyPageFragment()).commit()

                // CardChangeActivity 로부터 얻은 값을 bundle을 이용해 MyPageFragment로 데이터 전달
                val bundle = Bundle()
                bundle.putString("name", intent.getStringExtra("name"))
//                bundle.putString("nickname", intent.getStringExtra("nickname"))
//                bundle.putString("info", intent.getStringExtra("info"))
//                bundle.putString("sns", intent.getStringExtra("sns"))
//                bundle.putString("phone", intent.getStringExtra("phone"))
//                bundle.putString("mail", intent.getStringExtra("mail"))
                transaction.replace(R.id.linearLayout, MyPageFragment().apply { arguments = bundle })

                //gif부분
                //Glide.with(this).load(R.drawable.none).into(imageView)

                return true
            }
        }
        return false
    }

    // 액티비티에서 선언되지 못한 프래그먼트가 하단바를 사용하기 위해서 작성된 부분.
    // 프래그먼트에서 프래그먼트로 이동하기 위한 과정
    // addToBackStack을 이용해 백스택을 저장함(전으로 돌아갈 수 있도록)
    fun FragmentChange(index: Int) {
        if (index == 0) {
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, Post1Fragment())
                .addToBackStack(null).commit()
        } else if (index == 1) {
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, Post2Fragment())
                .addToBackStack(null).commit()
        } else if (index == 2) {
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, ChangeFragment())
                .addToBackStack(null).commit()
        } else if (index == 3) {
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, SettingFragment())
                .addToBackStack(null).commit()
//        else{
//            supportFragmentManager.beginTransaction().detach(MyPageFragment()).attach(MyPageFragment()).addToBackStack(null).commit()
////            transaction = fragmentManager.beginTransaction()
////            transaction.detach(MyPageFragment()).attach(MyPageFragment()).commit()
//        }
        }
    }

        // SettingFragment에서 띄우는 이용약관 메세지
        fun showDialog() {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog, null)
//        val dialogexplain = dialogView.findViewById<TextView>(R.id.dialog_explain)
//        val dialogtext = dialogView.findViewById<TextView>(R.id.dialog_text)

            // 확인 버튼을 이용해서 이용약관에서 빠져나오기
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }

}

