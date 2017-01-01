package com.mengmaster.david.mengmaster.market.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by dell on 2016/11/22.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText mEditMobilePhone;
    private ImageView mImgBack;
    private ImageView mBtnClearMobilePhone;
    private Button mBtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_register);

        //初始化控件
        initViews();

        //设置控件的监听事件
        setOnListener();
    }

    /**
     * 设置控件的监听事件
     */
    private void setOnListener() {
        //手机文本改变监听事件
        mEditMobilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEditMobilePhone.getText().toString().length() > 0) {
                    mBtnClearMobilePhone.setVisibility(View.VISIBLE);
                    mEditMobilePhone.setEnabled(true);
                    setSignable();
                } else {
                    mBtnClearMobilePhone.setVisibility(View.INVISIBLE);
                    mBtnClearMobilePhone.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mBtnRegister.setOnClickListener(this);
        mBtnClearMobilePhone.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
     /*   TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSignable();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mEditMobilePhone.addTextChangedListener(watcher);*/
    }

    /**
     * 是否符合注册条件
     */
    private void setSignable() {
            if (mEditMobilePhone.getText().toString().length() > 5) {
                mBtnRegister.setEnabled(true);
            } else {
                mBtnRegister.setEnabled(false);
            }
    }

    /**
     * 初始化控件
     */
    private void initViews()
    {
        mImgBack= (ImageView) findViewById(R.id.img_back);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEditMobilePhone = (EditText) findViewById(R.id.edit_mobilephone);
        mBtnClearMobilePhone = (ImageView) findViewById(R.id.img_register_clear_mobilephone);
    }


    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register://注册
                register();
                break;
            case R.id.img_back:	//返回
                finish();
                break;
            case R.id.img_register_clear_mobilephone:	//清除密码
                clearText(mEditMobilePhone);
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register()
    {
        Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 清空控件文本
     */
    private void clearText(EditText edit) {
        edit.setText("");
    }
}
