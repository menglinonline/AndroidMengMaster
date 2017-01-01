package com.mengmaster.david.mengmaster.market.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;

import android.widget.CompoundButton.OnCheckedChangeListener;


import com.mengmaster.david.mengmaster.market.service.UserService;
import com.mengmaster.david.mengmaster.market.service.UserServiceImpl;
import com.mengmaster.david.mengmaster.market.service.UserServiceRulesException;

import java.lang.ref.WeakReference;

/**
 * Created by david on 2016/11/22.
 */
public class LoginActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    //Widget
    private ToggleButton mTgBtnShowPsw;
    private EditText mEditPsw;
    private EditText mEditUid;
    private ImageView mBtnClearUid;
    private ImageView mBtnClearPsw;
    private Button mBtnLogin;
    private ImageView mImgBack;
    private TextView mRuickRegister;

    //自定义一个Handler
    private MyHandler handler = new MyHandler(this);
    private static ProgressDialog dialog;

    //Service
    private UserService userService = new UserServiceImpl();

    //Constant
    private static final int FLAG_LOGIN_SUCCESSFULLY = 1;//登录成功或失败标记 0失败/1成功
    private static final int FLAG_LOGIN_FAILED  = 0;//登录成功或失败标记 0失败/1成功
    private static final String MSG_LOGIN_ERROR = "登录出错";
    private static final String MSG_LOGIN_SUCCESSFULLY = "登录成功";
    public static final String MSG_LOGIN_FAILED = "用户名或密码错误";
    public static final String MSG_SERVER_ERROR = "请求服务器错误";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initViews();
        //设置控件的监听事件
        setOnListener();
    }

    /**
	 * 自定义一个Handler
     * 子线程与主线程（UI线程）的通信机制Handler
	 */
    public class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(LoginActivity activity){
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(dialog != null){
                dialog.dismiss();
            }

            //接收子线程发送过来的消息
            int flag = msg.what;
            switch(flag)
            {
                case FLAG_LOGIN_FAILED:
                    String errorMsg = (String)msg.getData().getSerializable("ErrorMsg");
                    //mActivity持有LoginActivity
                    ((LoginActivity)mActivity.get()).showTip(errorMsg);
                    break;
                case FLAG_LOGIN_SUCCESSFULLY:
                    ((LoginActivity)mActivity.get()).showTip(MSG_LOGIN_SUCCESSFULLY);
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);

                    break;
            }
        }
    }

    /**
	 * 弹出提示框
	 */
    private void showTip(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录
     */
    private void login()
    {
        //得到输入的用户名和密码
        final String userName = mEditUid.getText().toString();
        final String pwd = mEditPsw.getText().toString();

        Toast.makeText(this, "登录名：" + userName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "登录密码：" + pwd, Toast.LENGTH_SHORT).show();

        /**
         * loading
         */
        if(dialog == null){
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle("请等待");
            dialog.setMessage("登录中...");
            dialog.setCancelable(false);
            dialog.show();
        }

        /**
         * 子线程
         */
        Thread thead = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    userService.userLogin(userName, pwd);
                    handler.sendEmptyMessage(FLAG_LOGIN_SUCCESSFULLY);

                }
                catch(UserServiceRulesException e){//业务异常
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", e.getMessage());
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
                catch(Exception e){//其他异常
                    e.printStackTrace();
                    Message msg = new Message();
                    Bundle data =new Bundle();
                    data.putSerializable("ErrorMsg", MSG_LOGIN_ERROR);
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }
        });
        thead.start();
    }

    /**
     * 设置控件的监听事件
     */
    private void setOnListener() {
        //用户名文本改变监听事件
        mEditUid.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEditUid.getText().toString().length() > 0) {
                    mBtnClearUid.setVisibility(View.VISIBLE);
                    if (mEditPsw.getText().toString().length() > 0) {
                        mBtnLogin.setEnabled(true);
                    } else {
                        mBtnLogin.setEnabled(false);
                    }
                } else {
                    mBtnLogin.setEnabled(false);
                    mBtnClearUid.setVisibility(View.INVISIBLE);
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

        //密码文本改变监听事件
        mEditPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEditPsw.getText().toString().length() > 0) {
                    mBtnClearPsw.setVisibility(View.VISIBLE);
                    if (mEditUid.getText().toString().length() > 0) {
                        mBtnLogin.setEnabled(true);
                    } else {
                        mBtnLogin.setEnabled(false);
                    }
                } else {
                    mBtnLogin.setEnabled(false);
                    mBtnClearPsw.setVisibility(View.INVISIBLE);
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

        mBtnLogin.setOnClickListener(this);
        mBtnClearUid.setOnClickListener(this);
        mBtnClearPsw.setOnClickListener(this);
        mTgBtnShowPsw.setOnCheckedChangeListener(this);
        mImgBack.setOnClickListener(this);
        //findViewById(R.id.btn_login_wb).setOnClickListener(this);
        mRuickRegister.setOnClickListener(this);
    }

    /**
     * 单击事件
     * @param @控件id
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录
                login();
                break;
            case R.id.img_back:	//返回
                LoginActivity.this.finish();
                break;

            case R.id.img_login_clear_uid: //清除用户名
                clearText(mEditUid);
                break;
            case R.id.img_login_clear_psw:	//清除密码
                clearText(mEditPsw);
                break;
            case R.id.tv_quick_sign_up:	//快速注册
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 清空控件文本
     */
    private void clearText(EditText edit) {
        edit.setText("");
    }

    /**
     * 显示密码/隐藏密码
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //显示密码
            mEditPsw.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance());
        } else {
            //隐藏密码
            mEditPsw.setTransformationMethod(
                    PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 初始化控件
     */
    private void initViews()
    {
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mEditUid = (EditText) findViewById(R.id.edit_uid);
        mEditPsw = (EditText) findViewById(R.id.edit_psw);
        mBtnClearUid = (ImageView) findViewById(R.id.img_login_clear_uid);
        mBtnClearPsw = (ImageView) findViewById(R.id.img_login_clear_psw);
        mTgBtnShowPsw = (ToggleButton) findViewById(R.id.tgbtn_show_psw);
        mImgBack= (ImageView) findViewById(R.id.img_back);
        mRuickRegister= (TextView) findViewById(R.id.tv_quick_sign_up);
        //findViewById(R.id.btn_login_wb).setOnClickListener(this);
    }
}
