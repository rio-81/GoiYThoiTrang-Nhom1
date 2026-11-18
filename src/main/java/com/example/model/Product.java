package com.example.model;

public class Product {
    private int productId;
    private int categoryId;
    private String name;
    private double price;
    private String imageUrl;
    private String affiliateUrl;
    private String platform;
    private int clickCount;

    public Product() {}
    public Product(int productId, int categoryId, String name, double price, String imageUrl, String affiliateUrl, String platform, int clickCount) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.affiliateUrl = affiliateUrl;
        this.platform = platform;
        this.clickCount = clickCount;
    }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getAffiliateUrl() { return affiliateUrl; }
    public void setAffiliateUrl(String affiliateUrl) { this.affiliateUrl = affiliateUrl; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public int getClickCount() { return clickCount; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
}