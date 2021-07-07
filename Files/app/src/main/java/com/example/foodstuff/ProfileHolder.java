package com.example.foodstuff;

public class ProfileHolder {

    String category,image,username,password,address;

    ProfileHolder()
    {

    }

    public ProfileHolder(String category, String image, String username,String password,String address) {
        this.category = category;
        this.image = image;
        this.username = username;
        this.password= password;
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
