package com.mengmaster.david.mengmaster.market.utils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.mengmaster.david.mengmaster.market.entity.GoodsInfo;
import com.mengmaster.david.mengmaster.market.entity.InCart;

import java.util.List;

/**
 * Created by dell on 2016/12/2.
 */
public class DBUtils {
    public static void delete(GoodsInfo info) {
        new Delete()
                .from(GoodsInfo.class)
                .where("goodsId=? And isFavor=?", info.getGoodsId(),
                        info.getIsFavor()).executeSingle();
    }

    /**
     * 清空历史
     */
    public static void deleteHistory() {
        new Delete().from(GoodsInfo.class).where("isFavor=0").execute();
    }

    /**
     * 清空购物车
     */
    public static void deleteCart(InCart cart) {
        new Delete().from(InCart.class).where("goodsId=?", cart.getGoodsId())
                .executeSingle();
    }

    /**
     * 判断是否已收藏
     *
     * @param mInfo
     * @return
     */
    public static boolean hasFavor(GoodsInfo info) {
        GoodsInfo info2 = new Select().from(GoodsInfo.class)
                .where("goodsId=? And isFavor=?", info.getGoodsId(), 1)
                .executeSingle();

        // if (info2 == null){
        // return false;
        // }else{
        // return true;
        // }
        return info2 != null;
    }

    /**
     * 判断是否已加入购物车
     *
     * @param cart
     * @return
     */
    public static boolean hasInCart(InCart cart) {
        InCart info2 = new Select().from(InCart.class)
                .where("goodsId=?", cart.getGoodsId()).executeSingle();

        // if (info2 == null){
        // return false;
        // }else{
        // return true;
        // }
        return info2 != null;
    }

    public static void save(GoodsInfo info) {
        info.save();
    }

    public static void saveInCart(InCart cart) {
        cart.save();
    }

    public static List<GoodsInfo> getFavor() {
        return new Select().from(GoodsInfo.class).where("isFavor=?", 1)
                .execute();
    }

    public static List<GoodsInfo> getHistory() {
        return new Select().from(GoodsInfo.class).where("isFavor=?", 0)
                .execute();
    }

    public static List<InCart> getInCart() {
        return new Select().from(InCart.class).execute();
    }

    public static int getInCartNum() {
        int num = 0;
        List<InCart> execute = new Select().from(InCart.class).execute();
        for (int i = 0; i < execute.size(); i++) {
            num = num + execute.get(i).getNum();
        }
        return num;
    }

    private DBUtils() {
    }
}
