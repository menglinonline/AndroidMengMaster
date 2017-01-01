package com.mengmaster.david.mengmaster.market.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dell on 2016/12/28.
 */
public class OrdersActivity extends Activity implements View.OnClickListener {
    private TextView mTextView;   //"实物订单"文字
    private TextView mTextView2;  //“其他订单”文字
    private View indicator;       //”全部订单“布局
    private View indicator2;      //“其他订单”布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        //初始化控件和布局
        initView();

        //设置事件监听
        setOnListener();
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.layout_order1).setOnClickListener(this);
        findViewById(R.id.layout_order2).setOnClickListener(this);
    }

    /**
     * 初始化控件和布局
     */
    private void initView() {
        mTextView = (TextView) findViewById(R.id.TextView01);
        mTextView2 = (TextView) findViewById(R.id.TextView02);
        indicator = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_order1:
                indicator.setVisibility(View.VISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                mTextView.setTextColor(Color.RED);
                mTextView2.setTextColor(getResources().getColor(R.color.dark));
                break;
            case R.id.layout_order2:
                indicator2.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.INVISIBLE);
                mTextView2.setTextColor(Color.RED);
                mTextView.setTextColor(getResources().getColor(R.color.dark));
                break;
            default:
                break;
        }
    }
}
