package com.example.model;

import java.sql.Timestamp;
import java.util.List;

public class Outfit {
    private int outfitId;
    private String title;
    private String description;
    private String imageUrl;
    private int styleId;
    private int bodyTypeId;
    private String gender;
    private String season;
    private String occasion;
    private int createdBy;
    private Timestamp createdAt;

    private String styleName;
    private String bodyTypeName;
    private List<Product> products;

    public Outfit() {}

    public int getOutfitId() { return outfitId; }
    public void setOutfitId(int outfitId) { this.outfitId = outfitId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getStyleId() { return styleId; }
    public void setStyleId(int styleId) { this.styleId = styleId; }
    public int getBodyTypeId() { return bodyTypeId; }
    public void setBodyTypeId(int bodyTypeId) { this.bodyTypeId = bodyTypeId; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getStyleName() { return styleName; }
    public void setStyleName(String styleName) { this.styleName = styleName; }
    public String getBodyTypeName() { return bodyTypeName; }
    public void setBodyTypeName(String bodyTypeName) { this.bodyTypeName = bodyTypeName; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}