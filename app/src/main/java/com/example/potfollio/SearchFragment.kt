package com.example.potfollio

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.example.potfollio.databinding.FragmentMainBinding

class SearchFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentMainBinding
    lateinit var sqlDB: SQLiteDatabase
    lateinit var sdbManager: SearchDBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view : View = inflater!!.inflate(R.layout.fragment_search,container,false)
//        val search_bar : SearchView = inflater!!.inflate(R.layout.fragment_search,container,false) as SearchView
//
//        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                //sdbManager = SearchDBManager(this, "searchList", null, 2)
//                sqlDB = sdbManager.writableDatabase
//                sqlDB.execSQL(
//                    "INSERT INTO searchList VALUES ( '"
//                            + query.toString() + "');"
//                )
//                //Toast.makeText(applicationContext, "[검색버튼클릭] 검색어 = "+query.toString(), Toast.LENGTH_LONG).show();
//
//                sqlDB.close()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frame1 : FrameLayout= view.findViewById(R.id.frame1)
        val frame2 : FrameLayout= view.findViewById(R.id.frame2)


        val imageView : ImageView = view.findViewById(R.id.imageView)
        imageView.setClipToOutline(true)

        val imageView2 : ImageView = view.findViewById(R.id.imageView2)
        imageView2.setClipToOutline(true)

        frame1.setOnClickListener{
            Toast.makeText(getActivity(), "frame1이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }
        frame2.setOnClickListener{
            Toast.makeText(getActivity(), "frame2가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onClick(p0: View?) {
    }

    class SearchDBManager(
        context: Context,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE searchList ( gSearch CHAR(50) NOT NULL);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS searchList")
            onCreate(db)
        }
    }


//
//    private fun setOnClickListener() {
//        val btnSequence = binding.conslay.children
//        btnSequence.forEach { btn ->
//            btn.setOnClickListener(this)
//        }
//    }

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.imageView -> {
//                Toast.makeText(this, "Frame1이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
//            }
//            R.id.btn_second -> {
//                LogUtil.d(TAG, "Second Button")
//            }
//            R.id.btn_third -> {
//                LogUtil.d(TAG, "Third Button")
//            }
//        }
//    }


}