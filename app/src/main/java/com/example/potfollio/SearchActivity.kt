package com.example.potfollio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class SearchActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    lateinit var frame1 : FrameLayout
    lateinit var frame2 : FrameLayout
    lateinit var layout : RelativeLayout

    lateinit var search_bar : SearchView

    lateinit var search_text : TextView

    lateinit var sqlDB: SQLiteDatabase
    lateinit var sdbManager: SearchDBManager

    lateinit var search_recyclerview : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)

        frame1 = findViewById(R.id.frame1)
        frame2 = findViewById(R.id.frame2)
        search_text = findViewById(R.id.search_text)
        //search_del = findViewById(R.id.search_del)
        search_bar = findViewById(R.id.search_bar)
        //search_recyclerview = findViewById(R.id.search_recyclerview)

        sdbManager = SearchDBManager(this, "searchList", null, 2)
        sqlDB = sdbManager.writableDatabase

        frame1.setOnClickListener{
            Toast.makeText(this, "Frame1이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
        }

        frame2.setOnClickListener{
            Toast.makeText(this, "Frame2가 클릭되었습니다.", Toast.LENGTH_SHORT).show()
        }

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                sqlDB = sdbManager.writableDatabase
                sqlDB.execSQL(
                    "INSERT INTO searchList VALUES ( '"
                            + query.toString() + "');"
                )
                //Toast.makeText(applicationContext, "[검색버튼클릭] 검색어 = "+query.toString(), Toast.LENGTH_LONG).show();

                sqlDB.close()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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
}