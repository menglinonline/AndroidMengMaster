package com.mengmaster.david.mengmaster.market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.lib.json.JSONUtils;
import com.lib.uil.UILUtils;
import com.lib.volley.HTTPUtils;
import com.lib.volley.VolleyListener;
import com.mengmaster.david.mengmaster.market.entity.CategoryMenu;
import com.mengmaster.david.mengmaster.market.entity.GoodsInfo;
import com.mengmaster.david.mengmaster.market.utils.Constants;
import com.mengmaster.david.mengmaster.market.utils.MyGridView;
import com.mengmaster.david.mengmaster.market.utils.MyListView;
import com.mengmaster.david.mengmaster.market.utils.NumberUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by dell on 2016/11/25.
 */
public class GoodsListActivity extends FragmentActivity implements View.OnClickListener{

    private ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();        //数据源
    private ArrayList<GoodsInfo> goodsListCopy = new ArrayList<GoodsInfo>();	//备份，用于排序后恢复

    private MenuDrawer mDrawer;

    private boolean isGlobalMenuShow;
    private View mLayoutGlobalMenu;            //activity_goods_list.xml里的RelativeLayout综合视图
    private View mOverlayHeader;               //head_goods_list.xml Header的视图

    private int mLastFirstPosition;            //

    private int durationRotate = 700;          //旋转动画时间
    private int durationAlpha = 500;           //透明度动画时间
    // private int gridCode = -1;
    // private int listCode = -1;

    private MyListView mListView;              //列表
    private GoodsListAdapter mListAdapter;     //列表适配器
    private MyGridView mGridView;              //网格
    private GoodsGridAdapter mGridAdapter;     //网格适配器

    private ImageView mImgOverlay;             //回到顶部的图片
    private ImageView mImgMenu;                //列表和网格图片
    private ImageView mImgMenuList;            //列表和网格图片
    private ImageView mImgMenuGrid;            //列表和网格图片
    private ImageView mImgGlobal;              //Header的综合向下箭头图片
    private ImageView mImgGlobalGrid;          //Header的综合向下箭头图片
    private ImageView mImgGlobalList;          //Header的综合向下箭头图片


    private boolean isGrid;                    //是否为Grid列表
    private boolean isSortUp;	               //是否为价格升序排列

    private int menuSize;

    //private FilterMenuFragment filterMenuFragment;
    private TextView mTvSaleVolume;
    private TextView mTvSaleVolumeList;
    private TextView mTvSaleVolumeGrid;
    private TextView mTvPrice;
    private TextView mTvPriceList;
    private TextView mTvPriceGrid;
    private ImageView mImgPriceGrid;
    private ImageView mImgPriceList;
    private ImageView mImgPrice;
    private ProgressBar mProgressBar;            //进度条

    private ListView mmListView;
    DisplayImageOptions options;		         //DisplayImageOptions是用于设置图片显示的类

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        //初始化数据
        initGoods();
        //初始化控件
        initView();
        //初始化ListView布局和内容
        initListView();
        //初始化GridView布局和内容
        initGridView();
        //设置事件监听
        setOnListener();
        //进度条显示
        mProgressBar.setVisibility(View.GONE);
        //初始化ImageLoader
        ImageLoader.getInstance().init((ImageLoaderConfiguration.createDefault((GoodsListActivity.this))));
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
    private void initGoods() {
        goodsList.add(new GoodsInfo("100001", "鱼香肉丝", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods01.jpg", "服饰鞋包", 20.00, "好评96%", 1224, 1, 0));
        goodsList.add(new GoodsInfo("100002", "宫保鸡丁", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods02.jpg", "服饰鞋包", 17, "好评95%", 645, 0, 0));
        goodsList.add(new GoodsInfo("100003", "麻婆豆腐", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods03.jpg", "服饰鞋包", 15, "暂无评价", 1856, 0, 0));
        goodsList.add(new GoodsInfo("100004", "青椒土豆丝", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods04.jpg", "电脑数码", 10, "好评97%", 865, 0, 0));
        goodsList.add(new GoodsInfo("100005", "鱼香茄子", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods05.jpg", "电脑数码", 3299.00, "好评95%", 236, 0, 0));
        goodsList.add(new GoodsInfo("100006", "地三鲜", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods06.jpg", "服饰鞋包", 499.00, "好评95%", 115, 0, 0));
        goodsList.add(new GoodsInfo("100007", "木耳青索", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods07.jpg", "服饰鞋包", 199.00, "好评95%", 745, 0, 0));
        goodsList.add(new GoodsInfo("100008", "杏鲍菇", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods08.jpg", "电脑数码", 569.00, "好评95%", 854, 1, 0));
        goodsList.add(new GoodsInfo("100009", "白菜炒豆腐", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods09.jpg", "电脑数码", 5099.00, "好评94%", 991, 0, 0));
        goodsList.add(new GoodsInfo("100010", "糖醋里脊", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods10.jpg", "运动户外", 2999.00, "好评93%", 1145, 0, 0));
        goodsList.add(new GoodsInfo("100011", "木耳肉片", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods11.jpg", "运动户外", 1088.00, "好评92%", 909, 0, 0));
        goodsList.add(new GoodsInfo("100012", "椒盐蘑菇", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods12.jpg", "图书音像", 25.40, "好评95%", 1443, 0, 0));
        goodsList.add(new GoodsInfo("100013", "油麦菜", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods13.jpg", "图书音像", 19.70, "好评98%", 3702, 0, 0));
        goodsList.add(new GoodsInfo("100014", "糖醋丸子", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods14.jpg", "图书音像", 38.40, "好评97%", 442, 1, 0));
        goodsList.add(new GoodsInfo("100015", "蒜台炒鸡蛋", "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/goodsicons/goods15.jpg", "图书音像", 57.80, "好评93%", 765, 0, 0));
        goodsListCopy.addAll(goodsList);

        /**
         * 异步下载JSON
         * HTTPUtils引的是lib_yuchen0309_for_jd.jar,VolleyError引的是lib_volley.jar,TypeToken
         * TypeToken引的是gson-2.2.1.jar
         */
        /*HTTPUtils.getVolley(GoodsListActivity.this, Constants.URL.MENUJSON,
                new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                    }

                    @Override
                    public void onResponse(String arg0) {
                        Type type = new TypeToken<ArrayList<GoodsInfo>>() {
                        }.getType();
                        ArrayList<GoodsInfo> list = JSONUtils.parseJSONArray(arg0, type);
                        goodsList.addAll(list);
                        goodsListCopy.addAll(goodsList);
                    }
                });*/
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mImgPrice = (ImageView) findViewById(R.id.img_price);           //Header的价格右侧的上下箭头图片
        mTvPrice = (TextView) findViewById(R.id.tv_price);              //Header的价格文字
        mTvSaleVolume = (TextView) findViewById(R.id.tv_salse_volume);  //Header的销量文字
        mLayoutGlobalMenu = findViewById(R.id.layout_global_menu);      //activity_goods_list.xml里的RelativeLayout综合视图
        mOverlayHeader = findViewById(R.id.overlayHeader);              //head_goods_list.xml Header的视图
        mImgOverlay = (ImageView) findViewById(R.id.img_overlay);       //回到顶部图片
        mImgGlobal = (ImageView) findViewById(R.id.img_global);         //Header的综合向下箭头图片
        mImgMenu = (ImageView) findViewById(R.id.img_category_menu);    //列表和网格切换图片
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);   //进度条
    }

    /**
     * 初始化ListView布局和内容
     */
    private void initListView() {
        mListView = (MyListView) findViewById(R.id.listView1);
        View head = getLayoutInflater().inflate(R.layout.head_goods_list, null);
        mListView.addHeaderView(head, null, false);
        mListAdapter = new GoodsListAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                GoodsInfo info = goodsList.get(position - 1);
                gotoDetail(info);
            }
        });
        //滚动到最上方时隐藏mOverlayHeader
        mListView.setOnScroll2TopListener(new MyListView.OnScroll2TopListener() {
            @Override
            public void scroll2Top() {
                mOverlayHeader.setVisibility(View.INVISIBLE);
            }
        });
        //向上滚动后右下角出现回到顶部按钮
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //控制Header视图的显示/隐藏
                if (firstVisibleItem < mLastFirstPosition) {
                    mOverlayHeader.setVisibility(View.VISIBLE);
                } else if (firstVisibleItem > mLastFirstPosition) {
                    mOverlayHeader.setVisibility(View.INVISIBLE);
                }
                mLastFirstPosition = firstVisibleItem;
                //控制回到顶部图片的显示/隐藏
                if (firstVisibleItem > 0) {
                    mImgOverlay.setVisibility(View.VISIBLE);
                } else {
                    mImgOverlay.setVisibility(View.INVISIBLE);
                }
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
        mImgPriceList = (ImageView) head.findViewById(R.id.img_price);
        mTvPriceList = (TextView) head.findViewById(R.id.tv_price);
        mTvSaleVolumeList = (TextView) head.findViewById(R.id.tv_salse_volume);
        mImgGlobalList = (ImageView) head.findViewById(R.id.img_global);
        mImgMenuList = (ImageView) head.findViewById(R.id.img_category_menu);
        mImgMenuList.setOnClickListener(this);
        head.findViewById(R.id.btn_global).setOnClickListener(this);
        head.findViewById(R.id.btn_filter).setOnClickListener(this);
        head.findViewById(R.id.btn_price).setOnClickListener(this);
        head.findViewById(R.id.btn_salse_volume).setOnClickListener(this);
        head.findViewById(R.id.layout_category_search_bar).setOnClickListener(this);
        head.findViewById(R.id.img_back).setOnClickListener(this);
    }

    /**
     * 初始化GridView布局和内容
     */
    private void initGridView() {
        mGridView = (MyGridView) findViewById(R.id.gridView1);
        View head = getLayoutInflater().inflate(R.layout.head_goods_list, null);
        mGridView.addHeaderView(head, null, false);
        mGridAdapter = new GoodsGridAdapter();
        mGridView.setAdapter(mGridAdapter);
        mTvPriceGrid = (TextView) head.findViewById(R.id.tv_price);
        mTvSaleVolumeGrid = (TextView) head.findViewById(R.id.tv_salse_volume);
        mImgPriceGrid = (ImageView) head.findViewById(R.id.img_price);
        mImgGlobalGrid = (ImageView) head.findViewById(R.id.img_global);
        mImgMenuGrid = (ImageView) head.findViewById(R.id.img_category_menu);
        mImgMenuGrid.setOnClickListener(this);
        head.findViewById(R.id.btn_global).setOnClickListener(this);
        head.findViewById(R.id.btn_filter).setOnClickListener(this);
        head.findViewById(R.id.btn_price).setOnClickListener(this);
        head.findViewById(R.id.btn_salse_volume).setOnClickListener(this);
        head.findViewById(R.id.layout_category_search_bar).setOnClickListener(this);
        head.findViewById(R.id.img_back).setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                GoodsInfo info = goodsList.get(position - 2);
                gotoDetail(info);
            }
        });
        // 滚动到最上方时隐藏mOverlayHeader
        mGridView.setOnGridScroll2TopListener(new MyGridView.OnGridScroll2TopListener() {

            public void scroll2Top() {
                mOverlayHeader.setVisibility(View.INVISIBLE);
            }
        });
        // 向上滚动后右下角出现回到顶部按钮
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //控制Header视图的显示/隐藏
                if (firstVisibleItem < mLastFirstPosition) {
                    mOverlayHeader.setVisibility(View.VISIBLE);
                } else if (firstVisibleItem > mLastFirstPosition) {
                    mOverlayHeader.setVisibility(View.INVISIBLE);
                }
                mLastFirstPosition = firstVisibleItem;
                //控制回到顶部图片的显示/隐藏
                if (firstVisibleItem > 0) {
                    mImgOverlay.setVisibility(View.VISIBLE);
                } else {
                    mImgOverlay.setVisibility(View.INVISIBLE);
                }
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

        });
    }

    /**
     * 设置事件
     */
    private void setOnListener() {
        findViewById(R.id.img_back).setOnClickListener(this);                   //返回
        findViewById(R.id.btn_global).setOnClickListener(this);
        findViewById(R.id.btn_filter).setOnClickListener(this);
        findViewById(R.id.btn_price).setOnClickListener(this);
        findViewById(R.id.btn_salse_volume).setOnClickListener(this);
        findViewById(R.id.layout_category_search_bar).setOnClickListener(this);
        mOverlayHeader.setOnClickListener(this);                                 //隐藏Header
        mLayoutGlobalMenu.setOnClickListener(this);                              //
        mImgOverlay.setOnClickListener(this);                                    //回到顶部
        mImgMenu.setOnClickListener(this);                                       //列表和网格切换
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_category_menu:  //视图切换菜单
                changeGrid();
                break;
            case R.id.btn_global:         //综合

                break;
            case R.id.layout_global_menu: //综合下拉菜单

                break;
            case R.id.img_overlay:         //回到顶部
                mListView.setSelection(0);
                mGridView.setSelection(0);
                break;
            case R.id.img_back:            //返回
                finish();
                break;
            case R.id.overlayHeader:       //
                break;
            default:
                break;
        }
    }

    /**
     * ListView适配器
     */
    class GoodsListAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = null;
            ViewHolder holder = null;
            if (convertView == null) {
                inflate = getLayoutInflater().inflate(R.layout.item_goods_list_list, null);
                holder = new ViewHolder();
                holder.imgIcon = (ImageView) inflate.findViewById(R.id.img_icon);
                holder.imgVip = (ImageView) inflate.findViewById(R.id.img_icon_vip);
                holder.tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
                holder.tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
                holder.tvPercent = (TextView) inflate.findViewById(R.id.tv_percent);
                holder.tvNum = (TextView) inflate.findViewById(R.id.tv_num);
                inflate.setTag(holder);
            } else {
                inflate = convertView;
                holder = (ViewHolder) inflate.getTag();
            }
            GoodsInfo goodsInfo = goodsList.get(position);
            holder.tvTitle.setText(goodsInfo.getGoodsName());
            holder.tvPrice.setText(NumberUtils.formatPrice(goodsInfo.getGoodsPrice()));
            holder.tvPercent.setText(goodsInfo.getGoodsPercent());
            holder.tvNum.setText(goodsInfo.getGoodsComment() + "人");
            //UILUtils.displayImage(GoodsListActivity.this, goodsInfo.getGoodsIcon(), holder.imgIcon);
            ImageLoader.getInstance().displayImage(goodsInfo.getGoodsIcon(), holder.imgIcon, options);
            if (goodsInfo.getIsPhone() == 1) {
                holder.imgVip.setVisibility(View.VISIBLE);
            } else {
                holder.imgVip.setVisibility(View.INVISIBLE);
            }
            return inflate;
        }

        @Override
        public int getCount() {
            return goodsList.size();
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

    /**
     * GridView适配器
     */
    class GoodsGridAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = null;
            ViewHolder holder = null;
            if (convertView == null) {
                inflate = getLayoutInflater().inflate(R.layout.item_goods_list_grid, null);
                holder = new ViewHolder();
                holder.imgIcon = (ImageView) inflate.findViewById(R.id.img_icon);
                holder.tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
                holder.tvPrice = (TextView) inflate.findViewById(R.id.tv_price);
                inflate.setTag(holder);
            } else {
                inflate = convertView;
                holder = (ViewHolder) inflate.getTag();
            }
            GoodsInfo goodsInfo = goodsList.get(position);
            holder.tvTitle.setText(goodsInfo.getGoodsName());
            holder.tvPrice.setText(NumberUtils.formatPrice(goodsInfo.getGoodsPrice()));
            //UILUtils.displayImage(GoodsListActivity.this, goodsInfo.getGoodsIcon(), holder.imgIcon);
            ImageLoader.getInstance().displayImage(goodsInfo.getGoodsIcon(), holder.imgIcon, options);

            return inflate;
        }

        @Override
        public int getCount() {
            return goodsList.size();
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

    /**
     * 切换视图
     */
    private void changeGrid() {
        isGrid = !isGrid;
        if (isGrid) {
            mGridView.setSelection(mListView.getFirstVisiblePosition());
            mListView.setVisibility(View.INVISIBLE);
            mGridView.setVisibility(View.VISIBLE);
            mImgMenu.setImageResource(R.drawable.product_list_top_list_normal);
            mImgMenuList.setImageResource(R.drawable.product_list_top_list_normal);
            mImgMenuGrid.setImageResource(R.drawable.product_list_top_list_normal);
        } else {
            mListView.setSelection(mGridView.getFirstVisiblePosition());
            mGridView.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            mImgMenu.setImageResource(R.drawable.product_list_top_grid_normal);
            mImgMenuList.setImageResource(R.drawable.product_list_top_grid_normal);
            mImgMenuGrid.setImageResource(R.drawable.product_list_top_grid_normal);
        }
    }

    /**
     * 初始化筛选菜单
     */
    private void initMenu() {
/*        Intent intent = getIntent();
        CategoryMenu.CategoryItem categoryItem = (CategoryMenu.CategoryItem) intent.getSerializableExtra(Constants.INTENT_KEY.MENU_TO_GOODS_LIST);
        // gridCode = getIntent().getIntExtra("gridCode", -1);
        // listCode = getIntent().getIntExtra("listCode", -1);
        mDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.END);
        mDrawer.setMenuView(R.layout.menudrawer);
        mDrawer.setContentView(R.layout.activity_goods_list);
        // 菜单无阴影
        mDrawer.setDropShadowEnabled(false);
        filterMenuFragment = new FilterMenuFragment();
        // filterMenuFragment.setGoodsCode(listCode, gridCode);
        filterMenuFragment.setCategoryItem(categoryItem);
        getSupportFragmentManager().beginTransaction().add(R.id.menu_container, filterMenuFragment).commit();*/
    }

    /**
     * 商品详情
     * @param info
     */
    private void gotoDetail(GoodsInfo info) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY.INFO_TO_DETAIL, info);
        startActivity(intent);
    }

    class ViewHolder {
        ImageView imgIcon;
        ImageView imgVip;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvPercent;
        TextView tvNum;
    }
}
