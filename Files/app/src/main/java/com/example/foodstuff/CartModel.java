package com.example.foodstuff;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class CartModel implements Serializable
{
    @ServerTimestamp
    private String name;
    private String price;
    private String quantity;
    private String image;
    private String username;
    String address;


    public CartModel() {

    }


    public CartModel(String image, String name, String price, String quantity, String username,String address) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
