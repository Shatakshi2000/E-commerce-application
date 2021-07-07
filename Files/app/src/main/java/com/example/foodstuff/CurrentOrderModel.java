package com.example.foodstuff;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class CurrentOrderModel implements Serializable {

    @ServerTimestamp
    private String name;
    private String price;
    private String quantity;
    private String image;
    private Long number;
    private  String retailerPhoneNumber;
    private String retailerAddress;

    public CurrentOrderModel() {
    }

    public CurrentOrderModel(String name, String price, String quantity, String image, Long number, String retailerPhoneNumber, String retailerAddress) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.number = number;
        this.retailerPhoneNumber = retailerPhoneNumber;
        this.retailerAddress = retailerAddress;
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getRetailerPhoneNumber() {
        return retailerPhoneNumber;
    }

    public void setRetailerPhoneNumber(String retailerPhoneNumber) {
        this.retailerPhoneNumber = retailerPhoneNumber;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }
}
