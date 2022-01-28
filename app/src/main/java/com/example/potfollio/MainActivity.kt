package com.example.potfollio

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.potfollio.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding  // 뷰 바인딩
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sdbManager: SearchActivity.SearchDBManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        // 하단 네비게이션바
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // 리니어레이아웃 부분을 변경할 것임
        supportFragmentManager.beginTransaction().add(R.id.linearLayout, MainFragment()).commit()

        // CardChangeActivity.kt 에서 나왔을 때 바로 마이페이지가 보이도록 한다.
        if (intent.hasExtra("MyPage")) { // "MyPage"라는 이름이 key값에 저장되어 있다면 마이페이지로 이동
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MyPageFragment()).commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // 하단 네비게이션바 메뉴 클릭시 이벤트
        when(item.itemId) {

            R.id.tab_main -> { // 메인화면(홈화면)
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MainFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.tab_search ->{ // 검색창
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , SearchFragment()).commitAllowingStateLoss()
                sdbManager = SearchActivity.SearchDBManager(this, "searchList", null, 2)
                sqlDB = sdbManager.writableDatabase
                return true
            }
            R.id.tab_add -> { // 게시글 추가
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , AddFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.tab_my -> { // 마이페이지
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MyPageFragment()).commitAllowingStateLoss()
                return true
            }
        }

        return false
    }

}

