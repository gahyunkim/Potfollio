package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.Random

class SearchFragment : Fragment(), View.OnClickListener {
    lateinit var activity : MainActivity
    lateinit var search_bar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater!!.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frame1 : FrameLayout= view.findViewById(R.id.frame1)
        val frame2 : FrameLayout= view.findViewById(R.id.frame2)
        val say_e : TextView = view.findViewById(R.id.say_e)
        val say_k : TextView = view.findViewById(R.id.say_k)

        search_bar = view.findViewById(R.id.search_bar)

        // 랜덤변수 생성
        val random = Random()
        val num = random.nextInt(5) // 0 ~ 4

        // 랜덤으로 문구 생성
        if(num == 0){
            say_e.text = "Success is the ability to go from one failure\nto another with no loss of enthusiasm."
            say_k.text = "성공이란 열정을 잃지 않고 실패를 거듭할 수 있는 능력이다.\n "
        }
        else if(num == 1) {
            say_e.text = "\nWhy be a man when you can be a success?"
            say_k.text = "성공한 사람이 될 수 있는데 왜 평범한 이에 머무르려 하는가?\n "
        }
        else if(num == 2) {
            say_e.text = "To follow, without halt, one aim:\nThere's the secret of success."
            say_k.text = "멈추지 말고 한 가지 목표에 매진하라. 그것이 성공의 비결이다.\n "
        }
        else if(num == 3) {
            say_e.text = "To accomplish great things,\nwe must dream as well as act."
            say_k.text = "위대한 성취를 하려면 행동하는 것뿐만 아니라,\n꿈꾸는 것도 반드시 필요하다."
        }
        else if(num == 4) {
            say_e.text = "Try not to become a man of success\nbut rather try to become a man of value."
            say_k.text = "성공한 사람이 아니라 가치있는 사람이 되기 위해 힘쓰라.\n "
        }

        val imageView : ImageView = view.findViewById(R.id.imageView)
        imageView.setClipToOutline(true)

        val imageView2 : ImageView = view.findViewById(R.id.imageView2)
        imageView2.setClipToOutline(true)

        frame1.setOnClickListener{
            Toast.makeText(getActivity(), "추천 검색어를 구경하세요!", Toast.LENGTH_SHORT).show();
            val intent = Intent(getActivity(), S1PostActivity::class.java)
            startActivity(intent)
        }
        frame2.setOnClickListener{
            Toast.makeText(getActivity(), "추천 검색어를 구경하세요!", Toast.LENGTH_SHORT).show();
            val intent = Intent(getActivity(), S2PostActivity::class.java)
            startActivity(intent)
        }

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼 누를 때 호출

                // 검색 버튼 클릭 시 관련 게시글들 나열된 곳으로 이동
                val intent = Intent(getActivity(), SearchPostActivity::class.java)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색창에서 글자가 변경이 일어날 때마다 호출
                return true
            }
        })
    }

    override fun onClick(p0: View?) {
    }
}