package com.mengmaster.david.mengmaster.market.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengmaster.david.mengmaster.market.activity.FavorActivity;
import com.mengmaster.david.mengmaster.market.activity.LoginActivity;
import com.mengmaster.david.mengmaster.market.activity.MainActivity;
import com.mengmaster.david.mengmaster.market.activity.OrdersActivity;
import com.mengmaster.david.mengmaster.market.activity.R;
import com.mengmaster.david.mengmaster.market.utils.Constants;

/**
 * Created by dell on 2016/11/26.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private View mViewNotLogined;    //没有登录的RelativeLayout布局
    private View mViewLogined;       //登录后的RelativeLayout布局
    private TextView mTvUid;         //账号文字
    private ImageView mImgUserIcon;  //京东狗图标
    private String uid;
    private String icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        //将界面布局文件加载到Activity中来操作
        layout = inflater.inflate(R.layout.fragment_mine, container, false);
        //初始化控件
        initView();
        //设置事件监听
        setOnListener();

        return layout;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mViewNotLogined = layout.findViewById(R.id.layout_not_logined);
        mViewLogined = layout.findViewById(R.id.layout_logined);
        mTvUid = (TextView) layout.findViewById(R.id.tv_uid);
        mImgUserIcon = (ImageView) layout.findViewById(R.id.user_icon);
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        layout.findViewById(R.id.layout_mine_order).setOnClickListener(this);
        //layout.findViewById(R.id.layout_mine_wallet).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_messages).setOnClickListener(this);
        layout.findViewById(R.id.personal_login_button).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_feedback).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_collects).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_history).setOnClickListener(this);
        //layout.findViewById(R.id.layout_mine_appoint).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_account_center).setOnClickListener(this);
        //layout.findViewById(R.id.layout_mine_service_manager).setOnClickListener(this);
        layout.findViewById(R.id.layout_mine_discuss).setOnClickListener(this);
        //layout.findViewById(R.id.layout_mine_android_my_jd_assitant).setOnClickListener(this);
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_login_button: //登录
                login();
                break;
            case R.id.layout_mine_feedback: //意见反馈
                //new FeedbackAgent(getActivity()).startFeedbackActivity();
                break;
         /*   case R.id.layout_mine_android_my_jd_assitant: //贴心服务
                *//*Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("direction", 5);
                startActivity(intent);*//*
                break;*/
            case R.id.layout_mine_order: //全部订单
                startActivity(new Intent(getActivity(), OrdersActivity.class));
                break;
        /*    case R.id.layout_mine_wallet: //我的钱包
                //startActivity(new Intent(getActivity(), PurseActivity.class));
                break;*/
            case R.id.layout_mine_messages: //我的消息
                //startActivity(new Intent(getActivity(), MessageCenterActivity.class));
                break;
            case R.id.layout_mine_collects: //我的关注
                startActivity(new Intent(getActivity(), FavorActivity.class));
                break;
            case R.id.layout_mine_history: //浏览记录
                //startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
          /*  case R.id.layout_mine_appoint: //我的预约
              *//*  Intent intent3 = new Intent(getActivity(), WebActivity.class);
                intent3.putExtra("direction", 9);
                startActivity(intent3);*//*
                break;*/
            case R.id.layout_mine_account_center: //账户与安全
             /*   Intent intent4 = new Intent(getActivity(), WebActivity.class);
                intent4.putExtra("direction", 12);
                startActivity(intent4);*/
                break;
      /*      case R.id.layout_mine_service_manager: //服务管家
              *//*  Intent intent5 = new Intent(getActivity(), WebActivity.class);
                intent5.putExtra("direction", 10);
                startActivity(intent5);*//*
                break;*/
            case R.id.layout_mine_discuss: //评价商品
               /* Intent intent6 = new Intent(getActivity(), WebActivity.class);
                intent6.putExtra("direction", 11);
                startActivity(intent6);*/
                break;
            default:
                break;
        }
    }

    /**
     * 接收回传
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.INTENT_KEY.LOGIN_REQUEST_CODE) {
            if (resultCode == Constants.INTENT_KEY.LOGIN_RESULT_SUCCESS_CODE) {
                /*SharedPreferences sp = getActivity().getSharedPreferences(
                        "login_type", Context.MODE_PRIVATE);
                int type = sp.getInt("login_type", 0);
                String uid = "";
                String icon = "";
                switch (type) {
                    case 1:
                        uid = data.getStringExtra("uid");
                        break;
                    case 2:
                        uid = data.getStringExtra("screen_name");
                        icon = data.getStringExtra("profile_image_url");
                        //UILUtils.displayImage(getActivity(), icon, mImgUserIcon);
                        break;

                    default:
                        break;
                }
                mTvUid.setText(uid);
                mViewNotLogined.setVisibility(View.GONE);
                mViewLogined.setVisibility(View.VISIBLE);
                // 将登录结果设置给MainActivity
                MainActivity activity = (MainActivity) getActivity();
                activity.setIsLogined(true, uid, icon);*/
            }
        } else if (requestCode == Constants.INTENT_KEY.REQUEST_MOREACTIVITY) {

        }
    }

    /**
     * 登录
     */
    private void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, Constants.INTENT_KEY.LOGIN_REQUEST_CODE);
    }
}
