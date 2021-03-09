package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.activity.CategoryStuffActivity
import com.example.myapplication.bean.Category

class CategoryAdapter(
    private val context: Context,
    private val categoryList: MutableList<Category>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val commonCategoryIconList = arrayOf(
        R.drawable.ic_other_function_01,
        R.drawable.ic_other_function_02, R.drawable.ic_other_function_03,
        R.drawable.ic_other_function_04, R.drawable.ic_other_function_05,
    )

    class ViewHolder(categoryItemView: View) : RecyclerView.ViewHolder(categoryItemView) {
        var image = categoryItemView.findViewById<View>(R.id.category_img) as ImageView
        var name = categoryItemView.findViewById<View>(R.id.category_name) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = categoryList[position].categoryName
        val size = commonCategoryIconList.size
        try {
            Glide.with(context).load(commonCategoryIconList[position % size]).into(holder.image)
        } catch (e: Exception) {
            Log.w("onBindViewHolder", "error.${e.message}")
        }
        holder.image.setOnClickListener {
            val intent = Intent(context, CategoryStuffActivity::class.java)
            intent.putExtra("categoryName", categoryList[position].categoryName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}