package com.mengmaster.david.mengmaster.market.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentTabHost;

import com.mengmaster.david.mengmaster.market.app.SysApplication;
import com.mengmaster.david.mengmaster.market.fragment.CartFragment;
import com.mengmaster.david.mengmaster.market.fragment.CategoryFragment;
import com.mengmaster.david.mengmaster.market.fragment.FindFragment;
import com.mengmaster.david.mengmaster.market.fragment.HomeFragment;
import com.mengmaster.david.mengmaster.market.fragment.MineFragment;

/**
 * Created by dell on 2016/11/26.
 */
public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private boolean isFromFavor;    //是否从关注界面跳转来
    private boolean isFromDetail;   //是否从详情界面跳转来
    private TextView mTvNumInCart;	//购物车商品数
    private boolean isBack; // 是否连续点击返回键
    private boolean isLogined; // 是否已登录

    //Tab界面
    private Class[] mFragments = new Class[] {
            HomeFragment.class,
            CategoryFragment.class,
            CartFragment.class,
            MineFragment.class };
    //Tab数组
    private int[] mTabSelectors = new int[] {
            R.drawable.main_bottom_tab_home,
            R.drawable.main_bottom_tab_category,
            R.drawable.main_bottom_tab_cart,
            R.drawable.main_bottom_tab_mine };
    //
    private String[] mTabSpecs = new String[] {
            "home",
            "category",
            "cart",
            "mine" };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        //Initialise the tab-host
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        addTab();
    }

    /**
     * 添加Tab
     *
     */
    private void addTab() {
        for (int i = 0; i < 4; i++) {
            //布局文件解析为xml
            View tabIndicator = getLayoutInflater().inflate(R.layout.tab_indicator, null);
            //得到布局文件的ImageView & 设置ImageView的图片
            ImageView imageView = (ImageView) tabIndicator.findViewById(R.id.imageView1);
            imageView.setImageResource(mTabSelectors[i]);
            if (i == 2) {
                mTvNumInCart = (TextView) tabIndicator.findViewById(R.id.textView1);
            }
            //Add a tab to the tabHost
            mTabHost.addTab(mTabHost.newTabSpec(mTabSpecs[i]).setIndicator(tabIndicator), mFragments[i], null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果是从关注界面跳转来，则转到HomePage
        // 不能在onReceive()中直接设置，否则会出现IllegalStateException: Can not perform this
        // action after onSaveInstanceState
        if (isFromFavor) {
            mTabHost.setCurrentTab(0);
            isFromFavor = false;
        } else if (isFromDetail) {
            mTabHost.setCurrentTab(3);
            isFromDetail = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
