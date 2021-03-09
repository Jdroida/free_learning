package com.example.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.admin.AdminActivity;
import com.example.myapplication.bean.User;
import com.example.myapplication.bottomnavigation.add.AddFragment;
import com.example.myapplication.bottomnavigation.home.HomeFragment;
import com.example.myapplication.bottomnavigation.my.MyFragment;
import com.example.myapplication.myview.FixFragmentNavigator;
import com.example.myapplication.utils.Config;
import com.example.myapplication.utils.SharePreferencesUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class BottomNavigationActivity extends BaseActivity {
    String userId;
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CODE = 267;
    NavController navController;
    BottomNavigationView navView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("AddFragment", "onRequestPermissionsResult: permission granted");
        } else {
            Log.i("AddFragment", "onRequestPermissionsResult: permission denied");
            Toast.makeText(this, "You Denied Permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_navigation);
        Bmob.initialize(this, "acb82b8fb5c6b9cbc68c4464959681f7");
        navView = findViewById(R.id.nav_view);
        /*申请读取存储的权限*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMISSION_WRITE_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERMISSION_WRITE_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        }
        userId = (String) SharePreferencesUtils.getParam(this, "userId", "");
        if (!TextUtils.isEmpty(userId)) {
            //查找Person表里面id为6b6c11c537的数据
            BmobQuery<User> bmobQuery = new BmobQuery<User>();
            bmobQuery.getObject(userId, new QueryListener<User>() {
                @Override
                public void done(User object, BmobException e) {
                    if (e == null) {
                        Config.INSTANCE.setUser(object);
                    }
                }
            });
        }
        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        //获取页面容器NavHostFragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //获取导航控制器
        navController = NavHostFragment.findNavController(fragment);
        //创建自定义的Fragment导航器
        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
        //获取导航器提供者
        NavigatorProvider provider = navController.getNavigatorProvider();
        //把自定义的Fragment导航器添加进去
        provider.addNavigator(fragmentNavigator);
        //手动创建导航图
        NavGraph navGraph = initNavGraph(provider, fragmentNavigator);
        //设置导航图
        navController.setGraph(navGraph);
        //底部导航设置点击事件
        navView.setOnNavigationItemSelectedListener(item -> {
            userId = (String) SharePreferencesUtils.getParam(this, "userId", "");
            if (item.getItemId() == R.id.navigation_my) {
                if (TextUtils.isEmpty(userId)) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return false;
                } else if (userId.equals("Rflr222K")) {//这是我手动创建的管理员ID 账号admin Aa111111
                    startActivity(new Intent(this, AdminActivity.class));
                    return false;
                } else {
                    navController.navigate(item.getItemId());
                }
            } else {
                navController.navigate(item.getItemId());
            }
            return true;
        });
    }

    public void navigateHome() {
        navView.setSelectedItemId(R.id.navigation_home);
        navController.navigate(R.id.navigation_home);
    }


    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //用自定义的导航器来创建目的地
        FragmentNavigator.Destination homeFragment = fragmentNavigator.createDestination();
        homeFragment.setId(R.id.navigation_home);
        homeFragment.setClassName(HomeFragment.class.getCanonicalName());
        homeFragment.setLabel(getResources().getString(R.string.title_home));
        navGraph.addDestination(homeFragment);

        FragmentNavigator.Destination addFragment = fragmentNavigator.createDestination();
        addFragment.setId(R.id.navigation_add);
        addFragment.setClassName(AddFragment.class.getCanonicalName());
        addFragment.setLabel(getResources().getString(R.string.title_add));
        navGraph.addDestination(addFragment);

        FragmentNavigator.Destination myFragment = fragmentNavigator.createDestination();
        myFragment.setId(R.id.navigation_my);
        myFragment.setClassName(MyFragment.class.getCanonicalName());
        myFragment.setLabel(getResources().getString(R.string.title_my));
        navGraph.addDestination(myFragment);

        navGraph.setStartDestination(R.id.navigation_home);
        return navGraph;
    }


}