package com.example.myapplication.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.R
import com.example.myapplication.adapter.CommentAdapter
import com.example.myapplication.bean.Comment
import com.example.myapplication.bean.Stuff
import com.example.myapplication.bean.User
import com.example.myapplication.utils.Config
import kotlinx.android.synthetic.main.activity_idle_goods_detail_info.*
import java.util.*

class IdleGoodsDetailInfoActivity : AppCompatActivity() {
    private var stuff: Stuff? = null
    private var isCollected: Boolean = false
    private var isShopCar: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idle_goods_detail_info)
        supportActionBar?.hide()
        // 更改顶部菜单栏标题
        myTitleBar_idleGoodsDetailInfo.setTvTitleText("闲置物详情")
        myTitleBar_idleGoodsDetailInfo.tvForward?.visibility = View.GONE
        getStuffDetail()

        et_commemt.setOnEditorActionListener { v: TextView, actionId: Int, event: KeyEvent? ->
            Log.d("onEditorAction", "" + v.text)
            val temp = Stuff(stuff!!.name)
            temp.comments = stuff!!.comments
            var comment = Comment(v.text.toString())
            comment.username = Config.user?.nickname
            if (temp.comments == null) {
                temp.comments = mutableListOf(comment)
            } else {
                temp.comments.add(comment)
            }

            temp.update(stuff?.objectId, object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    if (p0 == null) {
                        Log.d("et_commemt", "评论成功")
                        getStuffDetail()
                        Toast.makeText(this@IdleGoodsDetailInfoActivity, "评论成功", Toast.LENGTH_SHORT)
                    } else {
                        Toast.makeText(
                            this@IdleGoodsDetailInfoActivity,
                            "评论失败${p0.message}",
                            Toast.LENGTH_SHORT
                        )
                    }
                }

            })
            false
        }
        ll_leave_comment?.setOnClickListener { v: View? ->
            et_commemt.requestFocus()
            val inputManager =
                et_commemt.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(et_commemt, 0)
        }
        tv_want.setOnClickListener {
            banWantDialog()
        }
        ll_collect.setOnClickListener {
            collectStuff()
        }

    }

    private fun getStuffDetail() {
        val bmobQuery = BmobQuery<Stuff>()
        bmobQuery.getObject(intent.getStringExtra("goodsId"), object : QueryListener<Stuff>() {
            override fun done(p0: Stuff?, p1: BmobException?) {
                stuff = p0
                stuff?.apply {
                    tv_desc.text = desc
                    tv_username.text = name
                }

                rv_comments.layoutManager = LinearLayoutManager(this@IdleGoodsDetailInfoActivity)
                rv_comments.adapter =
                    stuff?.comments?.let { CommentAdapter(this@IdleGoodsDetailInfoActivity, it) }
            }

        })
    }

    private fun collectStuff() {
        if (stuff == null) {
            Toast.makeText(this, "收藏失败，未获取商品详情", Toast.LENGTH_SHORT).show()
            return
        }
        if (Config.user == null) {
            Toast.makeText(this, "收藏失败，未获取用户信息", Toast.LENGTH_SHORT).show()
            return
        }
        val temp = User(Config.user!!.username)
        if (!isCollected) {
            temp.apply {
                if (collections == null) {
                    collections = mutableListOf(stuff!!)
                } else {
                    collections!!.add(stuff!!)
                }
                update(Config.user!!.objectId, object : UpdateListener() {
                    override fun done(p0: BmobException?) {
                        if (p0 != null) {
                            Log.w("collectStuff", "error  ${p0.message}")
                            return
                        }
                        Toast.makeText(
                            this@IdleGoodsDetailInfoActivity, "收藏成功",
                            Toast.LENGTH_SHORT
                        ).show()
                        iv_collect.setImageResource(R.drawable.ic_baseline_star_24)
                        isCollected = true
                    }
                })
            }
        } else {
            temp.apply {
                collections?.remove(stuff)
                update(Config.user!!.objectId, object : UpdateListener() {
                    override fun done(p0: BmobException?) {
                        if (p0 != null) {
                            Log.w("collectStuff", "error  ${p0.message}")
                            return
                        }
                        Toast.makeText(
                            this@IdleGoodsDetailInfoActivity, "取消收藏",
                            Toast.LENGTH_SHORT
                        ).show()
                        iv_collect.setImageResource(R.drawable.ic_baseline_star_border_24)
                        isCollected = false
                    }
                })
            }
        }

    }

    private fun banWantDialog() {
        MaterialDialog.Builder(this)
            .content(R.string.ban_want_hint)
            .positiveText(android.R.string.ok)
            .negativeText(android.R.string.cancel)
            .onPositive { _: MaterialDialog, _: DialogAction? ->
                Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show()
                if (stuff == null) {
                    Toast.makeText(this, "收藏失败，未获取商品详情", Toast.LENGTH_SHORT).show()
                    return@onPositive
                }
                if (Config.user == null) {
                    Toast.makeText(this, "收藏失败，未获取用户信息", Toast.LENGTH_SHORT).show()
                    return@onPositive
                }
                val temp = User(Config.user!!.username)
                if (!isShopCar) {
                    temp.apply {
                        if (shoppingCar == null) {
                            shoppingCar = mutableListOf(stuff!!)
                        } else {
                            shoppingCar!!.add(stuff!!)
                        }
                        update(Config.user!!.objectId, object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 != null) {
                                    Log.w("collectStuff", "error  ${p0.message}")
                                    return
                                }
                                Toast.makeText(
                                    this@IdleGoodsDetailInfoActivity, "添加购物车成功",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isShopCar = true
                            }
                        })
                    }
                } else {
                    temp.apply {
                        shoppingCar?.remove(stuff)
                        update(Config.user!!.objectId, object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 != null) {
                                    Log.w("collectStuff", "error  ${p0.message}")
                                    return
                                }
                                Toast.makeText(
                                    this@IdleGoodsDetailInfoActivity, "取消购物车",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isShopCar = false
                            }
                        })
                    }
                }
            }.show()
    }
}