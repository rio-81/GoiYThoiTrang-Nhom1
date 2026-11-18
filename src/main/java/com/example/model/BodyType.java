package com.example.model;

public class BodyType {
    private int bodyTypeId;
    private String name;
    private String description;

    public BodyType() {}
    public BodyType(int bodyTypeId, String name, String description) {
        this.bodyTypeId = bodyTypeId;
        this.name = name;
        this.description = description;
    }
    public int getBodyTypeId() { return bodyTypeId; }
    public void setBodyTypeId(int bodyTypeId) { this.bodyTypeId = bodyTypeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}