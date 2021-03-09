package com.example.myapplication.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.myapplication.R
import com.example.myapplication.adapter.StuffAdapter
import com.example.myapplication.bean.Stuff
import kotlinx.android.synthetic.main.activity_category_stuff.*
import java.util.*

class CategoryStuffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_stuff)
        actionBar?.hide()
        category_stuff.layoutManager = LinearLayoutManager(this)
        getStuffList(intent.getStringExtra("categoryName"))
    }

    private fun getStuffList(categoryName: String?): List<Stuff>? {
        val result: MutableList<Stuff> = ArrayList()
        val bmobQuery = BmobQuery<Stuff>()
        bmobQuery.addWhereEqualTo("category", categoryName)
            .findObjects(object : FindListener<Stuff>() {
                override fun done(list: List<Stuff>?, e: BmobException?) {
                    if (list != null) {
                        result.addAll(list)
                        category_stuff.adapter = StuffAdapter(result, this@CategoryStuffActivity)
                    }
                    if (e != null)
                        Log.w("getStuffList", "  ${e.message}")
                }
            })
        return result
    }

}