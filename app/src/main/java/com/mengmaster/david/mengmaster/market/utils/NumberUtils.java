package com.mengmaster.david.mengmaster.market.utils;

import java.text.DecimalFormat;

/**
 * Created by dell on 2016/11/28.
 */
public class NumberUtils {
    /**
     * 格式化价格，强制保留2位小数
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        DecimalFormat df=new DecimalFormat("0.00");
        String format = "￥" + df.format(price);
        return format;
    }
}
