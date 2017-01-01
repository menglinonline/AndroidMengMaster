package com.mengmaster.david.mengmaster.market.app;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/**
 * 用于初始化ImageLoader。若不执行初始化，下载图片前清除缓存，存在潜在空指针问题
 * @author Administrator
 *
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_LONG).show();
        initImageLoader(this);
        ActiveAndroid.initialize(this);
        //初始化百度地图
        //SDKInitializer.initialize(this);
        initJPush();
    }

    private void initJPush() {
//		JPushInterface.setDebugMode(true);	// 设置开启日志,发布时请关闭日志
        //JPushInterface.init(this);     		// 初始化 JPush
    }

    /**
     * 初始化ImageLoader
     * @param context
     */
   public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        Toast.makeText(getApplicationContext(),"456",Toast.LENGTH_LONG).show();
    }
}
