package com.example.myapplication.activity.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.myapplication.R
import com.example.myapplication.adapter.AllStuffAdapter
import com.example.myapplication.adapter.StuffAdapter
import com.example.myapplication.bean.Stuff
import kotlinx.android.synthetic.main.activity_all_stuff.*
import java.util.*

class AllStuffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_stuff)
        actionBar?.hide()
        rv_goods.layoutManager = LinearLayoutManager(this)
        getStuffList()
    }

    private fun getStuffList() {
        val bmobQuery = BmobQuery<Stuff>()
        val result: MutableList<Stuff> = ArrayList()
        bmobQuery.findObjects(object : FindListener<Stuff>() {
            override fun done(list: List<Stuff>?, e: BmobException?) {
                if (list != null) {
                    result.addAll(list)
                    rv_goods.adapter = AllStuffAdapter(result, this@AllStuffActivity)
                }
            }
        })
    }

}