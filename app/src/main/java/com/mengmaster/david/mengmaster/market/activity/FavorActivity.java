package com.mengmaster.david.mengmaster.market.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lib.uil.ScreenUtils;
import com.lib.uil.UILUtils;
import com.mengmaster.david.mengmaster.market.entity.GoodsInfo;
import com.mengmaster.david.mengmaster.market.utils.Constants;
import com.mengmaster.david.mengmaster.market.utils.MyListView;
import com.mengmaster.david.mengmaster.market.utils.NumberUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/12/25.
 */
public class FavorActivity extends Activity implements View.OnClickListener {
    private final String strGoods = "您还没有关注过任何商品！何不去逛逛~";
    private final String strStore = "您还没有关注过任何店铺！何不去逛逛~";
    private ArrayList<GoodsInfo> mData = new ArrayList<GoodsInfo>();
    private FavorAdapter mAdapter;
    private SwipeMenuListView mListView;
    private ProgressBar mBar;                             //进度条
    private View layoutNull;
    private TextView mTvGoods;                            //商品文字
    private TextView mTvStore;                            //店铺文字
    private View indicator;                               //商品文字下面的红色条
    private View indicator2;                              //店铺文字下面的红色条
    private TextView mBtnMore;                            //去首页逛逛
    private TextView mTvDisc;                             //去首页逛逛文字
    private View mLayoutGoods;                            //“商品”布局
    private View mLayoutStore;                            //”店铺“布局
    private ImageView mImgBack;                           //返回
    DisplayImageOptions options;		                  //DisplayImageOptions是用于设置图片显示的类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        //初始化数据
        initData();
        //初始化控件
        initView();
        //初始化ListView内容和布局
        initListView();
        //设置事件监听
        setOnListener();

        //初始化ImageLoader
        ImageLoader.getInstance().init((ImageLoaderConfiguration.createDefault((FavorActivity.this))));
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.load_logo)
                .showImageOnFail(R.drawable.load_logo)
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))
                .build();									// 创建配置过得DisplayImageOption对象
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new FavorTask().execute();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mBtnMore = (TextView) findViewById(R.id.btn_more);           //去首页逛逛
        mLayoutGoods =  findViewById(R.id.layout_goods);             //“商品”布局
        mLayoutStore =  findViewById(R.id.layout_store);             //”店铺“布局
        mBar = (ProgressBar) findViewById(R.id.progressBar1);        //进度条
        mImgBack = (ImageView) findViewById(R.id.img_back);          //返回
        mTvGoods = (TextView) findViewById(R.id.tv_goods);           //商品文字
        mTvStore = (TextView) findViewById(R.id.tv_store);           //店铺文字
        mTvDisc = (TextView) findViewById(R.id.tv_disc);             //去首页逛逛文字
        indicator = findViewById(R.id.indicator1);                   //商品文字下面的红色条
        indicator2 = findViewById(R.id.indicator2);                  //店铺文字下面的红色条
        mListView = (SwipeMenuListView) findViewById(R.id.listView1);//ListView控件
        layoutNull = findViewById(R.id.layout_null);                 //您还没有关注过任何商品！何不去逛逛 RelativeLayout
    }

    /**
     * 初始化ListView内容和布局
     */
    private void initListView() {
        mAdapter = new FavorAdapter();
        mListView.setAdapter(mAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(FavorActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(ScreenUtils.getScreenWidth(FavorActivity.this) / 3);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                //deleteItem(position);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavorActivity.this, DetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY.INFO_TO_DETAIL, mData.get(position));
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * ListView适配器
     */
    class FavorAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = null;
            ViewHolder holder = null;
            if (convertView == null) {
                inflate = getLayoutInflater().inflate(R.layout.item_favor_list, null);
                holder = new ViewHolder();
                holder.imgGoods = (ImageView) inflate.findViewById(R.id.img_icon);
                holder.tvGoodsName = (TextView) inflate.findViewById(R.id.tv_title);
                holder.tvGoodsPrice = (TextView) inflate.findViewById(R.id.tv_price);
                inflate.setTag(holder);
            } else {
                inflate = convertView;
                holder = (ViewHolder) inflate.getTag();
            }
            GoodsInfo info = mData.get(position);
            holder.tvGoodsName.setText(info.getGoodsName());
            holder.tvGoodsPrice.setText(NumberUtils.formatPrice(info.getGoodsPrice()));
            //UILUtils.displayImage(FavorActivity.this, info.getGoodsIcon(),holder.imgGoods);
            ImageLoader.getInstance().displayImage(info.getGoodsIcon(), holder.imgGoods, options);
            return inflate;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

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
     * 设置事件监听
     */
    private void setOnListener() {
        mImgBack.setOnClickListener(this);          //“返回”事件
        mBtnMore.setOnClickListener(this);          //“去首页逛逛”事件
        mLayoutGoods.setOnClickListener(this);      //”商品“布局事件
        mLayoutStore.setOnClickListener(this);      //“店铺”布局事件
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:     //返回
                finish();
                break;
            case R.id.btn_more:     //去首页
                //gotoHomePage();
                break;
            case R.id.layout_goods: //商品
                goodsClick();
                break;
            case R.id.layout_store: //店铺
                stroeClick();
                break;
            default:
                break;
        }
    }

    /**
     * 商品点击
     */
    private void goodsClick() {
        if (mData.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            layoutNull.setVisibility(View.GONE);
        } else {
            layoutNull.setVisibility(View.VISIBLE);
        }
        mBtnMore.setText("去首页逛逛");
        mTvDisc.setText(strGoods);
        mTvGoods.setTextColor(Color.RED);
        mTvStore.setTextColor(getResources().getColor(R.color.dark));
        indicator.setVisibility(View.VISIBLE);
        indicator2.setVisibility(View.INVISIBLE);
    }

    /**
     * 店铺点击
     */
    private void stroeClick() {
        layoutNull.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mBtnMore.setText("去好店铺逛逛");
        mTvDisc.setText(strStore);
        mTvStore.setTextColor(Color.RED);
        mTvGoods.setTextColor(getResources().getColor(R.color.dark));
        indicator.setVisibility(View.INVISIBLE);
        indicator2.setVisibility(View.VISIBLE);
    }

    /**
     * 异步任务获取”我的关注“数据
     */
    class FavorTask extends AsyncTask<Void, Void, List<GoodsInfo>> {

        @Override
        protected List<GoodsInfo> doInBackground(Void... params) {
            //return DBUtils.getFavor();
            ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
            goodsList.add(new GoodsInfo("100001", "鱼香肉丝", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg", "服饰鞋包", 20.00, "好评96%", 1224, 1, 0));
            goodsList.add(new GoodsInfo("100002", "宫保鸡丁", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg", "服饰鞋包", 17, "好评95%", 645, 0, 0));
            goodsList.add(new GoodsInfo("100003", "麻婆豆腐", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg", "服饰鞋包", 15, "暂无评价", 1856, 0, 0));

            return goodsList;
        }

        @Override
        protected void onPostExecute(List<GoodsInfo> result) {
            super.onPostExecute(result);
            mData.clear();
            mData.addAll(result);
            Toast.makeText(FavorActivity.this,String.valueOf(5),Toast.LENGTH_LONG).show();
            mAdapter.notifyDataSetChanged();
            if (mData.size() == 0) {
                layoutNull.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            } else {
                layoutNull.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            }
            //进度条显示
            mBar.setVisibility(View.GONE);
        }
    }
}
