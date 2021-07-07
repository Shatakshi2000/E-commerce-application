package com.example.foodstuff;

public class Model
{
    private String CategoryName;
    private int img;

    public String getCategoryName()
    {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
