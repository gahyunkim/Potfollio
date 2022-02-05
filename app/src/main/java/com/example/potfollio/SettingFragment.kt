package com.example.potfollio

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SettingFragment : Fragment() {
    lateinit var activity : MainActivity
    lateinit var logactivity : LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        logactivity = getActivity() as LoginActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val explain_btn : Button = view.findViewById(R.id.explain_btn)
        val passwordchange_btn: Button = view.findViewById(R.id.passwordchange_btn)
        val btnLogOut: Button = view.findViewById(R.id.btnLogOut)

        explain_btn.setOnClickListener {
            activity.showDialog()
        }

        passwordchange_btn.setOnClickListener {
            val intent = Intent(getActivity(), PasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogOut.setOnClickListener {
//            var logactivity : LoginActivity
            val intent = Intent(getActivity(), LoginActivity::class.java)

//            logactivity.deleteData()
            startActivity(intent)
        }

    }
}