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
public class ProductInfo extends Model implements Serializable, Cloneable{
    /**
     *
     */
    private static final long serialVersionUID = 6851895872936891139L;
    @Column
    String transactionNumber;          //商品id
    @Column
    String productName;                //商品名称
    @Column
    String productIcon;                //商品图片
    @Column
    String productCategory;            //商品种类
    @Column
    double productPrice;               //商品价格
    @Column
    int isFavor;                       //是否已关注
    @Column
    String productDescription;         //商品一句话描述
    @Column
    String productDetail;              //商品描述
    @Column
    String productUnit;                //商品单位 一份/一个

    /**
     * 构造函数
     */
    public ProductInfo() {

    }

    /**
     * 构造函数
     */
    public ProductInfo(String transactionNumber, String productName, String productIcon,
                     String productCategory,String productDescription,String productDetail,String productUnit, double productPrice, int isFavor) {
        super();
        this.transactionNumber = transactionNumber;
        this.productName = productName;
        this.productIcon = productIcon;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productDetail = productDetail;
        this.productUnit = productUnit;
        this.productPrice = productPrice;
        this.isFavor = isFavor;
    }

    /**
     * 得到商品id
     */
    public String getProductId() {
        return this.transactionNumber;
    }

    /**
     * 设置商品id
     */
    public void setProductId(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * 得到商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 得到商品图标
     */
    public String getProductIcon() {
        return productIcon;
    }

    /**
     * 设置商品图标
     */
    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    /**
     * 得到商品类型
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * 设置商品类型
     */
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * 得到商品价格
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * 设置商品价格
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
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
    public ProductInfo clone() {
        return new ProductInfo(transactionNumber, productName, productIcon, productCategory,productDescription,productDetail,productUnit,productPrice, isFavor);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductInfo other = (ProductInfo) obj;

        if (productIcon == null) {
            if (other.productIcon != null)
                return false;
        } else if (!productIcon.equals(other.productIcon))
            return false;
        if (transactionNumber == null) {
            if (other.transactionNumber != null)
                return false;
        } else if (!transactionNumber.equals(other.transactionNumber))
            return false;
        if (productName == null) {
            if (other.productName != null)
                return false;
        } else if (!productName.equals(other.productName))
            return false;

        if (Double.doubleToLongBits(productPrice) != Double
                .doubleToLongBits(other.productPrice))
            return false;
        if (productCategory == null) {
            if (other.productCategory != null)
                return false;
        } else if (!productCategory.equals(other.productCategory))
            return false;
        if (isFavor != other.isFavor)
            return false;
        return true;
    }

    /*@Override
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
    }*/
}
