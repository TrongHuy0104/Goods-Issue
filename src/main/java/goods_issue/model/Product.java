/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.model;

public class Product {

    private String pId;
    private String pName;
    private String pSlug;
    private String pCode;
    private double pRating;
    private String pBanner;
    private String pThumb;
    private double pSale;
    private String pTag;
    private double pPrice;
    private String pOrigin;
    private double pTax;
    private String pDescription;
    private int pNumberLeft;
    private int pNumberOfProduct;
    private String pCategory;
    private int pCateId;
    private int pStatus;
    private String sId;

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsId() {
        return sId;
    }
    public Product(String pId, String pName, double pRating, String pThumb, double pPrice,String pDescription, int pNumberLeft, int pNumberOfProduct, String pCategory, int pStatus, String sId) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pCategory = pCategory;
        this.pStatus = pStatus;
        this.sId = sId;
    }
    
    

//    public Product(String pId, String pName, double pRating, String pThumb, double pPrice,String pDescription, int pNumberLeft, int pNumberOfProduct, String pCategory, int pStatus, String sId) {
//        this.pId = pId;
//        this.pName = pName;
//        this.pRating = pRating;
//        this.pThumb = pThumb;
//        this.pPrice = pPrice;
//        this.pDescription = pDescription;
//        this.pNumberLeft = pNumberLeft;
//        this.pNumberOfProduct = pNumberOfProduct;
//        this.pCategory = pCategory;
//        this.sId = sId;
//    }

    @Override
    public String toString() {
        return "Product{" + "pId=" + pId + ", pName=" + pName + ", pSlug=" + pSlug + ", pCode=" + pCode + ", pRating=" + pRating + ", pBanner=" + pBanner + ", pThumb=" + pThumb + ", pSale=" + pSale + ", pTag=" + pTag + ", pPrice=" + pPrice + ", pOrigin=" + pOrigin + ", pTax=" + pTax + ", pDescription=" + pDescription + ", pNumberLeft=" + pNumberLeft + ", pNumberOfProduct=" + pNumberOfProduct + ", pCategory=" + pCategory + ", pCateId=" + pCateId + ", pStatus=" + pStatus + '}';
    }

    

    public Product(String pId, String pName, String pSlug, String pCode, double pRating, String pBanner, String pThumb, double pSale, String pTag, double pPrice, String pOrigin, double pTax, String pDescription, int pNumberLeft, int pNumberOfProduct) {
        this.pId = pId;
        this.pName = pName;
        this.pSlug = pSlug;
        this.pCode = pCode;
        this.pRating = pRating;
        this.pBanner = pBanner;
        this.pThumb = pThumb;
        this.pSale = pSale;
        this.pTag = pTag;
        this.pPrice = pPrice;
        this.pOrigin = pOrigin;
        this.pTax = pTax;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
    }

    public Product() {
    }

    public Product(String pId, String pName, String pSlug, String pCode, double pRating, String pBanner, String pThumb, double pSale, String pTag, double pPrice, String pOrigin, double pTax) {
        this.pId = pId;
        this.pName = pName;
        this.pSlug = pSlug;
        this.pCode = pCode;
        this.pRating = pRating;
        this.pBanner = pBanner;
        this.pThumb = pThumb;
        this.pSale = pSale;
        this.pTag = pTag;
        this.pPrice = pPrice;
        this.pOrigin = pOrigin;
        this.pTax = pTax;
    }

    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
    }
    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct, int pStatus) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pStatus = pStatus;
    }

    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct, String pOrigin) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pOrigin = pOrigin;
    }
    
    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct, String pOrigin, int pStatus) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pOrigin = pOrigin;
    }
    
    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct, String pOrigin, String pCategory) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pOrigin = pOrigin;
        this.pCategory = pCategory;
        this.pStatus = pStatus;
    }
    
    public Product(String pId, String pName, double pRating, String pThumb, double pPrice, String pDescription, int pNumberLeft, int pNumberOfProduct, String pOrigin, String pCategory , int pStatus) {
        this.pId = pId;
        this.pName = pName;
        this.pRating = pRating;
        this.pThumb = pThumb;
        this.pPrice = pPrice;
        this.pDescription = pDescription;
        this.pNumberLeft = pNumberLeft;
        this.pNumberOfProduct = pNumberOfProduct;
        this.pOrigin = pOrigin;
        this.pCategory = pCategory;
        this.pStatus = pStatus;
    }

    public String getpCategory() {
        return pCategory;
    }

    public double getpTax() {
        return pTax;
    }

    public String getpId() {
        return pId;
    }

    public String getpName() {
        return pName;
    }

    public String getpSlug() {
        return pSlug;
    }

    public String getpCode() {
        return pCode;
    }

    public int getpCateId() {
        return pCateId;
    }

    public int getpStatus() {
        return pStatus;
    }
    
    

    public double getpRating() {
        return pRating;
    }

    public String getpBanner() {
        return pBanner;
    }

    public String getpThumb() {
        return pThumb;
    }

    public double getpSale() {
        return pSale;
    }

    public String getpTag() {
        return pTag;
    }

    public double getpPrice() {
        return pPrice;
    }

    public String getpOrigin() {
        return pOrigin;
    }

    public String getpDescription() {
        return pDescription;
    }

    public int getpNumberLeft() {
        return pNumberLeft;
    }

    public int getpNumberOfProduct() {
        return pNumberOfProduct;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpSlug(String pSlug) {
        this.pSlug = pSlug;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public void setpRating(double pRating) {
        this.pRating = pRating;
    }

    public void setpBanner(String pBanner) {
        this.pBanner = pBanner;
    }

    public void setpCateId(int pCateId) {
        this.pCateId = pCateId;
    }
    

    public void setpThumb(String pThumb) {
        this.pThumb = pThumb;
    }

    public void setpSale(double pSale) {
        this.pSale = pSale;
    }

    public void setpTag(String pTag) {
        this.pTag = pTag;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public void setpOrigin(String pOrigin) {
        this.pOrigin = pOrigin;
    }

    public void setpTax(double pTax) {
        this.pTax = pTax;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public void setpNumberLeft(int pNumberLeft) {
        this.pNumberLeft = pNumberLeft;
    }

    public void setpNumberOfProduct(int pNumberOfProduct) {
        this.pNumberOfProduct = pNumberOfProduct;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public void setpStatus(int pStatus) {
        this.pStatus = pStatus;
    }

}
