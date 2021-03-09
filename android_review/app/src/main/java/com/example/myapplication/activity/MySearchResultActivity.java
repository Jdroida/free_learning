package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.bean.Category;
import com.example.myapplication.bean.Stuff;
import com.example.myapplication.domain.DoMainStuff;
import com.example.myapplication.myview.MyTitleBar;
import com.example.myapplication.adapter.MyCollectedListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MySearchResultActivity extends AppCompatActivity {
    private MyTitleBar mySearchResultMyTitleBar;
    private RecyclerView mySearchResultRecyclerView;
    private final List<Stuff> mySearchResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_search_result);

        mySearchResultMyTitleBar = findViewById(R.id.myTitleBar_mySearchResult);
        mySearchResultRecyclerView = findViewById(R.id.rv_mySearchResult);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        mySearchResultMyTitleBar.setTvTitleText("搜索结果");
        mySearchResultMyTitleBar.getTvForward().setVisibility(View.INVISIBLE);
        Toast.makeText(this, "因模糊查询为后端云收费功能，本版仅支持精确查询", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String searchResult = intent.getStringExtra("searchResult");

        Log.d("searchResult", "" + searchResult);

        LinearLayoutManager manager = new LinearLayoutManager(MySearchResultActivity.this);
        mySearchResultRecyclerView.setLayoutManager(manager);
        MyCollectedListAdapter myCollectedListAdapter = new MyCollectedListAdapter(mySearchResultList);

        mySearchResultRecyclerView.setAdapter(myCollectedListAdapter);
        getStuffList();
    }


    private void getStuffList() {
        BmobQuery<Stuff> bmobQuery = new BmobQuery<>();
        //模糊查询仅对付费用户开放
        bmobQuery.addWhereEqualTo("name", getIntent().getStringExtra("stuff_name"))
                .findObjects(new FindListener<Stuff>() {
                    @Override
                    public void done(List<Stuff> list, BmobException e) {
                        if (list != null && list.size() > 0) {
                            mySearchResultList.addAll(list);
                            mySearchResultRecyclerView.setAdapter(new MyCollectedListAdapter(mySearchResultList));
                        }
                    }
                });
    }

}