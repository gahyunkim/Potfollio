package com.example.potfollio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SettingFragment : Fragment() {
    lateinit var activity : MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // MainActivity와 연결해서 사용
        activity = getActivity() as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val explain_btn : Button = view.findViewById(R.id.explain_btn)
        val passwordchange_btn: Button = view.findViewById(R.id.passwordchange_btn)
        val btnLogOut: Button = view.findViewById(R.id.btnLogOut)

        explain_btn.setOnClickListener {
            // 메인 액티비티에서 생성한 dialog 불러오기
            activity.showDialog()
        }

        passwordchange_btn.setOnClickListener {
            // 비밀번호 변경 버튼 클릭 시 변경 화면(PasswordActivity)으로 이동
            val intent = Intent(getActivity(), PasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener {
            // 로그아웃 버튼 클릭 시 첫 화면(StartActivity)으로 이동
            val intent = Intent(getActivity(),LoginActivity::class.java)
            startActivity(intent)
        }

    }
}