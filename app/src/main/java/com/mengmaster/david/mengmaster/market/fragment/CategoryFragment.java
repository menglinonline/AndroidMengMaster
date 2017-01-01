package com.mengmaster.david.mengmaster.market.fragment;

import com.android.volley.VolleyError;
import android.content.Intent;
import android.os.Bundle;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.json.JSONUtils;
import com.lib.uil.UILUtils;
import com.lib.volley.HTTPUtils;
import com.lib.volley.VolleyListener;
import com.mengmaster.david.mengmaster.market.activity.GoodsListActivity;
import com.mengmaster.david.mengmaster.market.activity.MainActivity;
import com.mengmaster.david.mengmaster.market.activity.R;
import com.mengmaster.david.mengmaster.market.activity.SearchActivity;
import com.mengmaster.david.mengmaster.market.entity.CategoryMenu;
import com.mengmaster.david.mengmaster.market.utils.Constants;
import com.mengmaster.david.mengmaster.market.utils.ListViewForScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * Created by dell on 2016/11/25.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private ScrollView mScrollView;
    private ArrayList<CategoryMenu> menuList = new ArrayList<CategoryMenu>(); //一级分类集合
    private List<CategoryMenu.CategoryItem> categoryitem;          //二级分类集合
    private CategoryListAdapter mListAdapter;                      //一级分类适配器
    private SubCategoryGridAdapter mGridAdapter;                   //二级分类适配器
    private int selectedPosition;                                  //一级分类选中的位置
    DisplayImageOptions options;		                           //DisplayImageOptions是用于设置图片显示的类
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        //将界面布局文件加载到Activity中来操作
        layout = inflater.inflate(R.layout.fragment_category, container, false);
		
        //设置事件
        //layout.findViewById(R.id.img_category_search_code).setOnClickListener(this);
        //layout.findViewById(R.id.layout_category_search).setOnClickListener(this);

        mScrollView = (ScrollView) layout.findViewById(R.id.scrollView_category);
        //初始化ImageLoader
        ImageLoader.getInstance().init((ImageLoaderConfiguration.createDefault(getActivity())));

        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.load_logo)
                .showImageOnFail(R.drawable.load_logo)
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))
                .build();									// 创建配置过得DisplayImageOption对象

        //初始化分类数据
        initCategoryData();

        //初始化一级分类
        initCategoryListView();

        //初始化二级分类
        initSubCategoryGridView();

        return layout;
    }

    /**
     * 初始化一级分类数据
     */
    private void initCategoryData() {
        /**
         * 异步下载JSON
         * HTTPUtils引的是lib_yuchen0309_for_jd.jar,VolleyError引的是lib_volley.jar,TypeToken
         * TypeToken引的是gson-2.2.1.jar
         */
        HTTPUtils.getVolley(getActivity(), Constants.URL.MENUJSON,
                new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                    }

                    @Override
                    public void onResponse(String arg0) {
                        Type type = new TypeToken<ArrayList<CategoryMenu>>() {
                        }.getType();
                        ArrayList<CategoryMenu> list = JSONUtils.parseJSONArray(arg0, type);

                        menuList.addAll(list);
                        //Toast.makeText(getActivity(),list.get(1).getCategory(),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), String.valueOf(menuList.size()),Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(),  menuList.get(0).getCategoryitem().get(0).getImgurl(),Toast.LENGTH_SHORT).show();

                        mListAdapter.notifyDataSetChanged();
                        mGridAdapter.notifyDataSetChanged();
                        layout.findViewById(R.id.progressBar1).setVisibility(View.GONE);
                    }
                });
    }

    /**
     * 初始化一级分类
     */
    private void initCategoryListView() {
        //得到ListView控件
        final ListViewForScrollView listView = (ListViewForScrollView) layout.findViewById(R.id.listView_category);
        //给ListView控件设置适配器
        mListAdapter = new CategoryListAdapter();
        listView.setAdapter(mListAdapter);

        //给ListView item单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (selectedPosition == position) {
                    return;
                }
                mScrollView.smoothScrollTo(0, position * view.getHeight());
                selectedPosition = position;
                mListAdapter.notifyDataSetChanged();
                mGridAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 一级分类适配器
     */
    class CategoryListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //得到一级分类的xml
            View category_layout = getActivity().getLayoutInflater().inflate(R.layout.item_category_list, null);
            //得到xml的TextView控件
            TextView tvCategory = (TextView) category_layout.findViewById(R.id.tv_category_list);
            category_layout.setTag(tvCategory);
            //设置返回项的一级分类的文本
            String categoryTypeName = menuList.get(position).getCategory();
            tvCategory.setText(categoryTypeName);

            //如果选中的一级分类
            if (selectedPosition == position) {
                category_layout.setBackgroundResource(R.drawable.drawable_common_category_list_s);
                tvCategory.setTextColor(getResources().getColor(R.color.red));
            } else {
                category_layout.setBackgroundResource(R.drawable.drawable_common_category_list);
                tvCategory.setTextColor(getResources().getColor(R.color.text));
            }

            return category_layout;
        }

        @Override
        public int getCount() {
            return menuList.size();
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
     * 初始化二级分类
     */
    private void initSubCategoryGridView() {
        //得到GridView控件
        GridView gridView = (GridView) layout.findViewById(R.id.gridView1);
        //给GridView控件控件设置适配器
        mGridAdapter = new SubCategoryGridAdapter();
        gridView.setAdapter(mGridAdapter);

        //给GridView item单击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra(Constants.INTENT_KEY.MENU_TO_GOODS_LIST, categoryitem.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * 二级分类适配器
     */
    class SubCategoryGridAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layoutSubcategory = null;
            GridViewHolder holder = null;
            if (convertView == null) {
                holder = new GridViewHolder();
                //得到二级分类的xml
                layoutSubcategory = getActivity().getLayoutInflater().inflate(R.layout.item_category_grid, null);
                //得到xml的ImageView和TextView控件
                holder.imgCategoryGrid = (ImageView) layoutSubcategory.findViewById(R.id.img_category_grid);
                holder.tvCategoryGrid = (TextView) layoutSubcategory.findViewById(R.id.tv_category_grid);

                layoutSubcategory.setTag(holder);
            } else {
                layoutSubcategory = convertView;
                holder = (GridViewHolder) layoutSubcategory.getTag();
            }
            //给TextView设置文本
            holder.tvCategoryGrid.setText(menuList.get(selectedPosition).getCategoryitem().get(position).getTypename());
            //给IamgeView设置文本图片
            //UILUtils.displayImage(getActivity(), "http://7xi38r.com1.z0.glb.clouddn.com/@/server_anime/menujson/shuiyijiajufu.png", holder.imgCategoryGrid);
            ImageLoader.getInstance().displayImage(menuList.get(selectedPosition).getCategoryitem().get(position).getImgurl(), holder.imgCategoryGrid, options);

            return layoutSubcategory;
        }

        @Override
        public int getCount() {
            if (menuList.size() > 0) {
                categoryitem = menuList.get(selectedPosition).getCategoryitem();
                return categoryitem.size();
            }
            return 0;
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

    class GridViewHolder {
        ImageView imgCategoryGrid;
        TextView tvCategoryGrid;
    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.img_category_search_code: // 二维码扫描

                break;
            case R.id.layout_category_search://搜索
                gotoSearch();
                break;*/
            default:
                break;
        }
    }

    /**
     * 搜索
     */
    private void gotoSearch() {
        //跳转到搜索界面
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
        // activity开启无动画
        getActivity().overridePendingTransition(0, 0);
    }
}
