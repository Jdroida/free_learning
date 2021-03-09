package com.example.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.admin.AllAnnouncementActivity;
import com.example.myapplication.bean.User;
import com.example.myapplication.domain.DoMainUser;
import com.example.myapplication.myview.MyItemGroup;
import com.example.myapplication.myview.MyTitleBar;
import com.example.myapplication.service.UpDateUserInfo;
import com.example.myapplication.utils.Config;
import com.example.myapplication.utils.MD5Util;
import com.example.myapplication.utils.SharePreferencesUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyDetailInfoActivity extends AppCompatActivity {

    private MyTitleBar myInfoMyTitleBar;
    private TextView saveInfo;

    private MyItemGroup nicknameMyItemGroup;
    private MyItemGroup userPhoneNumMyItemGroup, addressMyItemGroup, announcementMyItemGroup, passwordMyItemGroup;
    private SharedPreferences userinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deatil_info);

        saveInfo = findViewById(R.id.tv_forward);
        myInfoMyTitleBar = findViewById(R.id.myTitleBar_myInfo);
        nicknameMyItemGroup = findViewById(R.id.myItemGroup_nickName);
        userPhoneNumMyItemGroup = findViewById(R.id.myItemGroup_userPhoneNum);
        addressMyItemGroup = findViewById(R.id.user_address);
        passwordMyItemGroup = findViewById(R.id.edit_password);
        announcementMyItemGroup = findViewById(R.id.system_announcement);

        announcementMyItemGroup.setOnClickListener(v -> startActivity(new Intent(MyDetailInfoActivity.this, AllAnnouncementActivity.class)));

        saveInfo.setOnClickListener(v -> {
            User temp = new User(Config.INSTANCE.getUser().getUsername());
            temp.setObjectId(Config.INSTANCE.getUser().getObjectId());
            temp.setNickname(nicknameMyItemGroup.getContentTextViewText());
            temp.setPhoneNum(userPhoneNumMyItemGroup.getContentTextViewText());
            temp.setAddress(addressMyItemGroup.getContentTextViewText());
            temp.setPassword(MD5Util.getMD5Str(passwordMyItemGroup.getContentTextViewText()));
            temp.update(temp.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(MyDetailInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        Config.INSTANCE.setUser(temp);
                        finish();
                    } else {
                        Toast.makeText(MyDetailInfoActivity.this, "更新失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        myInfoMyTitleBar.setTvTitleText("编辑资料");

        // 取出本地缓存的个人信息填入页面
        userinformation = getSharedPreferences(SharePreferencesUtils.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUserId = userinformation.getString("userId", null);
        String localUserLoginId = userinformation.getString("userLoginId", null);
        String localUserName = userinformation.getString("userName", null);
        String localUserEmail = userinformation.getString("userEmail", null);
        String localUserPhoneNum = userinformation.getString("userPhoneNum", null);
        String localUserRegisterDate = userinformation.getString("userRegisterDate", null);

        if (localUserName != null) {
            nicknameMyItemGroup.setContentTextViewText(localUserName);
        }
        if (localUserPhoneNum != null) {
            userPhoneNumMyItemGroup.setContentTextViewText(localUserPhoneNum);
        }


        nicknameMyItemGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText usernameEditText = new EditText(MyDetailInfoActivity.this);
                usernameEditText.setText(nicknameMyItemGroup.getContentTextViewText());
                usernameEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (usernameEditText.getText().toString().isEmpty()) {
                            usernameEditText.setError("用户名不能为空！");
                        }
                    }
                });

                //创建弹窗
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyDetailInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("更改用户名为").setView(usernameEditText).setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 将信息更新至文本框中，待最终按下保存按钮再更新至本地共享文件并更新数据库
                        if (!usernameEditText.getText().toString().isEmpty() && usernameEditText.getError() == null) {
                            nicknameMyItemGroup.setContentTextViewText(usernameEditText.getText().toString());
                            Toast.makeText(MyDetailInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyDetailInfoActivity.this, "用户密码格式错误请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });


        userPhoneNumMyItemGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText phonenumEditText = new EditText(MyDetailInfoActivity.this);
                phonenumEditText.setText(userPhoneNumMyItemGroup.getContentTextViewText());
                phonenumEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        if (!phonenumEditText.getText().toString().matches("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                            phonenumEditText.setError("手机号码格式有误！");
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!phonenumEditText.getText().toString().matches("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$")) {
                            phonenumEditText.setError("手机号码格式有误！");
                        }
                    }
                });

                //创建弹窗
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyDetailInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("更改用户联系方式为").setView(phonenumEditText).setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 将信息更新至文本框中，待最终按下保存按钮再更新至本地共享文件并更新数据库
                        if (!phonenumEditText.getText().toString().isEmpty() && phonenumEditText.getError() == null) {
                            userPhoneNumMyItemGroup.setContentTextViewText(phonenumEditText.getText().toString());
                            Toast.makeText(MyDetailInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyDetailInfoActivity.this, "用户联系方式格式错误请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        addressMyItemGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText addressEditText = new EditText(MyDetailInfoActivity.this);
                addressEditText.setText(userPhoneNumMyItemGroup.getContentTextViewText());
                addressEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                //创建弹窗
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyDetailInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("更改地址为").setView(addressEditText).setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 将信息更新至文本框中，待最终按下保存按钮再更新至本地共享文件并更新数据库
                        if (!addressEditText.getText().toString().isEmpty() && addressEditText.getError() == null) {
                            userPhoneNumMyItemGroup.setContentTextViewText(addressEditText.getText().toString());
                            Toast.makeText(MyDetailInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyDetailInfoActivity.this, "请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        passwordMyItemGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText passwordEditText = new EditText(MyDetailInfoActivity.this);
                passwordEditText.setText(userPhoneNumMyItemGroup.getContentTextViewText());
                passwordEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!passwordEditText.getText().toString().matches("^(\\w){8,20}$")) {
                            passwordEditText.setError("密码长度介于8到20位之间，且由字母或数字构成！");
                        }
                    }
                });

                //创建弹窗
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyDetailInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("更改密码为").setView(passwordEditText).setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 将信息更新至文本框中，待最终按下保存按钮再更新至本地共享文件并更新数据库
                        if (!passwordEditText.getText().toString().isEmpty() && passwordEditText.getError() == null) {
                            userPhoneNumMyItemGroup.setContentTextViewText(passwordEditText.getText().toString());
                            Toast.makeText(MyDetailInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyDetailInfoActivity.this, "请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}