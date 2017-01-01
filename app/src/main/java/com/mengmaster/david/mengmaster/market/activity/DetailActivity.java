package com.mengmaster.david.mengmaster.market.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.lib.uil.ToastUtils;
import com.mengmaster.david.mengmaster.market.entity.GoodsInfo;
import com.mengmaster.david.mengmaster.market.entity.InCart;
import com.mengmaster.david.mengmaster.market.fragment.DetailBannerItemFragment;
import com.mengmaster.david.mengmaster.market.utils.Constants;
import com.mengmaster.david.mengmaster.market.utils.DBUtils;
import com.mengmaster.david.mengmaster.market.utils.NumberUtils;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/11/28.
 */
public class DetailActivity extends FragmentActivity implements View.OnClickListener {
    private int[] mBanner = new int[]{R.drawable.ip1, R.drawable.ip2,
            R.drawable.ip3, R.drawable.ip4};                                //图片展示
    private TextView mTvCount;                                              //图片页数文字
    private TextView mTvInCartNum;                                          //购物车商品数量
    private ImageView mImgFavor;                                            //收藏图片
    private TextView mTvCollect;                                            //关注文字
    private GoodsInfo mGoodsInfo;                                           //商品详情对象
    private InCart mInCart;                                                 //购物车对象
    private ArrayList<GoodsInfo> historyList = new ArrayList<GoodsInfo>();  //历史记录
    private ViewPager mPager;                                               //商品图片展示ViewPager
    private MenuDrawer mDrawer;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        //initMenu();        //初始化浏览记录
        initData();        //初始化数据，得到从商品列表来的商品信息
        saveHistory();     //保存到历史记录
        initView();        //初始化控件
        initPager();       //初始化ImagePager
        setOnListener();   //设置事件监听
        initHistory();     //初始化历史记录
        //initListView();    //

        //initInCartNum();   //初始化购物车中商品的数量

        findViewById(R.id.progressBar1).setVisibility(View.GONE);
    }

    private void initMenu() {
        mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.END);
        mDrawer.setMenuView(R.layout.menudrawer_history);
        // 菜单宽度
        mDrawer.setMenuSize(260);
        // 菜单无阴影
        mDrawer.setDropShadowEnabled(false);
    }

    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.listView1);
        mAdapter = new HistoryAdapter();
        //Toast.makeText(this,String.valueOf(historyList.size()),Toast.LENGTH_LONG).show();
        listView.setAdapter(mAdapter);
    }

    /**
     * 历史记录适配器
     */
    class HistoryAdapter extends BaseAdapter {
        /**
         * 返回项的View
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = null;
            ViewHolder holder = null;
            if (convertView == null) {
                inflate = getLayoutInflater().inflate(R.layout.item_detail_menu_list, null);
                holder = new ViewHolder();
                holder.imgGoods = (ImageView) inflate.findViewById(R.id.img_icon);
                holder.tvGoodsName = (TextView) inflate.findViewById(R.id.tv_title);
                holder.tvGoodsPrice = (TextView) inflate.findViewById(R.id.tv_price);
                inflate.setTag(holder);
            } else {
                inflate = convertView;
                holder = (ViewHolder) inflate.getTag();
            }
            GoodsInfo info = historyList.get(position);
            holder.tvGoodsName.setText(info.getGoodsName());
            //holder.tvGoodsPrice.setText(NumberUtils.formatPrice(info.getGoodsPrice()));

            return inflate;
        }

        /**
         * 项的个数，循环几次
         */
        @Override
        public int getCount() {
            return historyList.size();
        }

        /**
         * 返回项
         */
        @Override
        public Object getItem(int position) {
            return null;
        }

        /**
         * 返回项id
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    class ViewHolder {
        ImageView imgGoods;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
    }

    /**
     * 初始化ImagePager
     */
    private void initPager() {
        mPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter adapter = new MyPagerAdapter(fm);
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mTvCount.setText(arg0 + 1 + "");
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * ImagePager适配器
     */
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 返回项
         */
        @Override
        public Fragment getItem(int position) {
            DetailBannerItemFragment fragment = new DetailBannerItemFragment();
            fragment.setResId(mBanner[position]);
            fragment.setPosition(position);

            return fragment;
        }

        /**
         * 项的个数，循环几次
         */
        @Override
        public int getCount() {
            return mBanner.length;
        }
    }

    /**
     * 初始化历史记录
     */
    private void initHistory() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        goodsList.add(new GoodsInfo("100001", "鱼香肉丝", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg", "服饰鞋包", 20.00, "好评96%", 1224, 1, 0));
        goodsList.add(new GoodsInfo("100002", "宫保鸡丁", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg", "服饰鞋包", 17, "好评95%", 645, 0, 0));
        goodsList.add(new GoodsInfo("100003", "麻婆豆腐", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg", "服饰鞋包", 15, "暂无评价", 1856, 0, 0));
        historyList.addAll(goodsList);
        //new HistoryTask().execute();
    }

    class HistoryTask extends AsyncTask<Void, Void, List<GoodsInfo>> {

        @Override
        protected List<GoodsInfo> doInBackground(Void... params) {
            ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
            goodsList.add(new GoodsInfo("100001", "鱼香肉丝", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg", "服饰鞋包", 20.00, "好评96%", 1224, 1, 0));
            goodsList.add(new GoodsInfo("100002", "宫保鸡丁", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg", "服饰鞋包", 17, "好评95%", 645, 0, 0));
            goodsList.add(new GoodsInfo("100003", "麻婆豆腐", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg", "服饰鞋包", 15, "暂无评价", 1856, 0, 0));

            return goodsList;
        }

        @Override
        protected void onPostExecute(List<GoodsInfo> result) {
            super.onPostExecute(result);
            historyList.clear();
            historyList.addAll(result);
            //mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化数据，得到从商品列表来的商品信息
     */
    private void initData() {
        Intent intent = getIntent();
        mGoodsInfo = (GoodsInfo) intent.getSerializableExtra(Constants.INTENT_KEY.INFO_TO_DETAIL);
        mInCart = new InCart(mGoodsInfo.getGoodsId(),
                mGoodsInfo.getGoodsName(), mGoodsInfo.getGoodsIcon(),
                mGoodsInfo.getGoodsType(), mGoodsInfo.getGoodsPrice(),
                mGoodsInfo.getGoodsPercent(), mGoodsInfo.getGoodsComment(),
                mGoodsInfo.getIsPhone(), mGoodsInfo.getIsFavor(), 1);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTvInCartNum = (TextView) findViewById(R.id.tv_incart);
        mTvCount = (TextView) findViewById(R.id.tv_count_page);
        mTvCollect = (TextView) findViewById(R.id.tv_collect);
        mImgFavor = (ImageView) findViewById(R.id.img_favor);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvPrice = (TextView) findViewById(R.id.tv_price);
        TextView tvVip = (TextView) findViewById(R.id.tv_vip);
        ImageView imgVip = (ImageView) findViewById(R.id.img_vip);
        tvTitle.setText(mGoodsInfo.getGoodsName());
        tvPrice.setText(NumberUtils.formatPrice(mGoodsInfo.getGoodsPrice()));
        // 判断是否收藏
      /*  if (DBUtils.hasFavor(mGoodsInfo)) {
            mTvCollect.setText("已关注");
            mImgFavor.setImageResource(R.drawable.pd_collect_p);
        }
        int isPhone = mGoodsInfo.getIsPhone();
        if (isPhone == 1) {
            imgVip.setVisibility(View.VISIBLE);
            tvVip.setVisibility(View.VISIBLE);
        } else {
            imgVip.setVisibility(View.GONE);
            tvVip.setVisibility(View.GONE);
        }*/
    }

    /**
     * 收藏/取消收藏
     */
    private void collect() {
        // 判断是否收藏
        mGoodsInfo = mGoodsInfo.clone();
        if (DBUtils.hasFavor(mGoodsInfo)) {
            // 由于DBUtil的方法，需要设置favor = 1，否则会无法取消关注
            mGoodsInfo.setIsFavor(1);
            //DBUtils.delete(mGoodsInfo);
            mTvCollect.setText("关注");
            mImgFavor.setImageResource(R.drawable.pd_collect_n);
            //ToastUtils.showToast(this, "取消成功");
            Toast.makeText(DetailActivity.this, "取消成功", Toast.LENGTH_LONG).show();
        } else {
            mGoodsInfo.setIsFavor(1);
            DBUtils.save(mGoodsInfo);
            mTvCollect.setText("已关注");
            mImgFavor.setImageResource(R.drawable.pd_collect_p);
            //ToastUtils.showToast(this, "关注成功");
            Toast.makeText(DetailActivity.this, "关注成功", Toast.LENGTH_LONG).show();
        }

        //通知收藏界面刷新
        Intent intent = new Intent();
        intent.setAction("updateFavor");
        sendBroadcast(intent);
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();             //返回
                break;
            case R.id.btn_goto_cart:  //购物车界面
                //gotoHomePage();
                break;
            case R.id.btn_collect:    //收藏
                collect();
                break;
            case R.id.btn_add_to_cart: //加入购物车
                add2Cart();
                break;
            case R.id.img_history:     //浏览记录
                mDrawer.toggleMenu();
                break;
            default:
                break;
        }
    }

    /**
     * 设置事件监听
     */
    private void setOnListener() {
        findViewById(R.id.btn_add_to_cart).setOnClickListener(this);//加到购物车
        findViewById(R.id.btn_goto_cart).setOnClickListener(this);  //跳转至购物车
        findViewById(R.id.btn_collect).setOnClickListener(this);    //收藏
        findViewById(R.id.img_history).setOnClickListener(this);    //历史记录
        findViewById(R.id.img_back).setOnClickListener(this);       //返回
    }

    /**
     * 保存到浏览历史
     */
    private void saveHistory() {
        /*GoodsInfo info = new Select().from(GoodsInfo.class)
                .where("goodsId=? AND isFavor=0", mGoodsInfo.getGoodsId())
                .executeSingle();
        // 删除同样的数据
        if (info != null) {
            info.delete();
        }
        // 插入到历史数据库
        mGoodsInfo.setIsFavor(0);
        DBUtils.save(mGoodsInfo);*/
    }

    /**
     * 初始化购物车中商品数量
     */
    private void initInCartNum() {
        int num = 1;//DBUtils.getInCartNum();
        Toast.makeText(DetailActivity.this, String.valueOf(num), Toast.LENGTH_LONG).show();
        if (num > 0) {
            mTvInCartNum.setVisibility(View.VISIBLE);
            mTvInCartNum.setText("" + num);
        } else {
            mTvInCartNum.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 加入购物车
     */
    private void add2Cart() {
       /* InCart inCart = new Select().from(InCart.class)
                .where("goodsId=?", mInCart.getGoodsId()).executeSingle();
        if (inCart != null) {
            // 若购物车中有，则数量+1
            //inCart.setNum(inCart.getNum() + 1);
            //inCart.save();
        } else {
            //mInCart.save();
        }*/
        // 刷新购物车商品数量
        //Toast.makeText(this, "已添加到购物车", Toast.LENGTH_SHORT).show();
        //ToastUtils.showToast(this, "已添加至购物车");
        // 通知主页刷新购物车商品数
       /* Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_FILTER.FILTER_CODE);
        intent.putExtra(Constants.BROADCAST_FILTER.EXTRA_CODE,
                Constants.INTENT_KEY.REFRESH_INCART);
        sendBroadcast(intent);*/
    }
}
