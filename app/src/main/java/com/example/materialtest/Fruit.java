package com.example.materialtest;

public class Fruit {

    private String name;//水果名称
    private int imageId;//水果对应图片的资源id

    public Fruit(String name,int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
