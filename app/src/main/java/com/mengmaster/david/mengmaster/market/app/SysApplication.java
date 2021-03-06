package com.mengmaster.david.mengmaster.market.app;

import android.app.Activity;
import android.app.Application;


import java.util.ArrayList;

/**
 * Created by dell on 2016/11/26.
 */
public class SysApplication extends Application {
    private ArrayList<Activity> mList = new ArrayList<Activity>();
    private ArrayList<Activity> detailList = new ArrayList<Activity>();
    private static SysApplication instance;

    private SysApplication() {
    }
    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
        //AseoZdpAseo.initType(activity, AseoZdpAseo.INSERT_TYPE);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void addDetailActivity(Activity activity) {
        detailList.add(activity);
    }

    /**
     * 关闭多余的Detail页面，使其总数不超过2个
     */
    public void removeExtraActivity() {
        if (detailList.size() > 2) {
            detailList.get(0).finish();
            detailList.remove(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
