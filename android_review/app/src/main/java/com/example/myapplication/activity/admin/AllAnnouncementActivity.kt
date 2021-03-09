package com.example.myapplication.activity.admin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.R
import com.example.myapplication.adapter.AnnouncementAdapter
import com.example.myapplication.bean.Announce
import com.example.myapplication.utils.Config
import kotlinx.android.synthetic.main.activity_all_announcement.*

class AllAnnouncementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_announcement)
        actionBar?.hide()
        rv_announcement.layoutManager = LinearLayoutManager(this)
        getAnnounceList()
        add_announcement.setOnClickListener {
            MaterialDialog.Builder(this)
                .title("请输入公告")
                .input("", "") { _: MaterialDialog?, _: CharSequence? -> }
                .cancelable(false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive { dialog: MaterialDialog, _: DialogAction? ->
                    val name = dialog.inputEditText?.text.toString().trim()
                    val announce = Announce(name)
                    announce.save(object : SaveListener<String>() {
                        override fun done(objectId: String, e: BmobException?) {
                            if (e == null) {
                                Toast.makeText(
                                    this@AllAnnouncementActivity,
                                    "发布成功",
                                    Toast.LENGTH_SHORT
                                )
                            } else {
                                Toast.makeText(
                                    this@AllAnnouncementActivity,
                                    "发布失败${e.message}",
                                    Toast.LENGTH_SHORT
                                )
                            }
                        }
                    })
                    dialog.dismiss()
                }.show()
        }
        if (Config.user?.objectId != "Rflr222K") {
            add_announcement.visibility = View.GONE
        }
    }

    private fun getAnnounceList() {
        var announceList = mutableListOf<Announce>()
        val bmobQuery = BmobQuery<Announce>()
        bmobQuery.findObjects(object : FindListener<Announce>() {
            override fun done(list: List<Announce>?, e: BmobException?) {
                if (list != null && list.isNotEmpty()) {
                    announceList.addAll(list)
                    rv_announcement.adapter = AnnouncementAdapter(announceList)
                }
            }
        })
    }
}