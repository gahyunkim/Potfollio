package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MyPageFragment : Fragment() {
    lateinit var activity : MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//
//        setHasOptionsMenu(true)
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

        //Log.e("data", "제발 보여줘 내 이름 " + text.toString())
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
        nickname?.text =  arguments?.getString("nickname", "Game Director")
        info?.text =  arguments?.getString("info", "안녕하세요.\n저의 Pot, Folio에 방문해주셔서 감사합니다.")
        sns?.text =  arguments?.getString("sns", "pp734.k")
        phone?.text =  arguments?.getString("phone", "010.6345.6284")
        mail?.text =  arguments?.getString("mail", "ikeyun3301@gmail.com")

        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
//        activity = null!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_card_change : Button = view.findViewById(R.id.btn_card_change)
        val card_name = view.findViewById<TextView>(R.id.card_name)
        val settingButton:ImageButton = view.findViewById(R.id.settingButton)

        var text = arguments?.getString("name", "이름")
        var uptext= text?.toUpperCase()
        card_name.text = uptext.toString()

        // 클릭시 명함 수정 액티비티로 화면 전환
        btn_card_change.setOnClickListener{
            val intent = Intent(getActivity(), CardChangeActivity::class.java)
            intent.putExtra("cardName", arguments?.getString("name"))
            startActivityForResult(intent, 101)
            Toast.makeText(getActivity(), "명함을 수정해보세요!", Toast.LENGTH_SHORT).show()
        }

        settingButton.setOnClickListener{
//            val intent = Intent(getActivity(), SettingActivity::class.java)
//            startActivity(intent)
//            (activity as MainActivity?)?.setting()
            activity.onFragmentChange()
        }

    }
}