package com.mengmaster.david.mengmaster.market.entity;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by dell on 2016/11/28.
 */
//创建的表名
@Table(name = "favor")
public class GoodsInfo extends Model implements Serializable, Cloneable{
    /**
     *
     */
    private static final long serialVersionUID = 6851895872936891139L;
    @Column
    String goodsId; // id
    @Column
    String goodsName; // 名称
    @Column
    String goodsIcon; // 图片
    @Column
    String goodsType; // 种类
    @Column
    double goodsPrice; // 价格
    @Column
    String goodsPercent; // 好评
    @Column
    int goodsComment; // 评论人数
    @Column
    int isPhone; // 是否手机专享
    @Column
    int isFavor; // 是否已关注

    /**
     * 构造函数
     */
    public GoodsInfo() {

    }

    /**
     * 构造函数
     */
    public GoodsInfo(String goodsId, String goodsName, String goodsIcon,
                     String goodsType, double goodsPrice, String goodsPercent,
                     int goodsComment, int isPhone, int isFavor) {
        super();
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsIcon = goodsIcon;
        this.goodsType = goodsType;
        this.goodsPrice = goodsPrice;
        this.goodsPercent = goodsPercent;
        this.goodsComment = goodsComment;
        this.isPhone = isPhone;
        this.isFavor = isFavor;
    }

    /**
     * 得到商品id
     */
    public String getGoodsId() {

        return goodsId;
    }

    /**
     * 设置商品id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 得到商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 得到商品图标
     */
    public String getGoodsIcon() {
        return goodsIcon;
    }

    /**
     * 设置商品图标
     */
    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    /**
     * 得到商品类型
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * 设置商品类型
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 得到商品价格
     */
    public double getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 设置商品价格
     */
    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 得到商品百分比
     */
    public String getGoodsPercent() {
        return goodsPercent;
    }

    /**
     * 设置商品百分比
     */
    public void setGoodsPercent(String goodsPercent) {
        this.goodsPercent = goodsPercent;
    }

    /**
     * 得到商品评论
     */
    public int getGoodsComment() {
        return goodsComment;
    }

    /**
     * 设置商品评论
     */
    public void setGoodsComment(int goodsComment) {
        this.goodsComment = goodsComment;
    }

    /**
     * 得到商品是否手机专享
     */
    public int getIsPhone() {
        return isPhone;
    }

    /**
     * 设置商品是否是手机专享
     */
    public void setIsPhone(int isPhone) {
        this.isPhone = isPhone;
    }

    /**
     * 得到商品是否已关注
     */
    public int getIsFavor() {
        return isFavor;
    }

    /**
     * 设置商品是否关注
     */
    public void setIsFavor(int isFavor) {
        this.isFavor = isFavor;
    }

    /**
     * 克隆一个商品
     */
    @Override
    public GoodsInfo clone() {
        return new GoodsInfo(goodsId, goodsName, goodsIcon, goodsType, goodsPrice, goodsPercent, goodsComment, isPhone, isFavor);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GoodsInfo other = (GoodsInfo) obj;
        if (goodsComment != other.goodsComment)
            return false;
        if (goodsIcon == null) {
            if (other.goodsIcon != null)
                return false;
        } else if (!goodsIcon.equals(other.goodsIcon))
            return false;
        if (goodsId == null) {
            if (other.goodsId != null)
                return false;
        } else if (!goodsId.equals(other.goodsId))
            return false;
        if (goodsName == null) {
            if (other.goodsName != null)
                return false;
        } else if (!goodsName.equals(other.goodsName))
            return false;
        if (goodsPercent == null) {
            if (other.goodsPercent != null)
                return false;
        } else if (!goodsPercent.equals(other.goodsPercent))
            return false;
        if (Double.doubleToLongBits(goodsPrice) != Double
                .doubleToLongBits(other.goodsPrice))
            return false;
        if (goodsType == null) {
            if (other.goodsType != null)
                return false;
        } else if (!goodsType.equals(other.goodsType))
            return false;
        if (isFavor != other.isFavor)
            return false;
        if (isPhone != other.isPhone)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + goodsComment;
        result = prime * result
                + ((goodsIcon == null) ? 0 : goodsIcon.hashCode());
        result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
        result = prime * result
                + ((goodsName == null) ? 0 : goodsName.hashCode());
        result = prime * result
                + ((goodsPercent == null) ? 0 : goodsPercent.hashCode());
        long temp;
        temp = Double.doubleToLongBits(goodsPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((goodsType == null) ? 0 : goodsType.hashCode());
        result = prime * result + isFavor;
        result = prime * result + isPhone;
        return result;
    }
}
