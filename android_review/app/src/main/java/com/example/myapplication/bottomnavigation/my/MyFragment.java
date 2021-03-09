package com.example.myapplication.bottomnavigation.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.BottomNavigationActivity;
import com.example.myapplication.activity.MyCollectedListActivity;
import com.example.myapplication.activity.MyDetailInfoActivity;
import com.example.myapplication.activity.ShopCarListActivity;
import com.example.myapplication.utils.Config;
import com.example.myapplication.utils.SharePreferencesUtils;

public class MyFragment extends Fragment implements View.OnClickListener {

    private Button logoutButton;
    private Button showMyInfoDetailButton;
    private ImageView myCollectionImageView;
    private TextView myCollectionTextView, nickname;
    private LinearLayout myUserInfoLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);

        showMyInfoDetailButton = root.findViewById(R.id.btn_showMyInfoDetail);
        logoutButton = root.findViewById(R.id.btn_logout);
        nickname = root.findViewById(R.id.tv_myUserName);
        myCollectionImageView = root.findViewById(R.id.iv_myCollection);
        myCollectionTextView = root.findViewById(R.id.tv_myCollection);
        myUserInfoLinearLayout = root.findViewById(R.id.ly_myUserInfo);


        // 查看我收藏的事件绑定
        myCollectionImageView.setOnClickListener(this);
        myCollectionTextView.setOnClickListener(this);
        root.findViewById(R.id.iv_myBrowse).setOnClickListener(this);
        root.findViewById(R.id.tv_myBrowse).setOnClickListener(this);
        root.findViewById(R.id.tv_mySold).setOnClickListener(this);
        root.findViewById(R.id.iv_mySold).setOnClickListener(this);
        root.findViewById(R.id.iv_myBought).setOnClickListener(this);
        root.findViewById(R.id.tv_myBought).setOnClickListener(this);

        // 简单个人信息展示点击进入详细信息展示页
        myUserInfoLinearLayout.setOnClickListener(this);
        showMyInfoDetailButton.setOnClickListener(this);

        // 退出登录事件绑定
        logoutButton.setOnClickListener(v -> {
            Config.INSTANCE.setUser(null);
            SharePreferencesUtils.clear(getContext());
            Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();
            ((BottomNavigationActivity) getActivity()).navigateHome();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        nickname.setText(Config.INSTANCE.getUser().getNickname());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_showMyInfoDetail:
                // 简单个人信息展示点击进入详细信息展示页
                intent = new Intent(getContext(), MyDetailInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myCollection:
            case R.id.iv_myCollection:
                intent = new Intent(getContext(), MyCollectedListActivity.class);
                intent.putExtra("userId", SharePreferencesUtils.getParam(getContext(), "userId", "").toString());
                startActivity(intent);
                break;
            case R.id.iv_myBrowse:
            case R.id.tv_myBrowse:
                intent = new Intent(getContext(), ShopCarListActivity.class);
                intent.putExtra("userId", SharePreferencesUtils.getParam(getContext(), "userId", "").toString());
                startActivity(intent);
                break;
            case R.id.tv_mySold:
            case R.id.iv_mySold:
            case R.id.iv_myBought:
            case R.id.tv_myBought:
                Toast.makeText(getContext(), R.string.cannot_order_hint, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
