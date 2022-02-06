package com.example.potfollio

import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.potfollio.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding  // 뷰 바인딩

    private val fragmentManager = supportFragmentManager
    private lateinit var transaction: FragmentTransaction

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

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // 하단 네비게이션바 메뉴 클릭시 이벤트
        when(item.itemId) {

            R.id.tab_main -> { // 메인화면(홈화면)
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , MainFragment()).addToBackStack(null).commitAllowingStateLoss()

                return true
            }
            R.id.tab_search ->{ // 검색창
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , SearchFragment()).addToBackStack(null).commitAllowingStateLoss()

                return true
            }
            R.id.tab_add -> { // 게시글 추가
                supportFragmentManager.beginTransaction().replace(R.id.linearLayout , AddFragment()).addToBackStack(null).commitAllowingStateLoss()

                return true
            }
            R.id.tab_my -> { // 마이페이지

                transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.linearLayout, MyPageFragment())
                transaction.addToBackStack(null)

                transaction.commit()

                // CardChangeActivity 로부터 얻은 값을 bundle을 이용해 MyPageFragment로 데이터 전달
                val bundle = Bundle()
                bundle.putString("name", intent.getStringExtra("name"))

                transaction.replace(R.id.linearLayout, MyPageFragment().apply { arguments = bundle })

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
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, ChangeFragment())
                .addToBackStack(null).commit()
        } else if (index == 1) {
            supportFragmentManager.beginTransaction().replace(R.id.linearLayout, SettingFragment())
                .addToBackStack(null).commit()
        }
    }

        // SettingFragment에서 띄우는 이용약관 메세지
        fun showDialog() {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog, null)

            // 확인 버튼을 이용해서 이용약관에서 빠져나오기
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> }.show()
        }

}

